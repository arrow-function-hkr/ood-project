package onion.lifeproducts.rms.presentation;

public final class ConsoleUIEntry {
	private String key;
	private String description;
	private String command;
	private Runnable callback;

	static private final String[] allowedCommands = {
		"print:menu",
		"clear:screen",
		"exit"
	};

	public ConsoleUIEntry(String key, String description, String command) {
		this.key = key;
		this.description = description;
		this.command = command;

		if (command == null) {
			System.out.println("[ConsoleUIEntry.<init>]: 'command' is null.");
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
	
	public ConsoleUIEntry(String key, String description, Runnable callback) {
		this.key = key;
		this.description = description;
		this.callback = callback;
	}

	public String key() { return this.key; }
	public String description() { return this.description; }
	public String command() { return this.command; }
	public Runnable callback() { return this.callback; }
}
