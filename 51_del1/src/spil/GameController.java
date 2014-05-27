/**
 * @author Gruppe 51_E13
 */

package spil;

import boundaryToMatador.*;

public class GameController {
	DiceCup gameDiceCup;
	private Player[] players;
	public static final int NUM_PLAYERS = 2;
	public static final int WIN_SCORE = 40;
	private boolean advancedRules;

	public GameController() {
		// Instantierer DiceCup og starter GUI
		gameDiceCup = new DiceCup();
		GUI.create("src/spil/fields2.txt");//For at fjerne felter fra spillepladen
		GUI.setDice(gameDiceCup.getDiceFaceValues()[0], gameDiceCup.getDiceFaceValues()[1]); //Pynt - l�ser ogs� problemet med at svartiden p� f�rste slag skal v�re s� lav som muligt
	}

	// k�rer spillet
	public void runGame() {
		GUI.showMessage("Velkommen til det fantastiske terningspil");

		String playAgain; //Er en string, da GUI'en returnerer den.
		do {
			playerSetup(); //Kalder privat metode der beder om spiller navne.
			selectRules(); //Kalder privat metode der beder om at v�lge avancerede eller simple regler.
			GUI.showMessage("Spillet starter");
			boolean winner = false;

			do {
				for (Player playerI : players) {

					if (advancedRules == false) {
						simplePlayerTurn(playerI); 
						if (playerI.getScore() >= WIN_SCORE) winner = true;
					}
					else 
						winner = advancedPlayerturn(playerI);
					System.out.println(playerI);
					if (winner == true) {
						GUI.showMessage(playerI.getPlayerName() + ", DU VANDT!");
						winner = true;
						break;
					}

				}
			} while (winner == false);

			playAgain = GUI.getUserButtonPressed("Spil Igen?", "Ja", "Nej");
			for (Player playerI : players) 
				playerI.setScore(0);
		} while (playAgain.equals("Ja"));
		GUI.close();

	}

	// K�rer en tur for en spiller
	private void simplePlayerTurn(Player player) {
		GUI.showMessage(player.getPlayerName() + ", det er din tur!");
		player.addToScore(gameDiceCup.rollDice()); // Sl�r med terningerne og l�gger summen til playerI's score
		GUI.setDice(gameDiceCup.getDiceFaceValues()[0], gameDiceCup.getDiceFaceValues()[1]); // Opdaterer GUI til at vise slaget
		GUI.showMessage(player.getPlayerName() + ", du har nu "+ player.getScore()); // Skriver PlayerI's score p� sk�rmen

	}
	// K�rer en avanceret turn
	private boolean advancedPlayerturn(Player player) {
		boolean winner = false;
		GUI.showMessage(player.getPlayerName() + ", det er din tur!");
		do {
			System.out.println(player); //Udskriver player til konsollen
			player.addToScore(gameDiceCup.rollDice()); // Sl�r med terningerne og l�gger summen til playerI's score
			GUI.setDice(gameDiceCup.getDiceFaceValues()[0],	gameDiceCup.getDiceFaceValues()[1]); // Opdaterer GUI til at vise slaget
			switch (gameDiceCup.getTwoOfAKind()){
			case 0:
				GUI.showMessage(player.getPlayerName() + ", du har nu " + player.getScore()+ "points");
				break;
			case 1:
				player.setScore(0);
				GUI.showMessage(player.getPlayerName() + ", �rgeligt! to ettere! du mister alle points og har nu " + player.getScore()+ "points");
				break;
			case 6:
				if (player.getLastRoll()[0] == 6 && player.getLastRoll()[1]==6) {
					GUI.showMessage(player.getPlayerName() + ", tillykke! to seksere - to gange i tr�k");
					return true;
				} else 
					GUI.showMessage(player.getPlayerName() + ", du slog to ens!! du f�r et ekstra kast og har nu " + player.getScore() + "points");
				break;
			default:
				if (player.getScore()<40) GUI.showMessage(player.getPlayerName() + ", du slog to ens!! du f�r et ekstra kast og har nu " + player.getScore() + "points");// Skriver PlayerI's score p� sk�rmen
				else  return true;
			}
			player.setLastRoll(gameDiceCup.getDiceFaceValues());
		} while (gameDiceCup.getTwoOfAKind()>0);
		return(winner);

	}
	private void playerSetup() {
		players = new Player[NUM_PLAYERS];
		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(GUI.getUserString("Indtast Spiller " + (i + 1) + " navn"));
		}

	}

	// s�tter reglerne for spillet
	public void selectRules() {
		String ruleSet = GUI.getUserSelection("spil med", "almindelige regler", "avancerede regler");
		if (ruleSet.equals("almindelige regler")) advancedRules = false; //rettet equals
		else advancedRules = true;

	}
}
