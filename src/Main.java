import onion.lifeproducts.rms.presentation.ConsoleUI;
import onion.lifeproducts.rms.presentation.Data;

public class Main {
	public static void main(String[] args) {
		ConsoleUI menu = new ConsoleUI(Data.mainMenuChoices);
		menu.run();
	}
}
