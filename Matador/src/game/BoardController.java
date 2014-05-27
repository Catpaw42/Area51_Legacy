package game;

import game.Account.IllegalAmountException;
import game.Account.InsufficientFundsException;
import game.cards.CardController;
import game.fields.*;

public class BoardController {

	private static Board board;
	//Constructor
	public BoardController(int numberOfFields) {
		board = new Board(numberOfFields);
		board.setupDefaultBoard();
	}
	//Overload - ordinary landOnField
	public void landOnField(Player player, int fieldNumber, Decorator decorator,
			PlayerController playerController, CardController cardController){
		landOnField(player, fieldNumber, decorator, playerController, cardController, 1);	
	}

	public void landOnField(Player player, int fieldNumber, Decorator decorator,
			PlayerController playerController, CardController cardController, int rentModifier){
		//finds the field the player landed on
		Field field = board.getFieldByNumber(fieldNumber);  
		//Checks fieldType -(Pseudo polymorphy)
		if (field instanceof Ownable){
			landOnOwnable(player, decorator, playerController, rentModifier, field);
		}
		if (field instanceof Chance){
			System.out.println("" + player + decorator + playerController + this);
			//Delegate responsibility to CardController
			cardController.drawCard(player, decorator, playerController, this);
		}
		if (field instanceof Refuge){
			decorator.showMessage(((Refuge)field).decoratorMessage(player));
		}
		if (field instanceof GotoJail){
			decorator.showMessage(((GotoJail)field).decoratorMessage(player));
			playerController.moveToJail(player, getJailNumber());
		}
		if (field instanceof Tax){
			landOnTax(player, decorator, playerController, field);
		}
		decorator.updatePlayer(player);
	}

	private void landOnOwnable(Player player, Decorator decorator,
			PlayerController playerController, int rentModifier, Field field) {
		Player owner = ((Ownable) field).getOwner();
		if (owner == null){
			//Unowned Field
			offerOwnableToPlayer((Ownable) field, player, decorator, playerController);
		} else if (owner != player){
			System.out.println("Started code for hostile field");
			//Owned by other pLayer
			String[] msg = new String[] {"OwnedByOtherPlayer",owner.getPlayerName(),"YouMustPayRent",String.valueOf(((Ownable) field).getRent())};
			int rent = ((Ownable)field).getRent()*rentModifier;
			//Raise money
			boolean rentPayed = playerController.payDebt(player, owner, decorator, msg, rent);
			if(!rentPayed){
				String[] msg2 = new String[]{"YouAreBankrupt"};
				decorator.showMessage(msg2);
				playerController.hostileTakeOver(owner, player);
			}
			//update players
			decorator.updatePlayer(player);
			decorator.updatePlayer(owner);
		} else if (owner == player){
			String[] msg = new String[] {"OwnField"};
			decorator.showMessage(msg);
		}
	}


	private void landOnTax(Player player, Decorator decorator,
			PlayerController playerController, Field field) {
		int taxAmount = ((Tax) field).getTaxAmount();
		int taxRate = ((Tax)field).getTaxrate();
		boolean debtPayed = false;
		//Determine TaxAmount
		if (taxRate<0){
			//Field is fixed tax amount
			decorator.showMessage(((Tax)field).decoratorMessage(player));
		} else {
			//Select taxAmount or Tax rate
			String[] options = new String[]{String.valueOf(taxAmount), String.valueOf(taxRate)+"%"};
			int userSelection = decorator.getUserSelection((((Tax)field).decoratorMessage(player)), options);
			if (userSelection == 1){
				taxAmount = (int)( ((taxRate/100.f)* playerController.getTotalAssets(this, player)));
				System.out.println("Valgt 10%" + taxAmount);
			}
		}
		String[] msg0 = new String[] {"YouMustPayTax",String.valueOf(taxAmount)};
		debtPayed = playerController.payDebt(player, null, decorator, msg0, taxAmount);
		//Pay debt
		if (!debtPayed) {
			String[] msg2 = new String[]{"YouAreBankrupt"};
			decorator.showMessage(msg2);
		}
		decorator.updatePlayer(player);
	}

