import onion.lifeproducts.rms.presentation.ANSI;
import onion.lifeproducts.rms.presentation.ConsoleUI;
import onion.lifeproducts.rms.presentation.Data;

/// needed in case there is a need to modify the default ANSI options for the ConsoleUI
// import onion.lifeproducts.rms.presentation.ConsoleUIANSIOptions;
// import onion.lifeproducts.rms.presentation.Util;

public class Main {

	public static void main(String[] args) { run(); }

	public static void run() { 
		try {
			executeMainApplication();
		} catch (Exception e) {
			// should never occur under any circumstances
			System.out.printf("[Main] Caught exception: %s\n", e);
		}
	}

	static public void executeMainApplication() {
		new ConsoleUI(Data.mainMenuEntries)
			// set banner
			.setInitMessage(
				String.format(

					ANSI.FG_CYAN +

					"┌─────────────────────────────┬────────┬──────┐\n" +
					"│%s│%s│%s│\n" +
					"└─────────────────────────────┴────────┴──────┘\n" +

					ANSI.CA,

					ANSI.CA + " " + ANSI.BOLD + ANSI.FG_MAGENTA +
					"Recycling Management System" +
					ANSI.CA + " " + ANSI.FG_CYAN,

					ANSI.CA + " " + ANSI.BOLD + ANSI.FG_GREEN +
					"SDG 12" +
					ANSI.CA + " " + ANSI.FG_CYAN,

					ANSI.CA + " " + ANSI.BOLD + ANSI.FG_YELLOW +
					"Java" +
					ANSI.CA + " " + ANSI.FG_CYAN

				)
			)

			/// these are the default options on ConsoleUI
			/// instance that are enabled by default.
			/// You can uncomment them and change to the needs

			// .setEntriesFormat("$o) $d")    // how entries are formatted
			// .setDefaultInputPrompt("> ")   // default input prompt for all user stdin
			// .setPrintMenuAtStartup(true)   // print menu at menu startup
			// .setMakeSpaceAfterInput(true)  // make space after user input
			// .setIncludeDeaultOptions(true) // include default options
			// .setUtil(new Util(new ConsoleUIANSIOptions())) // util object
			// .setAnsiOptions(new ConsoleUIANSIOptions())    // ansi options

			.runMenu();

	}
}
