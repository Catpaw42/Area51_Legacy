package game;

import game.fields.Field;
import game.fields.Ownable;
import game.fields.Street;
import game.fields.Street.Group;

public class TradeController {

	private final String cancelButton = "Cancel";
	final private int NUMBER_OF_GROUPS = 8;
	final private int UNPAWN_RENT = 10; //TODO should probably be moved to Ownable

	// Constructor
	public TradeController(){

	}

	public void trade(Player player, Decorator decorator, PlayerController playerController) {

		Ownable[] ownedFields = new Ownable[BoardController.getFieldsbyPlayer(player).length];
		ownedFields = BoardController.getFieldsbyPlayer(player);
		String[] msg0 = new String[]{"TradeOrPawn"};
		String[] msg1 = new String[]{"Trade", "Pawn","UnPawn", "Cancel"};
		String[] msg2 = new String[]{"TradeOrCancel"};
		String[] msg3 = new String[]{"NoFieldsToTrade"};
		int choice;

		if(ownedFields.length == 0){
			decorator.showMessage(msg3);
		}
		else if(BoardController.hasAnyUnPawnedFields(player)){
			choice = decorator.getUserButtonPressed(msg0, msg1);
			if(choice == 0){
				sell(player, ownedFields, decorator, playerController);
			}
			// handles the case where the player wants to pawn a field
			if(choice == 1){
				pawn(player, ownedFields, decorator, playerController);
			}
			// if a player wants to unpawn a field
			if(choice == 2){
				unPawn(player, ownedFields, decorator, playerController);
			}
		}
		else{
			String[] msg4 = new String[]{"OnlyTradeOrUnPawn"};
			String[] msg5 = new String[]{"Trade", "UnPawn", "Cancel"};
			decorator.showMessage(msg4);
			choice = decorator.getUserButtonPressed(msg2, msg5);
			if(choice == 0){
				sell(player, ownedFields, decorator, playerController);

			}
			if(choice == 1){
				unPawn(player, ownedFields, decorator, playerController);
			}
		}
	}

	private void sell(Player seller, Ownable[] ownedFields, Decorator decorator, PlayerController playerController){

		Ownable[] sellableFields = sellAndPawnArray(ownedFields);
		String[] ownedFieldsTitle = new String[sellableFields.length+1];
		ownedFieldsTitle[0] = cancelButton;

		// makes list of owned fields titles for the dropdown list
		for(int i = 0; i < (sellableFields.length); i++){
			ownedFieldsTitle[i+1] = sellableFields[i].getTitle();
		}

		String[] msg0 = new String[]{"ChooseFieldToSell"};
		String[] msg1 = new String[]{"ChooseFieldPrice"};
		String[] msg2 = new String[]{"NotValidPrice"};
		String[] msg3 = new String[]{"ChoosePlayerToSellTo"};
		String[] msg4 = new String[]{"ContinueTrade"};
		String[] opt1 = new String[]{"Yes", "No"};
		Player[] allPlayers = playerController.getPlayers();
		Player[] buyingPlayers = new Player[playerController.getNumberOfPlayersLeft()];
		String[] buyingPlayersName = new String[playerController.getNumberOfPlayersLeft()];
		buyingPlayersName[0] = cancelButton;

		int chosenFieldNumber = decorator.getUserSelection(msg0, ownedFieldsTitle);

		if(chosenFieldNumber != 0){
			// players choose field to sell
			Field chosenField = sellableFields[chosenFieldNumber-1];

			String userInput;
			int sellingPrice = 0;
			while(sellingPrice == 0){
				userInput = decorator.getUserString(msg1);
				try{	
					sellingPrice = Integer.parseInt(userInput);
				} catch(NumberFormatException e){
					decorator.showMessage(msg2);
				}
			}

			// buyingPlayers[0] is the cancel button therefore is j = 1
			int j = 1;
			// makes list of all possible buyers
			for(int i = 0; i < allPlayers.length; i++){
				if(!allPlayers[i].isBroke() && !allPlayers[i].equals(seller)){
					buyingPlayers[j] = allPlayers[i];
					buyingPlayersName[j] = allPlayers[i].getPlayerName();
					j++;
				}	
			}
			int chosenPlayerNumber = decorator.getUserSelection(msg3, buyingPlayersName);
			if(chosenPlayerNumber != 0){
				Player chosenPlayer = buyingPlayers[chosenPlayerNumber];

				while(sellingPrice > chosenPlayer.getAccount().getBalance()){
					playerController.handleInsufficientFunds(chosenPlayer, sellingPrice, decorator);	
					if(sellingPrice > chosenPlayer.getAccount().getBalance()){
						if(decorator.getUserButtonPressed(msg4, opt1) == 1){
							break;
						}	
					}
				}
				if(sellingPrice < chosenPlayer.getAccount().getBalance()){
					try {
						chosenPlayer.getAccount().withdraw(sellingPrice);
						seller.getAccount().deposit(sellingPrice);
						if(chosenField instanceof Ownable){
							((Ownable) chosenField).setOwner(chosenPlayer);
						}
					} catch (Exception e) {
						System.err.println("Faaaaaame stort problem tradeController.sell");
						// while loop should make sure that this never happens
					}

					if(chosenField instanceof Ownable && ((Ownable)chosenField).getOwner().equals(chosenPlayer)){
						// update field owner in GUI
						decorator.updateFieldOwner((Ownable) chosenField);

					}
					decorator.updatePlayer(chosenPlayer);
					decorator.updatePlayer(seller);
				}
			}
		}
	}

