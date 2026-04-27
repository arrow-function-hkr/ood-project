package onion.lifeproducts.rms.presentation;

public final class ConsoleUIEntry {
	private String key;
	private String description;
	private String command;
	private Runnable callback;

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
	 * If command is invalid or null, the process will exit with exit code 50. */
	public ConsoleUIEntry(String key, String description, String command) {
		this.key = key;
		this.description = description;
		this.command = command;

		if (command == null) {
			System.out.printf("[ConsoleUIEntry.<init>(\"%s\", \"%s\", %s)]: 'command' is null.\n", key, description, command);
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
	 * Provided with key, description, and a callback that will be executed when the key is chosen.<br>
	 * If callback is null, the process will exit with exit code 50. */
	public ConsoleUIEntry(String key, String description, Runnable callback) {
		this.key = key;
		this.description = description;
		this.callback = callback;

		if (callback == null) {
			System.out.printf("[ConsoleUIEntry.<init>(\"%s\", \"%s\", %s)]: 'callback' is null.\n", key, description, callback);
			System.exit(50);
		}
	}

	/** Get the key of the entry */
	public final String key() { return this.key; }
	/** Get the description of the entry */
	public final String description() { return this.description; }
	/** Get the command of the entry<br><br>
	 *
	 * Note: It will return null if callback is specified for this entry. */
	public final String command() { return this.command; }
	/** Get the callback of the entry<br><br>
	 *
	 * Note: It will return null if command is specified for this entry. */
	public final Runnable callback() { return this.callback; }
}
