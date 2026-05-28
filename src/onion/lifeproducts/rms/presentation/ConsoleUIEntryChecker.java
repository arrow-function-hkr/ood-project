package onion.lifeproducts.rms.presentation;

import java.util.HashMap;

/** Static class to hold usefull methods to check {@link ConsoleUIEntry}
 * entries before main execution happens to prevent undefined behavior at runtime. */
public final class ConsoleUIEntryChecker {

	/** Perform a check that all {@link ConsoleUIEntry} entries are valid.<br>
	 * If at least one entry is invalid or there are some issues,
	 * false boolean value is returned to indicate incorrect entry
	 * (supported with error message in the standard eror stream). */
	static public final boolean checkConsoleUIEntryOptions(ConsoleUIEntry[] consoleUIEntries) {
		HashMap<String, Integer> seen = new HashMap<>();

		for (int i = 0, length = consoleUIEntries.length; i < length; i++) {

			ConsoleUIEntry entry = consoleUIEntries[i];

			String
				option = entry.option(),
				description = entry.description(),
				command = entry.command();

			if (seen.containsKey(option)) {
				System.err.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d (%s) has duplicate option of choise entry #%d.\n",
					i,
					option,
					seen.get(option)
				);
				return false;
			}

			seen.put(option, i);

			Lambda<String> callback = entry.callback();

			// check that all values in entry are not null
			if (
					option == null || description == null ||
					(callback == null && command == null)
			) {
				System.err.printf("[ConsoleUIEntryChecker]: choice entry #%d has null values.\n", i);
				return false;

			// check that all entries are of correct types
			} else if (!(option instanceof String)) {
				System.err.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no string value for field 'key'.\n", i
				);
				return false;
			} else if (!(description instanceof String)) {
				System.err.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no string value for field 'description'.\n", i
				);
				return false;
			} else if (command == null && !(callback instanceof Lambda)) {
				System.err.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no 'Lambda' value for field 'callback'.\n", i
				);
				return false;
			} else if (callback == null && !(command instanceof String)) {
				System.err.printf(
					"[ConsoleUIEntryChecker]: choice entry #%d has no string value for field 'command'.\n", i
				);
				return false;
			}
		}
		return true;
	}
	
}