	private void pawn(Player player, Ownable[] ownedFields, Decorator decorator, PlayerController playerController){
		if(ownedFields.length != 0){

			Ownable[] ownedPawneble = sellAndPawnArray(ownedFields);
			String[] ownedPawnebleTitle = new String[ownedPawneble.length + 1];
			ownedPawnebleTitle[0] = cancelButton;
			// makes String[] for the GUI dropdown list
			for(int i = 0; i < (ownedPawneble.length); i++){			
				ownedPawnebleTitle[i+1] = ownedPawneble[i].getTitle();
			}

			// makes a dropdown list with only those fields that can be pawned and sets pawningField to the chosen field
			String[] msg2 = new String[]{"ChooseFieldToPawn"};
			int chosenFieldNumber = decorator.getUserSelection(msg2, ownedPawnebleTitle);
			if(chosenFieldNumber != 0){
				chosenFieldNumber--;
				Ownable pawningField = ((Ownable)ownedPawneble[chosenFieldNumber]); 
				try {
					pawningField.getOwner().getAccount().deposit(pawningField.getPawnValue()); //gives the owner of that field, the fields pawn value
					pawningField.setPawned(true); // sets the field isPawned to true
				} catch (Exception e) {
					e.printStackTrace();
				}				
				decorator.updatePlayer(player);
				decorator.updatePawned(pawningField);
			}
		}
	}

	private void unPawn(Player player, Ownable[] ownedFields, Decorator decorator, PlayerController playerController){
		Ownable[] rawUnPawnableFields = new Street[ownedFields.length];

		for(int i = 0; i < ownedFields.length; i++){
			if(((Ownable)ownedFields[i]).isPawned()){
				rawUnPawnableFields[i] = ((Ownable)ownedFields[i]);
			}
		}
		Ownable[] unPawnableFields = removeNullOwnable(rawUnPawnableFields);
		String[] unPawnableFieldsTitle = new String[unPawnableFields.length+1];

		if(unPawnableFields.length > 0){
			unPawnableFieldsTitle[0] = cancelButton;
			for(int i = 0; i < unPawnableFields.length; i++){
				unPawnableFieldsTitle[i+1] = unPawnableFields[i].getTitle();
			}
			int choice = decorator.getUserSelection(new String[]{"ChooseFieldToUnPawn"}, unPawnableFieldsTitle);
			if(choice != 0){
				choice--;
				int unPawnCost = (unPawnableFields[choice].getPawnValue() + unPawnableFields[choice].getPawnValue()/UNPAWN_RENT);
				boolean paid = false;
				while(!paid){
					try {
						player.getAccount().withdraw(unPawnCost);
						unPawnableFields[choice].setPawned(false);
						decorator.updatePawned(unPawnableFields[choice]);
						paid = true;
					} catch (Exception e) {
						playerController.handleInsufficientFunds(player, unPawnCost, decorator);
						e.printStackTrace();
					}
				}
			}

		} else{
			decorator.showMessage(new String[]{"NoFieldsToUnPawn"});
		}
		decorator.updatePlayer(player);
	}

