package onion.lifeproducts.rms.presentation;


import java.util.HashMap;
import java.util.Scanner;

import onion.lifeproducts.rms.domain.Material;
import onion.lifeproducts.rms.domain.Product;
import onion.lifeproducts.rms.domain.ProductCategory;
import onion.lifeproducts.rms.domain.RecyclingCategory;


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
	/**
	 * Controls whether to perform strict main menu choice checking or not.<br><br>
	 *
	 * Strict checking means that prorgram will stop if something is wrong.<br><br>
	 *
	 * Loose checking means that prgoram will skip entry if something is wrong
	 * (i.e. will continue execution without problematic entries)
	 */
	private final boolean strictMainMenuChecking = true;


	/**
	 * Default ANSI option for menu if not provided
	 * (to not create new object each time, one is reused)
	 */
	static private final ConsoleUIANSIOptions defaultMenuAnsiOptions = new ConsoleUIANSIOptions();

	/** Create menu instance with default values */
	public ConsoleUI() {
		this(ConsoleUI.defaultMainOptions, ConsoleUI.defaultMenuAnsiOptions);
	}

	public ConsoleUI(ConsoleUIEntry[] mainOptions, ConsoleUIANSIOptions menuANSIOptions) {
		this.mainOptions = mainOptions;
		this.ansiOptions = menuANSIOptions;
		this.ANSI_CURRENT = this.ansiOptions.USER_INPUT;
		this.init();
	}

	/** Create menu instance provided with custom ANSI values that menu will use during execution */
	public ConsoleUI(ConsoleUIANSIOptions menuANSIOptions) {
		this(ConsoleUI.defaultMainOptions, menuANSIOptions);
	}

	public ConsoleUI(ConsoleUIEntry[] mainOptions) {
		this(mainOptions, ConsoleUI.defaultMenuAnsiOptions);
	}

	/** Initialization logic that will be invoked from constructor to group initialization logic in one place, to not duplicate it across constructor overloads. */
	private void init() {
		/// init:

		int processed = 0, length = this.mainOptions.length;
		String[] mainChoicesRows = new String[length];

		/// init: menu options string
		for (int i = 0; i < length; i++) {

			ConsoleUIEntry entry = this.mainOptions[i];

			String key = entry.key(),
				   description = entry.description(),
				   command = entry.command();
			Runnable callback = entry.callback();

			// check that all values in entry are not null
			if (
					key == null || description == null ||
					(callback == null && command == null)
			) {
				if (this.strictMainMenuChecking) {
					System.out.printf("[debug]: choice entry #%d has null values. Aborting...\n", i);
					System.exit(2);
				} else continue;
			} else if (!(key instanceof String)) {
				if (this.strictMainMenuChecking) {
					System.out.printf("[debug]: choice entry #%d has no string value for field 'key'. Aborting...\n", i);
					System.exit(2);
				} else continue;
			} else if (!(description instanceof String)) {
				if (this.strictMainMenuChecking) {
					System.out.printf("[debug]: choice entry #%d has no string value for field 'description'. Aborting...\n", i);
					System.exit(2);
				} else continue;
			} else if (command == null && !(callback instanceof Runnable)) {
				if (this.strictMainMenuChecking) {
					System.out.printf("[debug]: choice entry #%d has no 'Runnable' value for field 'callback'. Aborting...\n", i);
					System.exit(2);
				} else continue;
			} else if (callback == null && !(command instanceof String)) {
				if (this.strictMainMenuChecking) {
					System.out.printf("[debug]: choice entry #%d has no string value for field 'command'. Aborting...\n", i);
					System.exit(2);
				} else continue;
			}

			processed++;

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

		// check that there are no null values
		if (!this.strictMainMenuChecking && processed != length) {
			String[] mainChoicesFiltered = new String[processed];
			for (int i = 0, processedElements = 0; i < this.mainOptions.length; i++) {
				if (mainChoicesRows[i] == null) continue;
				else mainChoicesFiltered[processedElements++] = mainChoicesRows[i];
			}
			mainChoicesRows = mainChoicesFiltered;
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

	/** Create product, prompting user to fill all the values */
	private void createProduct() {
		Util.TODO("implement createProduct");
	}

	/** Create material, prompting user to fill all the values */
	private void createMaterial() {
		Util.TODO("implement createMaterial");
	}

	/** Create material, prompting user to fill all the values */
	private void createImpactReport() {
		Util.TODO("implement createImpactReport");
	}

	/** Create recycling report for a specific product, or for a series of products */
	private void createRecyclingGuidance() {
		Util.TODO("implement createRecyclingGuidance");
	}

	/** Create a product category */
	private void createProductCategory() {
		Util.TODO("implement createProductCategory");
	}

	/** Create a recycling category */
	private void createRecyclingCategory() {
		Util.TODO("implement createRecyclingCategory");
	}

	// template methods to give clue what is going on

	/** Get all products in the application */
	private Product[] getAllProducts() {
		return new Product[]{};
	}

	/** Get all materials in the application */
	private Material[] getAllMaterials() {
		return new Material[]{};
	}

	/** Get all product categories in the application */
	private ProductCategory[] getAllProductCategories() {
		return new ProductCategory[]{};
	}

	/** Get all product categories in the application */
	private RecyclingCategory[] getAllRecyclingCategories() {
		return new RecyclingCategory[]{};
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
