import onion.lifeproducts.rms.presentation.ConsoleUI;
import onion.lifeproducts.rms.presentation.Data;

import onion.lifeproducts.rms.presentation.ConsoleUIANSIOptions;
import onion.lifeproducts.rms.presentation.ANSI;
import onion.lifeproducts.rms.presentation.Util;

public class Main {
	public static void main(String[] args) {
		/// this will change the default options used inside ConsoleUI with constructor that does it
		// Util.changeConsoleUIANSIOptions(new ConsoleUIANSIOptions(ANSI.FG_GREEN));

		// this constructor will use default ANSI options from Util class
		ConsoleUI menu = new ConsoleUI(Data.mainMenuChoices);
		menu.run();
	}
}
