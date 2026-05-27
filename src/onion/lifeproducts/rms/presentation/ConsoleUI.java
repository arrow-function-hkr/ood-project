package onion.lifeproducts.rms.presentation;


import java.util.Arrays;
import java.util.HashMap;

/**
 * Menu representing all available options of the main application in form of CLI application.
 * Designed to be used in multiple instances for sub-menus.
 */
public final class ConsoleUI {

	/** Default entries that will be appended to the end of the user provided options by default.<br><br>
	 *
	 * Controlled by the `includeDefaultOptions` property.<br><br>
	 *
	 * They can be overriden by the user-provided options with the same option */
	private final ConsoleUIEntry[] defaultOptions = new ConsoleUIEntry[]{
		new ConsoleUIEntry("m", "Print menu", "print:menu"),
		new ConsoleUIEntry("c", "Clear screen", "clear:screen"),
		new ConsoleUIEntry("q", "Quit application", "exit")
	};
	private final ConsoleUIEntry[] mainOptions; // user-provided options
	private final ConsoleUIEntry[] allOptions;  // user-provided + default
	// this one will be used in generaiton of option mappings and options string
	private ConsoleUIEntry[] currentOptions;

	static private final ConsoleUIEntry[] defaultMainOptions = {};

	/** Mappings of each choice to it's callback */
	private final HashMap<String, Lambda<String>> mainChoicesMappings = new HashMap<>();

	// deafult util instance to not create new object each time on each constructor invocation
	static private Util defaultUtil = new Util();
	// util instance that will be used in each instance of ConsoleUI.
	// this allows for dynamic options passing without relying on static methods from the class itself
	private Util util = ConsoleUI.defaultUtil;


	/** Format of how each option should be formated.<br><br>
	 *
	 * '$o' represents the option key.<br><br>
	 *
	 * '$d' represents the option description.
	 */
	private String format__mainOption = "$o) $d";
	/** Format that holds template for incorrect main menu option chosen (must contain only one %s template) */
	private final String format__invalidMainMenuOption = "Invalid option: '%s'\n";
	/** Init message at the very first menu launch */
	private String msg__init = null;


	/** Holds the built string for all menu choices to not generate it/print one entry by one each time */
	private String mainChoicesStr;
	/** Default input prompt used in all readings, unless specified different*/
	private final String defaultInputPrompt = "> "; // default fallback
	/** Current input prompt that will be used in all readings from the user */
	private String inputPrompt = this.defaultInputPrompt;

	/** ANSI option for instance of menu. Used to set graphics in some cases. See {@link ConsoleUIANSIOptions} */
	private ConsoleUIANSIOptions ansiOptions;

	/** Current ANSI formatting that will be applied when reading user input */
	private String ANSI_CURRENT;

	/** Indication whether to continue reading user input or not */
	private boolean activeMenuLoop = true;
	/** Indication whether to skip extra work that is done after processing the main menu enty or not */
	private boolean skipOneIterationPostProcessing = false;
	/** Indication whether to make spacing after user have chosen an option and retrieved result or not */
	private boolean makeSpaceAfterInput = true;
	/** Indication whether to print the menu options at menu startup or not */
	private boolean printMenuAtStartup = true;
	/** Indication whether the initialization process in constructor failed (for some reason) */
	private boolean failureInInitialization = false;
	/** Indication whether to include the default options in the options provided in constructor */
	private boolean includeDefaultOptions = true;
	/** Indication whether there are pending changes that needs to be made to the entries */
	private boolean isPendingChange = true;
	/** Indication whether the initial message is shown */
	private boolean isInitMessageShown = true;

	/** Create menu instance with default values:<br><br>
	 *
	 * - Default list of options
	 * - Default ANSI options from {@link this.util. class (static field `this.util.defaultConsoleUIANSIOptions'). */
	public ConsoleUI() {
		this(ConsoleUI.defaultMainOptions, ConsoleUI.defaultUtil);
	}

	/** Create menu instance with default util instance:<br><br>
	 *
	 * - Default list of options
	 * - Default ANSI options from {@link this.util. class (static field `this.util.defaultConsoleUIANSIOptions'). */
	public ConsoleUI(final ConsoleUIEntry[] mainOptions) {
		this(mainOptions, ConsoleUI.defaultUtil);
	}

