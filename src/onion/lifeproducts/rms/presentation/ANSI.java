package onion.lifeproducts.rms.presentation;

/*
 * Static class that provides values and methods that are related to ANSI escape sequences, namely: CSI (Control Sequence Introducer) and it's subset, SGR (Select Graphic Rendition)
 *
 * See: https://en.wikipedia.org/wiki/ANSI_escape_code
 */
public final class ANSI {

	/** ANSI: Errase entire screen */
	static public final String ERRASE_ENTIRE_SCREEN = "[2J";
	/** ANSI: Move cursor to home position (column 1 row 1) */
	static public final String MOVE_CURSOR_TO_HOME = "[H";

	/** ANSI: Clear ANSI graphical formatting */
	static public final String CA = "[0m";
	/** ANSI: Make text bold or increased intensity */
	static public final String BOLD = "[1m";
	/** ANSI: Make text faint or decreased intensity */
	static public final String DIM = "[2m";
	/** ANSI: Make text italic */
	static public final String ITALIC = "[3m";
	/** ANSI: Make text underline */
	static public final String UNDERLINE = "[4m";

	/** ANSI: Foreground: Black */
	static public final String FG_BLACK = "[30m";
	/** ANSI: Foreground: Red */
	static public final String FG_RED = "[31m";
	/** ANSI: Foreground: GREEN */
	static public final String FG_GREEN = "[32m";
	/** ANSI: Foreground: Yellow */
	static public final String FG_YELLOW = "[33m";
	/** ANSI: Foreground: Blue */
	static public final String FG_BLUE = "[34m";
	/** ANSI: Foreground: Magenta */
	static public final String FG_MAGENTA = "[35m";
	/** ANSI: Foreground: Cyan */
	static public final String FG_CYAN = "[36m";
	/** ANSI: Foreground: White */
	static public final String FG_WHITE = "[37m";
	/** ANSI: Foreground: Terminal's Default foreground color */
	static public final String FG_TERM_DEFAULT = "[39m";

	/** ANSI: Background: Black */
	static public final String BG_BLACK = "[40m";
	/** ANSI: Background: Red */
	static public final String BG_RED = "[41m";
	/** ANSI: Background: GREEN */
	static public final String BG_GREEN = "[42m";
	/** ANSI: Background: Yellow */
	static public final String BG_YELLOW = "[43m";
	/** ANSI: Background: Blue */
	static public final String BG_BLUE = "[44m";
	/** ANSI: Background: Magenta */
	static public final String BG_MAGENTA = "[45m";
	/** ANSI: Background: Cyan */
	static public final String BG_CYAN = "[46m";
	/** ANSI: Background: White */
	static public final String BG_WHITE = "[47m";
	/** ANSI: Background: Terminal's Default background color */
	static public final String BG_TERM_DEFAULT = "[49m";

	/**
	 * Apply formatting on an input string, terminating with clearing all graphics<br><br>
	 *
	 * Can be described as: format + str + ANSI.CA;
	 */
	static public final String formatString(final String str, final String format) {
		return format + str + ANSI.CA;
	}
}
