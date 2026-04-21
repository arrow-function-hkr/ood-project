package onion.lifeproducts.rms.presentation;


/** ANSI Graphics options for the menu */
public final class ConsoleUIANSIOptions {

	/** Holds the ANSI sequence to manipulate the user input */
	public String USER_INPUT = ANSI.FG_CYAN;
	/** Holds the ANSI sequence to manipulate the menu options key graphics */
	public String MENU_OPTION_KEY = ANSI.FG_CYAN;
	/** Holds the ANSI sequence to manipulate the menu options description highlight graphics */
	public String MENU_OPTION_HIGHLIGHT = ANSI.FG_YELLOW;

	/** Default empty constructor */
	public ConsoleUIANSIOptions() {}

	/** Set all ANSI values (if each value is not null) */
	public ConsoleUIANSIOptions(String userInput, String menuOptionKey, String menuOptionHighlight) {
		if (userInput != null) this.USER_INPUT = userInput;
		if (menuOptionKey != null) this.MENU_OPTION_KEY = menuOptionKey;
		if (menuOptionHighlight != null) this.MENU_OPTION_HIGHLIGHT = menuOptionHighlight;
	}

	/** Set only user input ANSI */
	public ConsoleUIANSIOptions(String userInput) {
		if (userInput != null) this.USER_INPUT = userInput;
	}
}
