package spil;

import java.awt.Color;

public class GameController {
	private int startBalance;
	private int playersLeft; 	//could be removed, but neccesitates that you check all 
	//players active status
	private static final int DEFAULT_START_BALANCE = 30000;
	private DiceCup gameDiceCup = new DiceCup();
	private Board gameBoard = new Board(gameDiceCup);
	private Player[] players;

	public GameController(int startBalance) {
		// Setup GUI and starting balance for players
		Decorator.setupGUI(gameBoard.getFields());
		this.startBalance = startBalance;
	}

	public GameController() {
		// Overload with default starting balance
		this(DEFAULT_START_BALANCE);
	}

	public void runGame() {
		// Initialization - player setup
		Decorator.showMessage("welcomeMessage");
		playerSetup();
		Decorator.showMessage("startMessage");
		
		// Main game loop - ends when there is only one player left
		do {
			//Iterate player array
			for (Player player : players) {
				//Check if player is still in the game.
				if (player.getActive() == true) {
					playerTurn(player);
					//Check if player went bankrupt during turn
					if (!player.getActive()) playersLeft = playersLeft - 1;	
					//Check if there is only one player left.
					if (playersLeft ==1) break;
				}
			}
		} while (playersLeft > 1);
		// System.out.println("Determine winner");
		for (Player winningPlayer : players)
			{
			if (winningPlayer.getActive()){
				Decorator.showMessage("congratsMessage", winningPlayer.getPlayerName(), "winnerMessage");
			}
			break;
		}
		Decorator.close();
	}

	private void playerTurn(Player player)
	{
		Decorator.showMessage(player.getPlayerName(), "playersTurnMessage");
		movePlayer(player, gameDiceCup.rollDice());
		Decorator.setDice(gameDiceCup.getDiceFaceValues()[0],
				gameDiceCup.getDiceFaceValues()[1]);
		Decorator.setCar(player.getCurrentFieldNumber()+1 ,player.getPlayerName());
		gameBoard.getFields()[player.getCurrentFieldNumber()].landOnField(player);
		Decorator.setBalance(player.getPlayerName(), player.getAccount()
				.getBalance());
	}


	/** Setup players
	 * Asks for input for number of players. Initializes player array
	 * 
	 */
	private void playerSetup() {
		// you have option to select between 2 and 6 players
		String selectNumberOfPlayers = Decorator.getUserSelection(
				"chooseNumPlayersMessage", "2", "3","4", "5", "6");
		int numberOfPlayers = Character.getNumericValue(selectNumberOfPlayers.charAt(0));
		//Initialize player array and set number of players left in game
		players = new Player[numberOfPlayers];
		playersLeft = numberOfPlayers;
		//Colors for cars
		Color[] colors = {Color.CYAN, Color.PINK,Color.MAGENTA,Color.RED,Color.ORANGE,Color.DARK_GRAY};
		// Ask for names for players and setup players with starting balance and add them to the GUI.
		for (int playerNumber = 0; playerNumber < players.length; playerNumber++)
		{	//TODO restrict length of playername to fx max 20 characters 	
			players[playerNumber] = new Player(Decorator.getUserString("player"
					, String.valueOf(playerNumber + 1) , "enterNameMessage"), startBalance);
			Decorator.addPlayer(players[playerNumber].getPlayerName(),
					players[playerNumber].getAccount().getBalance(),colors[playerNumber]);
		}
	}

	private void movePlayer(Player player, int sum) {
		int currentField = player.getCurrentFieldNumber();
		player.setCurrentFieldNumber((currentField + sum)
				% gameBoard.getFields().length);
	}
}