	private void offerOwnableToPlayer(Ownable ownable, Player player,
			Decorator decorator, PlayerController playerController) {
		String[] Fieldmsg = ownable.decoratorMessage(player);
		String[] msg = new String[]{"OfferFieldToPlayer"};
		msg = StringTools.add(Fieldmsg,msg);

		boolean pricePayed = false;
		while (!pricePayed){
			String[] opt0 = new String[]{"Yes", "No"};
			int userChoice = decorator.getUserButtonPressed(msg, opt0);
			if(userChoice == 0){
				try {
					player.getAccount().withdraw(ownable.getPrice());
					ownable.setOwner(player);
					decorator.updateFieldOwner(ownable);
					if (ownable instanceof Shipping){
						for (Field field : BoardController.getBoard().getFields()){
							// to make sure all fleets are updated
							decorator.updateFieldRent(field);
						}
					}
					decorator.updatePlayer(player);	
					pricePayed = true;
				} catch (InsufficientFundsException e) {

					if(getFieldsbyPlayer(player).length == 0){
						String[] msg0 = new String[]{"NoFieldsToTrade"};
						decorator.showMessage(msg0);
						break;
					}
					else if(!playerController.handleInsufficientFunds(player, ownable.getPrice(), decorator)){
						break;
					}
				} catch (IllegalAmountException e) {
					System.err.println("Fail in offerOwnableToPlayer");
					e.printStackTrace();
				}
			}
			else break;
		}
	}




	// Static helper Method to get array of a players owned fields
	public static Ownable[] getFieldsbyPlayer(Player player) {

		int numberOfOwnedFields = 0;

		// counts number of players owned fields
		for (int i=0; i<board.getFields().length; i++){
			Field field = board.getFields()[i];
			if(field instanceof Ownable){
				if(((Ownable)field).getOwner() == player){
					numberOfOwnedFields++;
				}
			}
		}
		int j =0;
		Ownable[] ownedFieldsByPlayer = new Ownable[numberOfOwnedFields];
		for (int i =0;i < board.getFields().length ;i++){
			Field field = board.getFields()[i];
			if (field instanceof Ownable){
				if (((Ownable) field).getOwner()== player){
					ownedFieldsByPlayer[j] = ((Ownable)field);
					j++;
				}
			}
		}
		return ownedFieldsByPlayer;
	}


	public static int getFieldNumber(Field field){
		int fieldNumber = 0;
		for(int i = 0 ; i < board.getFields().length; i++){
			if(field.equals(board.getFields()[i])){
				fieldNumber = i+1;
			}
		}
		return fieldNumber;
	}

	public static boolean hasAnyUnPawnedFields(Player player){

		boolean unPawnedFields = false;
		Field[] ownedFields = getFieldsbyPlayer(player);
		for(Field ownable: ownedFields){

			if(ownable instanceof Ownable && !((Ownable)ownable).isPawned()){
				unPawnedFields = true;
				break;	// breaks for loop if only one field is not pawned
			}
			else unPawnedFields = false;

		}
		return unPawnedFields;
	}


	public static Board getBoard() {
		return board;
	}

	//
	//	private static void setBoard(Board board) {
	//		BoardController.board = board;
	//	}
	//

	public static int getNumberOfFleets(Player player) {
		int numberOfFleets = 0;
		Field[] fields = getFieldsbyPlayer(player);
		for (Field field : fields)
			if (field instanceof Shipping){
				numberOfFleets++;
			}

		return numberOfFleets;
	}
	public int getJailNumber() {

		return getFieldNumber(getJail());
	}
	public Jail getJail() {
		Jail jail = null;
		for (Field field : board.getFields()){
			if (field instanceof Jail) jail = (Jail) field;
		}		return jail;
	}

}
