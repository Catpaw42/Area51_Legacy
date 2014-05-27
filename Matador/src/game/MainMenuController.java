package game;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


//Class to handle Language selection and possibly further options.
public class MainMenuController {
	//TODO Move setup functions from Decorator to here
	private JFrame menuFrame;
	private Object[] languageOptions = { "English", "Danish", "Faroese" };
	//private Object[] numOptions = {"2","3","4","5","6"};
	private String language;
	//private int numPlayers;
	private GameController gameController;
	
	public MainMenuController() {
		super();
		this.menuFrame = new JFrame();
	}

	public void runProgram (){
		language = getLanguage().toLowerCase();
		System.out.println(language);
		this.gameController = new GameController(language); 
		gameController.setupGame();
		gameController.runGame();
	}
	
	private String getLanguage () {
		String lang = (String)  JOptionPane.showInputDialog(menuFrame,
				"select language", "Language", JOptionPane.PLAIN_MESSAGE, null,
				languageOptions, "English");
		if (lang == null)
		{
			//someone pressed the windows "close" or the "cancel" button on the selection window
			//instead of selecting a language. We terminate the program.
			System.err.println("No language was selected, exiting program");
			//abandon ship!!
			System.exit(1);
		}
		return lang;
		
	}
	
/* Experimental method
	private int getNumPlayers() {
		String numPlayers = (String)  JOptionPane.showInputDialog(menuFrame,
				"select language", "Language", JOptionPane.PLAIN_MESSAGE, null,
				numOptions, "English");
		if (numPlayers == null)
		{
			//someone pressed the windows "close" or the "cancel" button on the selection window
			//instead of selecting a language. We terminate the program.
			System.err.println("No number of players was selected, exiting program");
			//abandon ship!!
			System.exit(1);
		}
		return Integer.parseInt(numPlayers);
	}
*/
	//Test Driver Method
	public static void main(String[] args){
		MainMenuController testController = new MainMenuController();
		String language = testController.getLanguage();
		System.out.println(language);
	}
	
}
