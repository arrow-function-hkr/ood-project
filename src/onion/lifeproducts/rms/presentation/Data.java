package onion.lifeproducts.rms.presentation;

/** Static class that holds the dynamic data that is fed into other classes */
public final class Data {
	/**
	 * List of all main option entries that the main ConsoleUI application will have.
	 */
	// validation before main execution is done inside `Menu.init()` method. See: /// init: menu options string
	static public final ConsoleUIEntry[] mainMenuChoices = {
		new ConsoleUIEntry("1", "Create product", () -> {}),
		new ConsoleUIEntry("2", "Create material", () -> {}),
		new ConsoleUIEntry("3", "Create environmental impact report", () -> {}),
		new ConsoleUIEntry("4", "Create recycling guidance", () -> {}),
		new ConsoleUIEntry("5", "Create recycling category", () -> {}),
		new ConsoleUIEntry("6", "Create product category", () -> {}),
		new ConsoleUIEntry("m", "Print menu", "print:menu"),
		new ConsoleUIEntry("c", "Clear screen", "clear:screen"),
		new ConsoleUIEntry("q", "Quit application", "exit"),
	};

}
