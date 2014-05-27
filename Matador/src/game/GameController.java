package game;

import game.Account.IllegalAmountException;
import game.Account.InsufficientFundsException;
import game.cards.CardController;

public class GameController {
	private static final int DEFAULT_START_BALANCE = 30000;
	private static final int DEFAULT_NUMBER_OF_FIELDS = 40;
	private Decorator decorator;
	private BoardController boardController;
	private PlayerController playerController;
	private CardController cardController;
	private DiceCup dicecup;

	public GameController(String language) {
		super();
		this.decorator = new Decorator(language);
		this.dicecup = DiceCup.getInstance();
		this.boardController = new BoardController(DEFAULT_NUMBER_OF_FIELDS);
		this.cardController= new CardController();
		this.playerController = new PlayerController(DEFAULT_START_BALANCE, DEFAULT_NUMBER_OF_FIELDS);
	}
	public void setupGame() {
		decorator.setupGUI(BoardController.getBoard().getFields(), null);

		playerController.playerSetup(this, decorator, DEFAULT_START_BALANCE);
		decorator.setupGUI(BoardController.getBoard().getFields(), playerController.getPlayers());
	}

	public void runGame () {
		while (playerController.getNumberOfPlayersLeft() > 1) {
			for (Player activePlayer : playerController.getPlayers()) {
				//check if player is in game
				if (!activePlayer.isBroke()) {
					if (activePlayer.getInJail() != 0) {
						jailTurn(activePlayer);
					} else {
						playerTurn(activePlayer);
					}
				}
				if (playerController.getNumberOfPlayersLeft() <= 1) {
					break;
				}
			}
			//Main GameLoop
		}
		for (Player winner : playerController.getPlayers()){
			if(!winner.isBroke()){
				decorator.showMessage(new String[]{"Congratulations",", ", winner.getPlayerName(), "YouWon"});	
			}
		}
		System.exit(1);
		
	}

	
	private void playerTurn(Player activePlayer) {
		activePlayer.setTwoOfAKindCount(0);
		do {
			//Slå med terninger og updater GUI
			decorator.showMessage(new String[]{activePlayer.getPlayerName(), "- ","RollDice","!"});
			// Ornamentation
			for (int i = 0;i<8;i++){
				dicecup.rollDice();
				decorator.updateDice(dicecup);
				try {
					Thread.sleep(100);
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			//endOf Ornamentation - real diceRoll...
			int diceRoll = dicecup.rollDice();
			decorator.updateDice(dicecup);
			twoOfAKindCheck(activePlayer);
			playerController.move(activePlayer, diceRoll, decorator,
					playerController,  cardController,  boardController);
			decorator.updatePlayer(activePlayer);

			//Delegates control to boardController...
			//boardController.landOnField(activePlayer,
			//					activePlayer.getCurrentFieldNumber(), decorator,
			//					playerController, cardController);
			if (activePlayer.getInJail()==0) {
				//Offer Player to buy houses and trade
				int UserSelection = 0;
				String[] msg = new String[] { "OfferBuyingTrading" };
				String[] options = new String[] { "NextTurn", "BuyHouses",
				"TradePawn" };
				do {
					UserSelection = decorator
							.getUserButtonPressed(msg, options);
					if (UserSelection == 2) {
						System.out.println("trading");
						playerController.trade(activePlayer,decorator);
					}
					if (UserSelection ==1){
						System.out.println("Buying Houses");
						playerController.buyHouse(activePlayer, decorator);
					}
				} while (UserSelection != 0);
			}
		} while (activePlayer.getTwoOfAKindCount()>0&&activePlayer.getTwoOfAKindCount()<3);
	}
	
	private void twoOfAKindCheck(Player activePlayer) {
		if (dicecup.getTwoOfAKind()!=0) {
			activePlayer.setTwoOfAKindCount(activePlayer.getTwoOfAKindCount()+1);
		}else{
			activePlayer.setTwoOfAKindCount(0);
		}
		if (activePlayer.getTwoOfAKindCount() >= 3){
			decorator.showMessage(new String[] {"TooManyOneOfAKind","GoToJail"});
			playerController.moveToJail(activePlayer, boardController.getJailNumber());
			activePlayer.setInJail(1);
		} 
		if (activePlayer.getTwoOfAKindCount() > 0 && activePlayer.getTwoOfAKindCount() < 3){
			decorator.showMessage(new String[] {activePlayer.getPlayerName(),"TwoOfAKindExtraTurn"});
		}
	}
	//TODO Refactor - extract method
	private void afterJailTurn(Player activePlayer, int diceRoll){
		decorator.showMessage(new String[] {activePlayer.getPlayerName(),"TwoOfAKindExtraTurn"});
		playerController.move(activePlayer, diceRoll, decorator,
				playerController,  cardController,  boardController);
		decorator.updatePlayer(activePlayer);

		//Offer Player to buy houses and trade
		int UserSelection = 0;
		String[] msg = new String[] { "OfferBuyingTrading" };
		String[] options = new String[] { "NextTurn", "BuyHouses",
		"TradePawn" };
		do {
			UserSelection = decorator
					.getUserButtonPressed(msg, options);
			if (UserSelection == 2) {
				System.out.println("trading");
				playerController.trade(activePlayer,decorator);
			}
		} while (UserSelection != 0);
		if(activePlayer.getTwoOfAKindCount() == 2){
			playerTurn(activePlayer);
		}
	}



	//
	private void jailTurn(Player activePlayer) {
		if(activePlayer.getInJail() >= 3){
			boolean raisedMoney = false;
			while(!raisedMoney){
				try {
					activePlayer.getAccount().withdraw(boardController.getJail().getBail());
					raisedMoney = true;
				} catch (InsufficientFundsException e) {
					if(playerController.handleInsufficientFunds(activePlayer, boardController.getJail().getBail(), decorator)){
						raisedMoney = true;
					}
					else{
						playerController.hostileTakeOver(null, activePlayer);
					}

				}
				catch (IllegalAmountException e) {
					System.err.println("IllegalAmount - Jailturn");
					e.printStackTrace();
				}
			}
			activePlayer.setInJail(0);
			String[] msg2 = new String[] {"CongratsYouAreFree"};
			decorator.showMessage(msg2);
			playerTurn(activePlayer);
		} else {
			String[] msg = new String[] {"YouAreInJail"};
			String[] options = new String[] {};				
			options = StringTools.add(options, new String[] {"RollDice"});
			if(activePlayer.getAccount().getBalance() >= boardController.getJailNumber()){
				options = StringTools.add(options, new String[]{"BuyOut"});
			}
			if(activePlayer.getNumberOfJailCards()>0){
				options = StringTools.add(options, new String[]{"UsePardon"});
			}
			int jailChoice = decorator.getUserButtonPressed(msg, options);
			if (jailChoice == 0){
				int diceRoll =	dicecup.rollDice();
				decorator.updateDice(dicecup);

				if (dicecup.getTwoOfAKind()!=0) {
					activePlayer.setTwoOfAKindCount(activePlayer.getTwoOfAKindCount()+1);
					activePlayer.setInJail(0);
					afterJailTurn(activePlayer, diceRoll);
					String[] msg3 = new String[]{"CongratsYouAreFree"};
					decorator.showMessage(msg3);
				} else{
					activePlayer.setInJail(activePlayer.getInJail() + 1);
				}

			}
			else if (jailChoice == 1){
				try {
					activePlayer.getAccount().withdraw(boardController.getJail().getBail());
				} catch (Exception e) {
					e.printStackTrace();
				}
				activePlayer.setInJail(0);
				String[] msg1 = new String[] {"CongratsYouAreFree"};
				decorator.showMessage(msg1);
				playerTurn(activePlayer);
			}
			else if (jailChoice == 2){
				activePlayer.setNumberOfJailCards(activePlayer.getNumberOfJailCards() - 1);
				activePlayer.setInJail(0);
				String[] msg3 = new String[]{"CongratsYouAreFree"};
				decorator.showMessage(msg3);
				cardController.useJailCard(activePlayer);
				playerTurn(activePlayer);
			}
		}
	}

	public static void main(String[] args){
		GameController testController = new GameController("danish");
		Decorator testdc = testController.decorator;

		CardController testCC = testController.cardController;
		BoardController testBC = testController.boardController;

		//testController.setupGame();
		//TestFixture
		//testdc.setupGUI(BoardController.getBoard().getFields(), null);
		Player[] players = new Player[]{new Player("Jack Sparrow", 1000), new Player("Captain Barbossa", 20000), new Player("Bootstrap Bill", 10000)};	
		PlayerController testPc = new PlayerController(players, DEFAULT_START_BALANCE, DEFAULT_NUMBER_OF_FIELDS);
		testController.playerController = testPc;
		Player jack = players[0];
		Player bill = players[2];
		testController.decorator.setupGUI(BoardController.getBoard().getFields(), testController.playerController.getPlayers());
		((game.fields.Ownable)BoardController.getBoard().getFields()[39]).setOwner(bill);
		((game.fields.Ownable)BoardController.getBoard().getFields()[37]).setOwner(bill);
		((game.fields.Street)BoardController.getBoard().getFields()[39]).addBuilding();
		testdc.updateHouses((game.fields.Ownable)BoardController.getBoard().getFields()[39]);
		testdc.updateHouses((game.fields.Ownable)BoardController.getBoard().getFields()[37]);
		testdc.updateFieldOwner((game.fields.Ownable)BoardController.getBoard().getFields()[39]);
		testdc.updateFieldOwner((game.fields.Ownable)BoardController.getBoard().getFields()[37]);
		testController.playerController.move(jack, 4, testdc, testPc, testCC, testBC);
	testController.playerController.move(bill, 6, testdc, testPc, testCC, testBC);
//		testController.playerController.move(bill, 33, testdc, testPc, testCC, testBC);
//		testController.playerController.move(bill, 7, testdc, testPc, testCC, testBC);
//		testController.playerController.move(bill, 0, testdc, testPc, testCC, testBC);
//		testController.playerController.move(bill, 0, testdc, testPc, testCC, testBC);
		
		testController.runGame();
	}
}
