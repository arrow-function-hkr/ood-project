package onion.lifeproducts.rms.presentation;


/** Static class to hold usefull methods to check {@link ConsoleUIEntry}
 * entries before main execution happens to prevent undefined behavior at runtime. */
public final class ConsoleUIEntryChecker {

	/** Perform a check that all {@link ConsoleUIEntry} entries are valid.<br>
	 * If at least one entry is invalid, the process will be terminaled to prevent undefined behavior. */
	static public final void checkConsoleUIEntryOptions(ConsoleUIEntry[] consoleUIEntries) {
		for (int i = 0, length = consoleUIEntries.length; i < length; i++) {

			ConsoleUIEntry entry = consoleUIEntries[i];

			String key = entry.key(),
				   description = entry.description(),
				   command = entry.command();

			Runnable callback = entry.callback();

			// check that all values in entry are not null
			if (
					key == null || description == null ||
					(callback == null && command == null)
			) {
				System.out.printf("[ConsoleUIEntryChecker]: choice entry #%d has null values. Aborting...\n", i);
				System.exit(2);

			// check that all entries are of correct types
			} else if (!(key instanceof String)) {
				System.out.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no string value for field 'key'. Aborting...\n", i
				);
				System.exit(2);
			} else if (!(description instanceof String)) {
				System.out.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no string value for field 'description'. Aborting...\n", i
				);
				System.exit(2);
			} else if (command == null && !(callback instanceof Runnable)) {
				System.out.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no 'Runnable' value for field 'callback'. Aborting...\n", i
				);
				System.exit(2);
			} else if (callback == null && !(command instanceof String)) {
				System.out.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no string value for field 'command'. Aborting...\n", i
				);
				System.exit(2);
			}
		}
	}
	
}
