package spil;


public class GameController
{
	private DiceCup gameDiceCup;
	private Player[] players;
	private Board gameBoard;
	private Decorator gameDecorator;
	private int winScore, startScore;

	// Default values
	public static final int DEFAULT_NUM_PLAYERS = 2;
	public static final int DEFAULT_WIN_SCORE = 3000;
	public static final int DEFAULT_START_SCORE = 1000;

	public GameController(int numPlayers, int winScore, int startScore)
	{
		// Instantiates DiceCup
		gameDiceCup = new DiceCup();
		gameBoard = new Board();
		gameDecorator = new Decorator(gameBoard.getFields());
		players = new Player[numPlayers];
		this.winScore = winScore;
		this.startScore = startScore;
	}

	public GameController()
	{
		this(DEFAULT_NUM_PLAYERS, DEFAULT_WIN_SCORE, DEFAULT_START_SCORE);
		
	}

	public void runGame()
	{
		gameDecorator.showMessage("WelcomeMessage"); 


		do //playAgainLoop
		{
			// Call a private method that asks for player names
			playerSetup(); 
			gameDecorator.showMessage("Startmessage");

			Player[] winners = new Player[players.length];
			int numberOfLosers = 0;
			int numberOfWinners = 0;
			do // gameLoop
			{
				//loop over all players, so that they all get their turn.
				//if you are bankrupt from a PREVIOUS turn, you do not get any further turns. (handles more than two players)
				for (int i = 0; i < players.length; i++) 
				{
					if(players[i].getWinStatus() != -1) // If player[i] hasn't lost already he gets a turn
					{
							players[i].setWinStatus(playerTurn(players[i]));
					}

				}
				//loop over all players, to check for any winners / losers
				for (int i = 0; i < players.length; i++)
				{
					if(players[i].getWinStatus() == 1)
						winners[0] = players[i];
					if(players[i].getWinStatus() == -1)
						numberOfLosers ++;
				}
				//if ALL but one player has gone bankrupt
				if(numberOfLosers == players.length -1)
				{
					//find remaining player and set him as winner.
					for (int i = 0; i < players.length; i++)
					{
						if(players[i].getWinStatus() != -1)
							winners[0] = players[i];

					}
				}

				if(winners[0] != null)
				{
					players = SortPlayers.mergeSort(players);

					for (int i = players.length -1; i >= 0 ; i--)
					{
						if(players[i].getAccount().getScore() == players[players.length-1].getAccount().getScore())
							numberOfWinners ++;
					}
					int i = players.length - 1;
					int j = 0;
					while(j < numberOfWinners)
					{
						winners[j] = players[i];
						i--;
						j++;
					}
				}

			} while (winners[0] == null);

			if (numberOfWinners > 1)
			{
				String winnerString = "";
				for (int i = 0; i < numberOfWinners; i++)
				{
					winnerString += winners[i].getPlayerName() + ", ";
				}

				gameDecorator.showMessage("tieMessage", winnerString.substring(0, winnerString.length()-2));
			}
			else
			{
				gameDecorator.showMessage("congratsMessage", winners[0].getPlayerName() ,"youWon");
			}

		} while (gameDecorator.getUserButtonPressed("playAgainMessage", "Yes", "No").equals("Yes"));
		gameDecorator.close();

	}




	// Runs player turn
	private int playerTurn(Player player)
	{
		boolean extraTurn = false;
		int resultOfTurn = 0;
		do
		{
			extraTurn = false;
			gameDecorator.showMessage(player.getPlayerName(), ", ", "yourTurnMessage");

			// Roll the dice and get the corresponding field
			Field currentField = gameBoard.getField(gameDiceCup.rollDice() - 2);

			//show the roll on the GUI
			gameDecorator.setDice(gameDiceCup.getDiceFaceValues()[0],gameDiceCup.getDiceFaceValues()[1]); 

			//move the car on GUI
			gameDecorator.setCar(gameDiceCup.getSum() - 1, player.getPlayerName());
			
			//Pass the player to the field, invoking playerAction method on field, 
			//which in turn updates players account, and returns an int describing result of playerAction;
			resultOfTurn = currentField.playerAction(player); 
			
			//update balance on GUI
			gameDecorator.setBalance(player.getPlayerName(), player.getAccount().getScore());
			System.out.println(resultOfTurn);
			switch (resultOfTurn)
			{
			case 0:
				// extratur, withdraw er fejlet
				// extraTurn = true; //no longer relevant, must still be caught.
			case -1:
				// ingen extratur, withdraw er fejlet (Bleeds through from case 0).
				gameDecorator.showMessage(player.getPlayerName(), ", ", "lessThanZeroMessage");
				break;
			case 2:
				// extratur, withdraw er succeeded
				extraTurn = true;
			case 1:
				// ingen extratur, withdraw er succeeded (bleeds through from case 2)
				gameDecorator.showMessage(player.getPlayerName(), ", ", "youNowHaveMessage", ""+player.getAccount().getScore() , "points." , (extraTurn ? "extraTurnMessage" : ""));
				break;
			default:
				break;
			}
		} while (extraTurn);

		//three possible endings:

		//(-1) the call to field returned that there was not enough money, = broke
		if(resultOfTurn == -1)
		{
			return -1;
		}
		//(1) the call to field succeeded (extra turn success handled in loop), check for win-condition
		else if(resultOfTurn == 1)
		{
			//did he win the game?
			if(player.getAccount().getScore() >=winScore)
			{
				return 1;
			}
			//if he did'nt win the game
			return 0;
		}
		//Not for use, only to satisfy the compiler(it does not know that we MUST hit one of the if statements)
		//ergo seeing this return signifies a bug in the program.
		return -2000;


	}

	//
	private void playerSetup()
	{
		for (int i = 0; i < players.length; i++)
		{
			players[i] = new Player(gameDecorator.getUserString("enterPlayerMessage" , ""+(i + 1) , "nameMessage"));
			try {
				players[i].getAccount().setScore(startScore);
			} catch (Exception e) {
				e.printStackTrace();
			}
			gameDecorator.addPlayer(players[i].getPlayerName(), players[i].getAccount().getScore());

		}

	}

}
