package onion.lifeproducts.rms.presentation;

import java.util.function.Consumer;

public final class ConsoleUIEntry implements Runnable {
	private String option;
	private String description;
	private String command;
	private Lambda<String> callback;

	/** List of all allowed commands that will be shown to the developer during testing process if provided command is incorrect.
	 * ConsoleUI must have mappings for all commands that are specified here. */
	static private final String[] allowedCommands = {
		"print:menu",
		"clear:screen",
		"exit"
	};

	/** Create new ConsoleUI entry<br><br>
	 *
	 * Provided with key, description, and a command what to do, checks will be made to ensure that the command is valid.<br>
	 * Option and description can have text wrapped in <h></h> tag to highlight specific text.<br>
	 * If command is invalid or null, the process will exit with exit code 50.<br><br>
	 *
	 * @param option - the option that user will type in from the CLI
	 * @param description - the description of the option (short)
	 * @param command - the command that will be localy processed by the ConsoleUI to create a local mapping of the local instance method to the key
	 * */
	public ConsoleUIEntry(String option, String description, String command) {
		this.option = option;
		this.description = description;
		this.command = command;

		if (command == null) {
			System.out.printf("[ConsoleUIEntry.<init>(\"%s\", \"%s\", %s)]: 'command' is null.\n", option, description, command);
			System.exit(50);
		}

		if (!command.matches("^(print:(menu)|clear:(screen)|exit)$")) {
			System.out.printf(
				"Incorrect command '%s'. Allowed commands: %s\n.",
				command,
				String.join(", ", ConsoleUIEntry.allowedCommands)
			);
			System.exit(50);
		}
	}
	
	/** Create new ConsoleUI entry<br><br>
	 *
	 * Provided with option, description, and a command what to do, checks will be made to ensure that the command is valid.<br>
	 * Option and description can have text wrapped in <h></h> tag to highlight specific text.
	 *
	 * If command is invalid or null, the process will exit with exit code 50.<br><br>
	 *
	 * @param option - the option that user will type in from the CLI
	 * @param description - the description of the option (short)
	 * @param callback - the callback that will be executed to invoke the logic associated with the option. Accepts no arguments.
	 * */
	public ConsoleUIEntry(String option, String description, Runnable callback) {
		this(option, description, new Lambda<>(callback));
	}

	/** Create new ConsoleUI entry given the consumer callback to accept the name of the option that was chosen<br><br>
	 * Option and description can have text wrapped in <h></h> tag to highlight specific text.<br>
	 *
	 * The option chosen will be passed to the consumer, allowing the callback to dynamically refer to the option that was chosen.
	 *
	 * @param option - the option that user will type in from the CLI
	 * @param description - the description of the option (short)
	 * @param callback - the callback that will be executed to invoke the logic associated with the option. Accepts a string as an option that was chosen.
	 * */
	public ConsoleUIEntry(String option, String description, Consumer<String> callback) {
		this(option, description, new Lambda<>(callback));
	}
	
	/** Create new ConsoleUI entry<br><br>
	 *
	 * Provided with key, description, and a callback that will be executed when the key is chosen.<br>
	 * The option chosen will be passed to the callback, allowing the callback to dynamically refer to the option that was chosen.<br><br>
	 * Option and description can have text wrapped in <h></h> tag to highlight specific text.<br>
	 * If callback is null, the process will exit with exit code 50.
	 *
	 * @param option - the option that user will type in from the CLI
	 * @param description - the description of the option (short)
	 * @param callback - the callback that will be executed to invoke the logic associated with the option. Accepts a string as an option that was chosen, or no parameters, wrapped in a custom Lambda<T> type.
	 * */
	public ConsoleUIEntry(String option, String description, Lambda<String> callback) {
		this.option = option;
		this.description = description;
		this.callback = callback;

		if (callback == null) {
			System.out.printf("[ConsoleUIEntry.<init>(\"%s\", \"%s\", %s)]: 'callback' is null.\n", option, description, callback);
			System.exit(50);
		}
	}

	/** Get the key of the entry */
	public final String option() { return this.option; }
	/** Get the description of the entry */
	public final String description() { return this.description; }
	/** Get the command of the entry<br><br>
	 *
	 * Note: It will return null if callback is specified for this entry. */
	public final String command() { return this.command; }
	/** Get the callback of the entry<br><br>
	 *
	 * Note: It will return null if command is specified for this entry. */
	public final Lambda<String> callback() { return this.callback; }

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(
			String.format("ConsoleUIEntry{option=\"%s\",description=\"%s\",", this.option, this.description)
		);

		if (this.command != null) {
			result.append(String.format("command=\"%s\"", this.command));
		} else {
			result.append(String.format("lambda=%s", this.callback));
		}

		result.append("}");

		return result.toString();
	}

	/** Run this entry if it was constructed with Lambda<String>, passing the option into the lambda */
	public void run() {
		if (this.callback != null) this.callback.run(this.option);
	}
}