	/** Create menu instance provided with list of main menu options
	 * that will be used during execution for this specific instance of menu,
	 * as well as custom ANSI options that menu will use during execution. */
	public ConsoleUI(final ConsoleUIEntry[] mainOptions, final Util util) {
		this.mainOptions = mainOptions;
		if (util != null) this.ansiOptions = util.consoleUIANSIOptions;
		else this.ansiOptions = Util.defaultConsoleUIANSIOptions;
		this.ANSI_CURRENT = this.ansiOptions.USER_INPUT;

		// generate array of all options once, use later during execution
		this.allOptions = this.overrideDefaultEntries(
			util.concatArraysWithCopy(this.mainOptions, this.defaultOptions)
		);

		this.currentOptions = this.getCurrentOptions();
	}

	private void generateOptionsAsString() {
		final int length = this.currentOptions.length;
		final String[] mainChoicesRows = new String[length];

		if (!ConsoleUIEntryChecker.checkConsoleUIEntryOptions(this.currentOptions)) {
			this.failureInInitialization = true;
			return;
		}

		this.mainChoicesMappings.clear();

		/// init: menu options string
		for (int i = 0; i < length; i++) {
			final ConsoleUIEntry entry = this.currentOptions[i];

			final String
				option = entry.option(),
				description = entry.description(),
				command = entry.command();
			Lambda<String> callback = entry.callback();

			mainChoicesRows[i] = this.util.formatHighlightString(
				this.format__mainOption
				.replaceAll("\\$o", String.format(
					"%s%s%s",
					this.ansiOptions.MENU_OPTION_KEY,
					option,
					ANSI.CA
				))
				.replaceAll("\\$d", description),
				this.ansiOptions.TEXT_HIGHLIGHT
			);


			/// init: options mappings
			if (callback == null) callback = new Lambda<>(this.getMainChoiseCommandMapping(command));
			this.mainChoicesMappings.put(option, callback);
		}

		this.mainChoicesStr = String.join("\n", mainChoicesRows);
	}

	private ConsoleUIEntry[] overrideDefaultEntries(final ConsoleUIEntry[] originalConsoleUIEntries) {
		final ConsoleUIEntry[] originalConsoleUIEntriesCopy = Arrays.copyOf(
			originalConsoleUIEntries,
			originalConsoleUIEntries.length
		);
		final HashMap<String, Integer> seen = new HashMap<>();

		final int originalLength = originalConsoleUIEntriesCopy.length;

		for (int i = 0; i < originalLength; i++) {
			final ConsoleUIEntry entry = originalConsoleUIEntriesCopy[i];
			final String option = entry.option();
			if (seen.containsKey(option) && seen.get(option) == -1) seen.put(option, i);
			else seen.put(option, -1);
		}

		// create the new array containing no duplicate entries with the same option
		final ConsoleUIEntry[] overridenEntries = new ConsoleUIEntry[seen.size()];

		for (int i = 0, updated = 0; i < originalLength; i++) {
			final ConsoleUIEntry entry = originalConsoleUIEntriesCopy[i];
			final String option = entry.option();
			final Integer referenceIdx = seen.get(option);
			if (seen.containsKey(option) && referenceIdx != -1) {
				originalConsoleUIEntriesCopy[referenceIdx] = entry;
				seen.remove(option);
				continue;
			}
			overridenEntries[updated++] = entry;
		}

		return overridenEntries;
	}

	/** Print menu to the output */
	private void printMenu() {
		System.out.println(this.mainChoicesStr);
	}

	/** Clear the screen and put the cursor in the top-left corner */
	private void clearScreen() {
		this.util.clearScreen();
		this.skipOneIterationPostProcessing = true;
	}
	
	/** Stops the active event loop, effectively exiting the menu */
	private void stopMenu() {
		this.activeMenuLoop = false;
	}

	/** Returns callback function for given option that is related to current instance of ConsoleUI */
	private Runnable getMainChoiseCommandMapping(final String command) {
		// this mapping should match the mapping of the commands in the 'ConsoleUIEntry' class
		switch (command) {
			case "print:menu": return this::printMenu;
			case "clear:screen": return this::clearScreen;
			case "exit": return this::stopMenu;
			default: return () -> {};
		}
	}

	/** Get the ConsoleUI entry array of all options that need to be processed */
	private ConsoleUIEntry[] getCurrentOptions() {
		return this.includeDefaultOptions ? this.allOptions : this.mainOptions;
	}

