package onion.lifeproducts.rms.presentation;

import java.util.Scanner;

/**
 * Class that holds a set of usefull properties/methods that helps keeping code clear,
 * structured, and organized by suppressing amount of noise in it.
 */
public final class Util {

	/** Scanner instance */
	static private final Scanner scanner = new Scanner(System.in);

	/**
	 * Output a message to the standard error stream with todo tag
	 * @param msg message to be shown
	*/
	static public final void TODO(String msg) {
		System.err.printf("[TODO]: %s\n", msg);
	}

	/**
	 * Output a message to the standard error stream with todo tag, and exit the program with the provided exit status
	 * @param msg message to be shown
	 * @param exitStatus exit status [0, 255]. Number out of this range will be wrapped around.
	*/
	static public final void TODO(String msg, int exitStatus) {
		Util.TODO(msg);
		System.exit(exitStatus);
	}

	/** Clear the screen, and move cursor to the top-left corner */
	static public final void clearScreen() {
		System.out.print(ANSI.ERRASE_ENTIRE_SCREEN + ANSI.MOVE_CURSOR_TO_HOME);
	}

	/**
	 * Get the line from the stdin<br><br>
	 * 
	 * Using default settings and no modifications
	 */
	static public String getLine() {
		return Util.scanner.nextLine().strip();
	}

	/**
	 * Get the line from the stdin<br><br>
	 * 
	 * Using default settings and no modifications
	 */
	static public String getLine(String prompt) {
		// this is where user input graphics comes in
		return Util.getLine(prompt, "");
	}

	/**
	 * Get the line from the stdin<br><br>
	 * 
	 * Using custom prompt and custom user input ANSI graphics
	 */
	static public String getLine(String prompt, String ansiUserInput) {
		// this is where user input graphics comes in
		System.out.printf("%s%s", prompt, ansiUserInput);
		final String in = Util.scanner.nextLine().strip();
		// dont forget to clear it, otherwise text will look weird
		System.out.print(ANSI.CA);
		return in;
	}

	/**
	 * Get the line from the stdin (valid non-empty string after striping)
	 * using 'getLine()' method<br><br>
	 * 
	 * Using default settings and no modifications
	 */
	static public String getLineAnswer() {
		String answer;
		while ((answer = Util.getLine()).isEmpty());
		return answer;
	}

	/**
	 * Get the line from the stdin (valid non-empty string after striping)
	 * using 'getLine()' method<br><br>
	 * 
	 * Providing parameter for prompt, and parameter to reset prompt after use
	 */
	static public String getLineAnswer(final String prompt, final String ansiUserInput) {
		String answer;
		while ((answer = Util.getLine(prompt, ansiUserInput)).isEmpty());
		return answer;
	}

	/**
	 * Get the line from the stdin (valid non-empty string after striping)
	 * using 'getLine()' method<br><br>
	 * 
	 * Providing parameter for prompt, and parameter to reset prompt after use
	 */
	static public String getLineAnswer(final String prompt) {
		String answer;
		while ((answer = Util.getLine(prompt, "")).isEmpty());
		return answer;
	}

}
