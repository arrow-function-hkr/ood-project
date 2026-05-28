package onion.lifeproducts.rms.presentation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;

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

	// global-wide use
	static public ConsoleUIANSIOptions defaultConsoleUIANSIOptions = new ConsoleUIANSIOptions();
	// instance use
	public ConsoleUIANSIOptions consoleUIANSIOptions = defaultConsoleUIANSIOptions;

	public Util() {}

	/** Change default ANSI options for the {@link ConsoleUI} class and {@link ConsoleUIEntry} options to use.<br>
	 * Modifies the global state of the static field {@link Util.defaultConsoleUIANSIOptions}. */
	public Util(final ConsoleUIANSIOptions consoleUIANSIOptions) {
		this.consoleUIANSIOptions = consoleUIANSIOptions;
	}

	/**
	 * Get the line from the stdin<br><br>
	 *
	 * Using default settings and no modifications
	 */
	static public final String getLine() {
		if (Util.scanner.hasNextLine())
			return Util.scanner.nextLine().strip();

		System.out.println(
			"\n" +
			ANSI.formatString("[INTERNAL]", ANSI.FG_RED) +
			" No standard input left. Exiting the process..."
		);
		System.exit(0); // gracefully exit the process if encountered EOF

		return ""; // will never happen, but to satisfy java compiler
	}

	/**
	 * Get the line from the stdin<br><br>
	 *
	 * Using default settings and no modifications
	 */
	static public final String getLine(final String prompt) {
		// this is where user input graphics comes in
		return Util.getLine(prompt, "");
	}

	/**
	 * Get the line from the stdin<br><br>
	 *
	 * Using custom prompt and custom user input ANSI graphics
	 */
	static public final String getLine(final String prompt, final String ansiUserInput) {
		// this is where user input graphics comes in
		System.out.printf("%s%s", prompt, ansiUserInput);
		final String in = Util.getLine();
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


	static public final String getLineAnswer(
		final String prompt,
		final String ansiUserInput,
		final Predicate<String> fn
	) {
		String answer;
		while ((answer = Util.getLine(prompt, ansiUserInput)).isBlank() || !fn.test(answer));
		return answer;
	}

	/**
	 * Get the line from the stdin (valid non-empty string after striping) using
	 * 'getLine()' method
	 * <br>
	 * <br>
	 * Providing prompt and predicate function callback to check if the
	 * given user input fits the requirements
	 */
	static public final String getLineAnswer(
		final String prompt,
		final Predicate<String> fn
	) {
		return Util.getLineAnswer(prompt, "", fn);
	}

	/**
	 * Get the line from the stdin (valid non-empty string after striping) using
	 * 'getLine()' method
	 * <br>
	 * <br>
	 * Providing predicate function callback to check if the
	 * given user input fits the requirements
	 */
	static public final String getLineAnswer(final Predicate<String> fn) {
		return Util.getLineAnswer("", "", fn);
	}

	static <T> T[] concatArraysWithCopy(final T[] array1, final T[] array2) {
		final T[] result = Arrays.copyOf(array1, array1.length + array2.length);
		System.arraycopy(array2, 0, result, array1.length, array2.length);
		return result;
	}

	/** Apply the ANSI highlights on a given string with text wrapped in `<h></h>` tag. */
	static public String formatHighlightString(final String format, final String ansi) {
		return format.replaceAll(
			"(?<!\\\\)<h>(.*)</h>",
			String.format(
				"%s$1%s",
				ansi,
				ANSI.CA
			)
		);
	}

	static public String formatHighlightString(final String format) {
		return Util.formatHighlightString(format, Util.defaultConsoleUIANSIOptions.TEXT_HIGHLIGHT);
	}

	/** Print the given string to the stdout highlighting the text in `<h></h>` tag with default highlight graphics. */
	static public void print(String string) {
		Util.print(string, Util.defaultConsoleUIANSIOptions.TEXT_HIGHLIGHT);
	}
	
	/** Print the given string to the stdout highlighting the text in `<h></h>` tag with custom highlight graphics. */
	static public void print(String string, String ansi) {
		System.out.print(Util.formatHighlightString(string, ansi));
	}

	/** Print the given string to the stdout with newline highlighting the text in `<h></h>` tag with default highlight graphics. */
	static public void println(String string) {
		Util.print(string + "\n", Util.defaultConsoleUIANSIOptions.TEXT_HIGHLIGHT);
	}
	
	/** Print the given string to the stdout with newline highlighting the text in `<h></h>` tag with custom highlight graphics. */
	static public void println(String string, String ansi) {
		Util.print(string + "\n", ansi);
	}

	/** Print the given string to the stdout with newline highlighting the text in `<h></h>` tag with default highlight graphics. */
	static public void printf(String format, Object... args) {
		Util.print(String.format(format, args), Util.defaultConsoleUIANSIOptions.TEXT_HIGHLIGHT);
	}

	static public boolean isLeapYear(int year) {
		return (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);
	}

	static public boolean isValidDate(String date) {
		if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			System.out.println("date doesn't match the regex");
			return false;
		}

		int
			year = Integer.parseInt(date.substring(0, 4)),
			month = Integer.parseInt(date.substring(5, 7)),
			day = Integer.parseInt(date.substring(8, 10))
		;
		LocalDateTime currentDate = LocalDateTime.now();

		if (
			month < 1 || month > 12 || day < 1 ||
			year < currentDate.getYear() ||        // year  in the past
			month < currentDate.getMonthValue() || // month in the past
			(                                      // day   in the past
				day < currentDate.getDayOfMonth() &&      
				month == currentDate.getMonthValue()
			)
		) return false;

		switch (month) {
			case 1: // jan
			case 3: // mar
			case 5: // may
			case 7: // jul
			case 8: // aug
			case 10: // oct
			case 12: // dec
				if (day > 31) return false;
				break;

			case 4: // apr
			case 6: // jun
			case 9: // sep
			case 11: // nov
				if (day > 30) return false;
				break;

			case 2: // feb
				if (isLeapYear(year) ? day > 29 : day > 28) return false;
				break;

			default: return false; // will never happen, but just in case
		}

		return true;
	}
}