	/** Execute the block of code before the menu starts up.<br><br>
	 * 
	 * @return - the indication whether to continue menu processing or not
	 */
	private boolean preMenuProcedure() {

		if (!this.isInitMessageShown && this.msg__init != null) {
			System.out.print(this.msg__init);
			this.isInitMessageShown = true;
		}
		
		if (this.isPendingChange) {
			generateOptionsAsString();
			this.currentOptions = this.getCurrentOptions();
			this.isPendingChange = false;
		}

		if (this.failureInInitialization) {
			System.out.println("Previous initialization process failed. Aborting...");
			return false;
		}

		return true;

	}


	/* ====== private methods ====== */
	/* ----------------------------- */
	/* ====== public  methods ====== */


	/** Set the default prompt used in all input promps from the user for current instance of ConsoleUI.
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI setDefaultInputPrompt(final String prompt) {
		if (prompt == null) return this;
		this.inputPrompt = prompt;
		return this;
	}

	/** Set the flag to make space after user typed an option
	 * to make the CLI experience more enjoyable and readable
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 * */
	public ConsoleUI setMakeSpaceAfterInput(final boolean value) {
		this.makeSpaceAfterInput = value;
		return this;
	}

	/** Set the flag to print the menu when the menu starts up
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI setPrintMenuAtStartup(final boolean value) {
		this.printMenuAtStartup = value;
		return this;
	}

	/** Set the format of how each option should be formated.<br><br>
	 *
	 * '$o' represents the option key.<br><br>
	 *
	 * '$d' represents the option description.
	 *
	 * @param format - format of the entry using format specifiers '$o', '$d'.
	 * If format is null, format won't be changed
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI setEntriesFormat(final String format) {
		if (format == null) return this;
		this.format__mainOption = format;
		this.isPendingChange = true;
		return this;
	}

	/** Set the flag that controls whether to include the
	 * default options in the final options list or not
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI setIncludeDeaultOptions(final boolean value) {
		if (this.includeDefaultOptions && value) return this;
		this.includeDefaultOptions = value;
		this.currentOptions = this.getCurrentOptions();
		this.isPendingChange = true;
		return this;
	}

	/** Set the util instance that will be used in the ConsoleUI instance<br><br>
	 *
	 * If the argument is null, nothing will be updated
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI setUtil(final Util util) {
		if (util != null) {
			this.util = util;
			return this.setAnsiOptions(util.consoleUIANSIOptions);
		}
		return this;
	}

	/** Set the util instance that will be used in the ConsoleUI instance<br><br>
	 *
	 * If the argument is null, nothing will be updated
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI setAnsiOptions(final ConsoleUIANSIOptions ansiOptions) {
		if (ansiOptions != null) {
			this.ansiOptions = ansiOptions;
			this.ANSI_CURRENT = ansiOptions.USER_INPUT;
		}
		this.isPendingChange = true;
		return this;
	}

	/** Set the init message that will be shown during the very first menu startup.<br><br>
	 *
	 * If the argument is null, nothing will be updated.<br><br>
	 *
	 * Next time when the menu starts up, the message will be shown only once.
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI setInitMessage(final String message) {
		if (message == null) return this;
		this.msg__init = message;
		this.isInitMessageShown	= false;
		return this;
	}

	/**
	 * Start the main event loop execution. This method will end execution
	 * only when user declares so (or if program exits earlier).
	 *
	 * @return the reference to the same ConsoleUI instance that the method was invoked on
	 */
	public ConsoleUI runMenu() {
		/// run:

		if (!this.preMenuProcedure()) return this;

		this.activeMenuLoop = true;

		// print menu only once during initialization process
		if (this.printMenuAtStartup) this.printMenu();
		if (this.printMenuAtStartup && this.makeSpaceAfterInput) System.out.println();

		String menuOption;

		while (this.activeMenuLoop) {

			menuOption = this.util.getLineAnswer(this.inputPrompt, this.ANSI_CURRENT);

			if (!this.mainChoicesMappings.containsKey(menuOption)) {
				System.out.printf(
					this.format__invalidMainMenuOption,
					ANSI.formatString(menuOption, ANSI.FG_YELLOW)
				);

				if (this.makeSpaceAfterInput) System.out.println();
				continue;
			}

			// invoke the callback that is assigned to this option,
			// passing the name of the option that was chosen
			this.mainChoicesMappings.get(menuOption).run(menuOption);

			if (
				this.makeSpaceAfterInput &&
				this.activeMenuLoop &&
				!this.skipOneIterationPostProcessing
			) System.out.println();

			if (this.skipOneIterationPostProcessing) this.skipOneIterationPostProcessing = false;
		}
		return this;
	}
}