	public void buildings(Player activePlayer, PlayerController playerController, Decorator decorator){
		//***********************************************************************
		//		Field[] fields = BoardController.getBoard().getFields();
		//		((Ownable)fields[1]).setOwner(activePlayer); // for test must be deleted
		//		((Ownable)fields[3]).setOwner(activePlayer); // for test must be deleted
		//		((Ownable)fields[5]).setOwner(activePlayer);
		//		((Ownable)fields[6]).setOwner(activePlayer);
		//		((Ownable)fields[8]).setOwner(activePlayer);
		//		((Ownable)fields[9]).setOwner(activePlayer);
		//		((Ownable)fields[11]).setOwner(activePlayer);
		//		((Ownable)fields[13]).setOwner(activePlayer);
		//		((Ownable)fields[14]).setOwner(activePlayer);
		//		((Ownable)fields[15]).setOwner(activePlayer);
		//		((Ownable)fields[16]).setOwner(activePlayer);
		//		((Ownable)fields[18]).setOwner(activePlayer);
		//		((Ownable)fields[19]).setOwner(activePlayer);
		//		((Ownable)fields[21]).setOwner(activePlayer);
		//		((Ownable)fields[23]).setOwner(activePlayer);
		//		((Ownable)fields[25]).setOwner(activePlayer);
		//		((Ownable)fields[26]).setOwner(activePlayer);
		//		((Ownable)fields[28]).setOwner(activePlayer);
		//		((Ownable)fields[29]).setOwner(activePlayer);
		//		((Ownable)fields[31]).setOwner(activePlayer);
		//		((Ownable)fields[32]).setOwner(activePlayer);
		//		((Ownable)fields[34]).setOwner(activePlayer);
		//		((Ownable)fields[37]).setOwner(activePlayer);
		//		((Ownable)fields[39]).setOwner(activePlayer);
		//
		//		decorator.updateFieldOwner(((Ownable)fields[1]));
		//		decorator.updateFieldOwner(((Ownable)fields[3]));
		//		decorator.updateFieldOwner(((Ownable)fields[5]));
		//		decorator.updateFieldOwner(((Ownable)fields[6]));
		//		decorator.updateFieldOwner(((Ownable)fields[8]));
		//		decorator.updateFieldOwner(((Ownable)fields[9]));
		//		decorator.updateFieldOwner(((Ownable)fields[11]));
		//		decorator.updateFieldOwner(((Ownable)fields[13]));
		//		decorator.updateFieldOwner(((Ownable)fields[14]));
		//		decorator.updateFieldOwner(((Ownable)fields[15]));
		//		decorator.updateFieldOwner(((Ownable)fields[16]));
		//		decorator.updateFieldOwner(((Ownable)fields[18]));
		//		decorator.updateFieldOwner(((Ownable)fields[19]));
		//		decorator.updateFieldOwner(((Ownable)fields[21]));
		//		decorator.updateFieldOwner(((Ownable)fields[23]));
		//		decorator.updateFieldOwner(((Ownable)fields[25]));
		//		decorator.updateFieldOwner(((Ownable)fields[26]));
		//		decorator.updateFieldOwner(((Ownable)fields[28]));
		//		decorator.updateFieldOwner(((Ownable)fields[29]));
		//		decorator.updateFieldOwner(((Ownable)fields[31]));
		//		decorator.updateFieldOwner(((Ownable)fields[32]));
		//		decorator.updateFieldOwner(((Ownable)fields[34]));
		//		decorator.updateFieldOwner(((Ownable)fields[37]));
		//		decorator.updateFieldOwner(((Ownable)fields[39]));

		//***********************************************************************


		Field[] playersField = BoardController.getFieldsbyPlayer(activePlayer);
		String[] msg0 = new String[]{"NoFieldsToBuildHousesOn"};
		String[] buyBuildingFieldsTitle = null;
		String[] sellBuildingFieldsTitle = null;
		Street[] buildableFields = getBuildableFields(playersField);


		if(buildableFields.length == 0){
			decorator.showMessage(msg0);
		}

		else{
			Street[] buyBuildingFields = getBuyBuildingFields(buildableFields);
			Street[] sellBuildingFields = getSellBuildingFields(buildableFields);

			if(buyBuildingFields.length !=0){
				buyBuildingFieldsTitle = new String[buyBuildingFields.length+1];
				buyBuildingFieldsTitle[0] = cancelButton;

				for(int i = 0; i < buyBuildingFields.length; i++){
					buyBuildingFieldsTitle[i+1] = buyBuildingFields[i].getTitle();
				}
			}
			if(sellBuildingFields.length != 0){
				sellBuildingFieldsTitle = new String[sellBuildingFields.length+1];
				sellBuildingFieldsTitle[0] = cancelButton;

				for(int i = 0; i < sellBuildingFields.length; i++){
					sellBuildingFieldsTitle[i+1] = sellBuildingFields[i].getTitle();
				}
			}

			String[] msg2 = new String[]{"BuyOrSellBuilding"};
			String[] buttons0 = new String[]{"Buy", "Sell", "Cancel"};
			int sellORbuy = decorator.getUserButtonPressed(msg2, buttons0);

			if(sellORbuy != 2){
				boolean regretBuying = false;
				// if player wants to buy
				if(sellORbuy == 0){
					String[] msg3 = new String[]{"ChooseFieldToBuildOn"};
					if(buyBuildingFields.length == 0){
						String[] msg4 = new String[]{"NoFieldsToBuildOn"};
						decorator.showMessage(msg4);
						return;
					}
					else{
						int buyOnField = (decorator.getUserSelection(msg3, buyBuildingFieldsTitle));
						if(buyOnField != 0){
							buyOnField--;
							int buildingPrice = buyBuildingFields[buyOnField].getBuildingPrice();

							if(activePlayer.getAccount().getBalance() < buildingPrice){
								regretBuying = !playerController.handleInsufficientFunds(activePlayer, buildingPrice, decorator);
							}
							if(!regretBuying){
								try {
									activePlayer.getAccount().withdraw(buyBuildingFields[buyOnField].getBuildingPrice());
									buyBuildingFields[buyOnField].addBuilding();
									decorator.updateHouses(buyBuildingFields[buyOnField]);
									decorator.updatePlayer(activePlayer);
								} catch (Exception e) {
									// should not get this far because of the previous if statement
									e.printStackTrace();
								}
							}
						}
					}
				}
				if(sellORbuy == 1){
					String[] msg5 = new String[]{"ChooseFieldToSellBuilding"};
					if(sellBuildingFields.length == 0){
						String[] msg4 = new String[]{"NoFieldsToSellBuilding"};
						decorator.showMessage(msg4);
						return;
					}

					else{
						int sellOnField = (decorator.getUserSelection(msg5, sellBuildingFieldsTitle));
						if(sellOnField != 0){
							sellOnField--;

							if(!regretBuying){
								try {
									activePlayer.getAccount().deposit(sellBuildingFields[sellOnField].getBuildingSellValue());
									sellBuildingFields[sellOnField].removeBuilding();
									decorator.updateHouses(sellBuildingFields[sellOnField]);
									decorator.updatePlayer(activePlayer);
								} catch (Exception e) {
									// only if you have more money than int can handle
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}


	private Street[] getBuyBuildingFields(Street[] buildableFields){

		Street[] buyHouseFields = new Street[buildableFields.length];
		for(int i = 0; i < buildableFields.length; i++){
			boolean legitField = true;
			if(buildableFields[i].getBuildings() < Street.MAX_NUMBER_OF_BUILDINGS){
				for(int j = 0; j < buildableFields.length; j++){
					if(buildableFields[i].getGroup().equals(buildableFields[j].getGroup()) && !buildableFields[i].equals(buildableFields[j])){

						if(buildableFields[i].getBuildings() > buildableFields[j].getBuildings()){
							legitField = false;
							break;
						}
					}
				}
			}
			else {
				System.out.println("LegitField = false");
				legitField = false;
			}
			if(legitField){
				buyHouseFields[i] = buildableFields[i]; 
			}
		}
		return removeNullStreets(buyHouseFields);
	}

	private Street[] getSellBuildingFields(Street[] buildableFields){

		Street[] sellBuildingFields = new Street[buildableFields.length];
		for(int i = 0; i < buildableFields.length; i++){
			boolean legitField = true;
			if(buildableFields[i].getBuildings() > 0){
				for(int j = 0; j < buildableFields.length; j++){
					if(buildableFields[i].getGroup().equals(buildableFields[j].getGroup()) && !buildableFields[i].equals(buildableFields[j])){

						if(buildableFields[i].getBuildings() < buildableFields[j].getBuildings()){
							legitField = false;
							break;
						}
					}
				}
			}
			else {
				legitField = false;
			}
			if(legitField){
				sellBuildingFields[i] = buildableFields[i];
			}
		}

		return removeNullStreets(sellBuildingFields);
	}

	private Street[] removeNullStreets(Street[] list){
		int nullCounter = 0;
		for(int i = 0; i < list.length; i++){
			if(list[i] == null){
				nullCounter++;
			}
		}
		Street[] newArray = new Street[list.length - nullCounter];
		int j = 0;
		System.out.println("nullcounter: " + nullCounter);
		for(int i = 0; i < list.length; i++){
			if(list[i] != null){
				newArray[j] = list[i];
				System.out.println("newArray: " + newArray[j].getTitle());
				j++;
			}
		}
		return newArray;
	}

	private Ownable[] sellAndPawnArray(Ownable[] ownedFields){

		Ownable[] rawSellAndPawnArray = new Ownable[ownedFields.length];
		for(int i = 0; i < ownedFields.length; i++){
			boolean addStreet = true;
			if(ownedFields[i] instanceof Street){
				if(((Street)ownedFields[i]).getBuildings() == 0){
					for(int j = 0; j < ownedFields.length; j++){
						if(ownedFields[j] instanceof Street){
							if(((Street)ownedFields[i]).getGroup() == ((Street)ownedFields[j]).getGroup()){
								if(((Street)ownedFields[j]).getBuildings() > 0){
									addStreet = false;
									break;
								}
							}
						}
					}							
				}
				else{
					addStreet = false;
				}
			}
			if(addStreet){
				rawSellAndPawnArray[i] = ownedFields[i];
			}
		}
		return removeNullOwnable(rawSellAndPawnArray);
	}
	private Ownable[] removeNullOwnable(Ownable[] list){
		int nullCounter = 0;

		for(int i = 0; i < list.length; i++){

			if(list[i] == null){
				nullCounter++;
			}
		}
		Ownable[] newArray = new Ownable[list.length - nullCounter];
		int j = 0;
		System.out.println("nullcounter: " + nullCounter);
		for(int i = 0; i < list.length; i++){
			if(list[i] != null){
				newArray[j] = list[i];
				System.out.println("newArray: " + newArray[j].getTitle());
				j++;
			}
		}
		return newArray;
	}
	private Street[] getBuildableFields(Field[] playersField){

		Field[] allFields = BoardController.getBoard().getFields();
		Group[] availableGroups = new Group[NUMBER_OF_GROUPS];
		int numberOfAvailableFields = 0;
		int numberOfAvailableGroup = 0;
		for(int i = 0; i < playersField.length; i++){
			if(playersField[i] instanceof Street){
				boolean allFieldsInGroup = true;
				// checks if owner has all fields matching playersField[i] group
				int j = 0;
				for(; j < allFields.length; j++){
					System.out.println("checker tilgængelige felter at bygge huse på"); // for test
					// should always get in this statement. Only check for if the field is instance of the class Street
					if(allFields[j] instanceof Street){
						// if loop avoids this statement, then all fields in playersField[i].getGroup is owned by same player
						if(((Street)playersField[i]).getGroup().equals(((Street)allFields[j]).getGroup()) && 
								!((Street)playersField[i]).getOwner().equals(((Street)allFields[j]).getOwner())){
							allFieldsInGroup = false;
							break;
						}
					}
				}
				// if enters this statement it means that the player is the owner of all fields in one group		
				if(allFieldsInGroup){
					availableGroups[numberOfAvailableGroup] = ((Street)playersField[i]).getGroup();

					if(numberOfAvailableGroup > 0){
						if(availableGroups[numberOfAvailableGroup].equals(availableGroups[numberOfAvailableGroup-1])){
							numberOfAvailableGroup--;
						}
					}
					numberOfAvailableGroup++;
					numberOfAvailableFields++;
				}
			}
		}
		Street[] buildableFields = new Street[numberOfAvailableFields];

		// loop to do array of fields that can have houses built on
		int k = 0; // starts with 1 because of the cancel button
		for(int j = 0; j < availableGroups.length; j++){

			for(int i = 0; i < playersField.length; i++){
				if(playersField[i] instanceof Street){
					if(((Street)playersField[i]).getGroup().equals(availableGroups[j])){
						buildableFields[k] = ((Street)playersField[i]);	
						k++;
						// have to get rid of this statement should not be necessary
						if(numberOfAvailableFields == k){
							break;
						}
					}	
				}
			}
			if(numberOfAvailableFields == k){
				break;
			}
		}
		return buildableFields;
	}

	// TODO future feature to be done
	//	public void auktion(Ownable auktionField, Player activePlayer, PlayerController playerController){
	//
	//		Player[] bidders = new Player[playerController.getNumberOfPlayersLeft()];
	//		Player highestBidder;
	//		Player currentBidder;
	//		int j = 0;
	//		for(int i = 0; i < playerController.getPlayers().length; i++){
	//			if(!playerController.getPlayers()[i].getIsBroke()){
	//				bidders[j] = playerController.getPlayers()[i];
	//			}
	//			if(playerController.getPlayers()[i].equals(activePlayer)){
	//				if(i == 0){
	//
	//				}
	//				currentBidder = playerController.getPlayers()[i];
	//			}
	//		}
	//		for(Player p: b)
	//		currentBidder = activePlayer;
	//			do{
	//			
	//				
	//			}
	//		while(highestBidder.equals(currentBidder));	

















}
