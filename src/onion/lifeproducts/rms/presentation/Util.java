package onion.lifeproducts.rms.presentation;

import java.util.Scanner;

/**
 * Class that holds a set of usefull properties/methods that helps keeping code clear,
 * structured, and organized by suppressing amount of noise in it.
 */
public final class Util {

	/** Scanner instance */
	static private final Scanner scanner = new Scanner(System.in);

	/** Clear the screen, and move cursor to the top-left corner */
	static public final void clearScreen() {
		System.out.print(ANSI.ERRASE_ENTIRE_SCREEN + ANSI.MOVE_CURSOR_TO_HOME);
	}

	static public ConsoleUIANSIOptions consoleUIANSIOptions = new ConsoleUIANSIOptions();

	/** Change default ANSI options for the {@link ConsoleUI} class and {@link ConsoleUIEntry} options to use.<br>
	 * Modifies the global state of the static field {@link Util.consoleUIANSIOptions}. */
	public Util(ConsoleUIANSIOptions consoleUIANSIOptions) {
		Util.consoleUIANSIOptions = consoleUIANSIOptions;
	}

	/** Change default ANSI options for the {@link ConsoleUI} class and {@link ConsoleUIEntry} options to use.<br>
	 * Modifies the global state of the static field {@link Util.consoleUIANSIOptions}. */
	static public final void changeConsoleUIANSIOptions(ConsoleUIANSIOptions consoleUIANSIOptions) {
		Util.consoleUIANSIOptions = consoleUIANSIOptions;
	}

	/**
	 * Get the line from the stdin<br><br>
	 * 
	 * Using default settings and no modifications
	 */
	static public final String getLine() {
		return Util.scanner.nextLine().strip();
	}

	/**
	 * Get the line from the stdin<br><br>
	 * 
	 * Using default settings and no modifications
	 */
	static public final String getLine(String prompt) {
		// this is where user input graphics comes in
		return Util.getLine(prompt, "");
	}

	/**
	 * Get the line from the stdin<br><br>
	 * 
	 * Using custom prompt and custom user input ANSI graphics
	 */
	static public final String getLine(String prompt, String ansiUserInput) {
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
	static public final String getLineAnswer() {
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
	static public final String getLineAnswer(final String prompt, final String ansiUserInput) {
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
	static public final String getLineAnswer(final String prompt) {
		String answer;
		while ((answer = Util.getLine(prompt, "")).isEmpty());
		return answer;
	}

}
