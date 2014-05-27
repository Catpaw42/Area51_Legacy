package game.cards;

import game.BoardController;
import game.Decorator;
import game.Player;
import game.PlayerController;
import game.fields.Field;
import game.fields.Street;
import game.cards.Shuffler;
import game.cards.MoveFieldsCard;
import game.cards.Card;

public class CardController {

	private Card[] deck; // array of card objects
	private static final int NUMBER_OF_CARDS = 44; //constant number of cards
	private int NUMBER_OF_GET_OUT_OF_JAIL_CARDS = 2;
	private Card[] getOutOfJailCards;
	private int payAmount;

	// constructor fills deck of cards
	public CardController() {
		deck = new Card[NUMBER_OF_CARDS];
		deck[0] = new GetOutOfJailCard("theKingsBirthday");
		deck[1] = new GetOutOfJailCard("theKingsBirthday2");
		deck[2] = new PayCard("payParkingTicket", 200);
		deck[3] = new PayCard("payBeer", 200);
		deck[4] = new PayCard("payCar", 3000);
		deck[5] = new PayCard("payCar2", 3000);
		deck[6] = new PayCard("payDentist", 2000);
		deck[7] = new PayCard("payCarInsurance", 1000);
		deck[8] = new PayCard("payToll", 200);
		deck[9] = new PayCard("payCarWash", 300);
		deck[10] = new PayCard("payNewTires", 1000);
		deck[11] = new PayCard("paySpeedingTicket", 1000);
		deck[12] = new PayForBuildingsCard("payPropertyTax", 800, 2300);
		deck[13] = new PayForBuildingsCard("payOilPrices", 500, 2000);
		deck[14] = new MoveToCard("moveToRådhuspladsen", 40); //TODO Når vi har tid, skal alle moveTo rettes til at flytte til det bestemte felt, hellere end feltnummer.
		deck[15] = new MoveToNearestCard("moveToNearestFerry", 1);
		deck[16] = new MoveToCard("moveToVimmelSkaftet", 33);
		deck[17] = new MoveToNearestCard("moveToNearestShipping", 2);
		deck[18] = new MoveToCard("moveToMolsLinien", 16);
		deck[19] = new GoToJailCard("moveToJail1");
		deck[20] = new MoveToCard("moveToFrederiksberg", 12);
		deck[21] = new GoToJailCard("moveToJail2");
		deck[22] = new MoveToCard("moveToStrandvejen", 20);
		deck[23] = new MoveToCard("moveToGrønningen", 25);
		deck[24] = new MoveFieldsCard("moveForward", 3);
		deck[25] = new MoveToCard("moveToStart", 1);
		deck[26] = new MoveToCard("moveToStart2", 1);
		deck[27] = new MoveFieldsCard("moveBackwards", -3);
		deck[28] = new MoveFieldsCard("moveBackwards2", -3);
		deck[29] = new ReceiveCard("receiveLottery", 500);
		deck[30] = new ReceiveCard("receiveLottery2", 500);
		deck[31] = new ReceiveFromPlayersCard("receiveBirthday", 200);
		deck[32] = new ReceiveCard("receiveDivivendsStocks", 1000);
		deck[33] = new ReceiveCard("receiveDividends", 1000);
		deck[34] = new ReceiveCard("receiveDividends2", 1000);
		deck[35] = new ReceiveCard("receivePaymentTaxes", 3000);
		deck[36] = new ReceiveFromPlayersCard("receiveParty", 500);
		deck[37] = new ReceiveCard("receiveGageRaise", 1000);
		deck[38] = new ReceiveCard("receiveFromBets", 1000);
		deck[39] = new ReceiveCard("receiveFromFurniture", 1000);
		deck[40] = new ReceiveCard("receiveFromBond", 1000);
		deck[41] = new ReceiveCard("receiveFromBond2", 1000);
		deck[42] = new ReceiveFromPlayersCard("receiveFamParty", 500);
		deck[43] = new ReceiveCard("receiveNyttehaven", 200);
		getOutOfJailCards = new Card[]{null, null};
		//
		Shuffler.Shuffle(deck);
	}
	public void drawCard(Player player, Decorator decorator, PlayerController playerController, BoardController boardController){
		Card card = getNextCard();
		decorator.showChanceCard(new String[] {card.getCardDescription()});
		decorator.showMessage(new String[] {"LandedOnChance"});

		if (card instanceof ReceiveCard){
			try {
				player.getAccount().deposit(((ReceiveCard) card).getAmount());
			} catch (Exception e) {
				System.err.println("deposit error - drawCard");
				e.printStackTrace();
			}	
		}
		if (card instanceof ReceiveFromPlayersCard){

			for(Player payingPlayer : playerController.getPlayers()){
				int cardAmount = ((ReceiveFromPlayersCard) card).getAmount();
				//all players except the one that shall receive money
				if (!player.equals(payingPlayer)) {
					boolean amountPaid = false;
					while(!amountPaid){
						try {
							payingPlayer.getAccount().withdraw(cardAmount);
							player.getAccount().deposit(cardAmount);
							decorator.updatePlayer(payingPlayer);
							decorator.updatePlayer(player);
							amountPaid = true;
						} catch (Exception e) {
							// as long the player has unpawned fields, he is forced to pawn fields until he can afford to pay
							if(BoardController.hasAnyUnPawnedFields(payingPlayer)){
								String[] msg0 = new String[]{"YouHaveToPawn"};
								decorator.showMessage(msg0);
								playerController.handleInsufficientFunds(payingPlayer, cardAmount, decorator);
							}
							// when his only choice is to sell off his fields, he is being offered to go bankrupt
							else{
								String[] msg1 = new String[]{"TradeOrBankrupt"};
								String[] opt1 = new String[]{"Trade", "Bankrupt"};
								int choice = decorator.getUserButtonPressed(msg1, opt1);
								if(choice == 1){
									playerController.hostileTakeOver(player, payingPlayer);
									break;
								}
								else{
									playerController.handleInsufficientFunds(payingPlayer, cardAmount, decorator);
								}
							}
							e.printStackTrace();
						}
					}
				}
			}
		}
		if (card instanceof GetOutOfJailCard){
			player.setNumberOfJailCards(player.getNumberOfJailCards() + 1);
		}
		if (card instanceof GoToJailCard){
			player.setInJail(1);
			player.setCurrentFieldNumber(11);
			decorator.updatePlayer(player);
		}
		if(card instanceof PayForBuildingsCard){
			try {
				payAmount = player.getAccount().withdraw(getBuildingExpenses(player, ((PayForBuildingsCard) card).getHousePrice(), ((PayForBuildingsCard) card).getHotelPrice(), boardController));
			} catch (Exception e) {
				playerController.handleInsufficientFunds(player, payAmount, decorator);
				e.printStackTrace();
			}
		}
		if(card instanceof PayCard){
			try {
				player.getAccount().withdraw(((PayCard) card).getAmount());
			} catch (Exception e) {
				playerController.handleInsufficientFunds(player, ((PayCard) card).getAmount(), decorator);
				e.printStackTrace();
			}	
		}
		if(card instanceof MoveFieldsCard){
			playerController.move(player, ((MoveFieldsCard) card).getNumberOfFields(), decorator, playerController, this, boardController);;
		}
		// TODO is broken
		if(card instanceof MoveToNearestCard){
			Field[] allFields = BoardController.getBoard().getFields();
			int playerField = player.getCurrentFieldNumber();
			int moveTo = 0;
			for(int i = 0; i < allFields.length; i++){
				if(allFields[i] instanceof game.fields.Shipping && playerField < i){
					moveTo = i+1;
					break;
				
				}
			}
			if(moveTo == 0){
				for(int i = 0; i < allFields.length; i++){
					if(allFields[i] instanceof game.fields.Shipping){
						moveTo = i+1;
						break;
					}
				}
			}
			playerController.moveTo(player, moveTo, boardController, decorator, this, ((MoveToNearestCard) card).getRentModifier());
		}
		if(card instanceof MoveToCard){
			playerController.moveTo(player, ((MoveToCard) card).getFieldNumber(), boardController, decorator, this);
		}
		decorator.updatePlayer(player);
	}



