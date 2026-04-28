package onion.lifeproducts.rms.presentation;


import java.util.HashMap;


/**
 * Menu representing all available options of the main application in form of CLI application.
 */
public final class ConsoleUI {

	private final ConsoleUIEntry[] mainOptions;

	static private final ConsoleUIEntry[] defaultMainOptions = {};

	/** Mappings of each choice to it's callback */
	private final HashMap<String, Runnable> mainChoicesMappings = new HashMap<>();


	/** Format of how each option should be formated.<br><br>
	 *
	 * '$o' represents the option key.<br><br>
	 *
	 * '$d' represents the option description.
	 */
	private final String format__mainOption = "$o) $d";
	/** Format that holds template for incorrect main menu option chosen (must contain only one %s template) */
	private final String format__invalidMainMenuOption = "Invalid option: '%s'\n";


	/** Holds the built string for all menu choices to not generate it/print one entry by one each time */
	private String mainChoicesStr;
	/** Default input prompt used in all readings, unless specified different*/
	private final String defaultInputPrompt = "> "; // default fallback


	/** ANSI option for instance of menu. Used to set graphics in some cases. See {@link ConsoleUIANSIOptions} */
	private final ConsoleUIANSIOptions ansiOptions;

	/** Current ANSI formatting that will be applied when reading user input */
	private String ANSI_CURRENT;

	/** Indication whether to continue reading user input or not */
	private boolean activeEventLoop = true;
	/** Indication whether to skip extra work that is done after processing the main menu enty or not */
	private boolean skipOneIterationPostProcessing = false;
	/** Indication whethert to make spacing after user have chosen an option and retrieved result or not */
	private boolean makeSpaceAfterInput = true;

	/** Create menu instance with default values:<br><br>
	 *
	 * - Empty list of options
	 * - Default ANSI options from {@link Util} class (static field `Util.consoleUIANSIOptions'). */
	public ConsoleUI() {
		this(ConsoleUI.defaultMainOptions);
	}

	/** Create menu instance provided with list of main menu options
	 * that will be used during execution for this specific instance of menu,
	 * as well as custom ANSI options that menu will use during execution. */
	public ConsoleUI(
		final ConsoleUIEntry[] mainOptions,
		final ConsoleUIANSIOptions menuANSIOptions
	) {
		this.mainOptions = mainOptions;
		this.ansiOptions = menuANSIOptions;
		this.ANSI_CURRENT = this.ansiOptions.USER_INPUT;
		this.init();
	}

	/** Create menu instance provided with custom ANSI values that menu will use during execution */
	public ConsoleUI(final ConsoleUIANSIOptions menuANSIOptions) {
		this(ConsoleUI.defaultMainOptions, menuANSIOptions);
	}

	/** Create menu instance provided with list of main menu options
	 * that will be used during execution for this specific instance of menu.<br>
	 * Default {@link ConsoleUIANSIOptions} options from {@link Util} class
	 * will be used for graphics options (static field `Util.consoleUIANSIOptions').
	 */
	public ConsoleUI(final ConsoleUIEntry[] mainOptions) {
		this(mainOptions, Util.consoleUIANSIOptions);
	}

	/** Initialization logic that will be invoked from constructor to group initialization logic in one place, to not duplicate it across constructor overloads. */
	private void init() {
		/// init:

		int length = this.mainOptions.length;
		String[] mainChoicesRows = new String[length];

		// will exit the process if something is wrong
		// In production, it will silently continue,
		// since all options will be checked before merging into master
		ConsoleUIEntryChecker.checkConsoleUIEntryOptions(this.mainOptions);
		// here, all options are valid

		/// init: menu options string
		for (int i = 0; i < length; i++) {

			ConsoleUIEntry entry = this.mainOptions[i];

			String key = entry.key(),
				   description = entry.description(),
				   command = entry.command();
			Runnable callback = entry.callback();

			mainChoicesRows[i] = new String(this.format__mainOption)
				.replaceAll("\\$o", String.format(
					"%s%s%s",
					this.ansiOptions.MENU_OPTION_KEY,
					key,
					ANSI.CA
				))
				.replaceAll("\\$d", description);

			/// init: options mappings
			if (callback == null) callback = this.getMainChoiseCommandMapping(command);
			this.mainChoicesMappings.put(key, callback);
		}

		this.mainChoicesStr = String.join("\n", mainChoicesRows);
	}

	/** Print menu to the output */
	private void printMenu() {
		System.out.println(this.mainChoicesStr);
	}

	/** Toggle the indication of the active event loop, effectively exiting the menu */
	private void disableEventLoop() {
		this.activeEventLoop = false;
	}

	/** Clear the screen and put the cursor in the top-left corner */
	private void clearScreen() {
		Util.clearScreen();
		this.skipOneIterationPostProcessing = true;
	}

	private Runnable getMainChoiseCommandMapping(final String command) {
		switch (command) {
			case "print:menu": return this::printMenu;
			case "clear:screen": return this::clearScreen;
			case "exit": return this::disableEventLoop;
			default: return () -> {};
		}
	}

	/**
	 * Start the main event loop execution. This method will end execution
	 * only when user declares so (or if program exits earlier).
	 */
	public void run() {
		/// run:

		this.activeEventLoop = true;

		this.printMenu(); // print menu only once during initialization process
		if (this.makeSpaceAfterInput) System.out.println();

		String menuOption;

		while (this.activeEventLoop) {

			menuOption = Util.getLineAnswer(this.defaultInputPrompt, this.ANSI_CURRENT);

			if (!this.mainChoicesMappings.containsKey(menuOption)) {
				System.out.printf(
					this.format__invalidMainMenuOption,
					ANSI.formatString(menuOption, ANSI.FG_YELLOW)
				);

				if (this.makeSpaceAfterInput) System.out.println();
				continue;
			}

			this.mainChoicesMappings.get(menuOption).run();

			if (
				this.makeSpaceAfterInput &&
				this.activeEventLoop &&
				!this.skipOneIterationPostProcessing
			) System.out.println();

			if (this.skipOneIterationPostProcessing) this.skipOneIterationPostProcessing = false;
		}
	}
}