	//Flytter alle kort et til venstre i deck
	public Card getNextCard() {
		Card currentCard = deck[0];
		if (currentCard instanceof GetOutOfJailCard)
		{
			moveCards(deck, NUMBER_OF_CARDS);
			if (getOutOfJailCards[1] != null)
			{
				moveCards(getOutOfJailCards, NUMBER_OF_GET_OUT_OF_JAIL_CARDS);
				getOutOfJailCards[1] = currentCard;
			} else {
				getOutOfJailCards[0] = currentCard;
			}
		} 
		else {
			moveCards(deck, NUMBER_OF_CARDS);
			deck[NUMBER_OF_CARDS-1] = currentCard;
		}
		return currentCard;
	}

	public void useJailCard(Player player){
		// TODO smide fængselskortet ind i deck igen
		player.setInJail(0);
		if (getOutOfJailCards[0] != null){
			Card currentCard = getOutOfJailCards[0];
			getOutOfJailCards[0] = null;
			deck[deck.length - 1] = currentCard;
		} else if(getOutOfJailCards[1] != null){
			Card currentCard = getOutOfJailCards[1];
			getOutOfJailCards[1] = null;
			deck[deck.length] = currentCard;

		} else{
			System.err.println("Player has no jailcard!");
		}
		player.setNumberOfJailCards(player.getNumberOfJailCards() - 1);
	}

	private void moveCards(Card[] card, int numberOfCards){
		for(int i = 1; i < numberOfCards; i++){
			card[i-1] = card[i];
		}
	}

	private int getBuildingExpenses(Player player, int houseExpense, int hotelExpense, BoardController boardController){
		int buildingExpenses = 0;
		Field[] playersFields = BoardController.getFieldsbyPlayer(player);
		for(Field field: playersFields){
			if(field instanceof Street){
				int fieldBuildings = ((Street)field).getBuildings();
				if (fieldBuildings == 5){
					buildingExpenses += hotelExpense;
				}else {
					buildingExpenses += fieldBuildings*houseExpense;
				}
			}

		}

		return buildingExpenses;
	}
	public static void main(String [] args){
		CardController testCardController = new CardController();
		BoardController testBoardController = new BoardController(40);
		Player[] players = new Player[3];
		PlayerController testPlayerController = new PlayerController(players, 30000, 40);
		Decorator testDecorator = new Decorator("danish");
		players[0] = new Player("BootStrap", 30000);
		players[1] = new Player("Sparrow", 30000);
		players[2] = new Player("Barbossa", 30000);
		
		testDecorator.setupGUI(BoardController.getBoard().getFields(), players);
		for(int i = 0; i < 40; i++){
		testDecorator.updatePlayer(players[0]);
		testDecorator.updatePlayer(players[1]);
		testDecorator.updatePlayer(players[2]);
		testCardController.drawCard(players[0] , testDecorator, testPlayerController, testBoardController);
		}

	}
}
