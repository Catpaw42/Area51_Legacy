package game;
import java.awt.Color;

import game.fields.Ownable;
import boundaryToMatador.GUI;

public class Decorator {
	private static final int START_FIELD = 1;
	private Translator translator;

	//Constructor
	public Decorator(String language) {
		super();
		this.translator = new Translator(language);
	}

	//Setup GUI
	public void setupGUI(game.fields.Field[] gameFields, game.Player[] gamePlayers ) {
		GUI.create("Fields.txt");
		//add players to GUI
		if (gamePlayers!=null) {
			for (int i = 0; i < gamePlayers.length; i++) {
				if (gamePlayers[i].getPlayerName().equals("Casper")){
					GUI.addPlayer(gamePlayers[i].getPlayerName(),
							gamePlayers[i].getAccount().getBalance(), new Color(0, 0, 255, 64));
				}else {
					GUI.addPlayer(gamePlayers[i].getPlayerName(), gamePlayers[i]
							.getAccount().getBalance(),getColor(i));
					GUI.setCar(START_FIELD, gamePlayers[i].getPlayerName());
				}
			}
		}
		//Update Fields to new prices and texts
		for (int j = 0; j<gameFields.length;j++){
			if (gameFields[j] instanceof game.fields.Ownable){
				String[] priceText = new String[] {"Price", ": "};
				GUI.setSubText(j+1, translator.translateConcat(priceText) + ((game.fields.Ownable) gameFields[j]).getPrice());
			}
			if (gameFields[j] instanceof game.fields.Refuge){
				GUI.setSubText(j+1, translator.translateConcat(((game.fields.Refuge)gameFields[j]).getSubText()));
				GUI.setDescriptionText(j+1, translator.translateConcat(((game.fields.Refuge)gameFields[j]).getDescription()));
			}
		}
	}
	private Color getColor(int i) {
		switch (i) {
		case 0:
			return Color.BLUE;
		case 1:
			return Color.RED;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.YELLOW;
		case 4:
			return Color.BLACK;
		case 5:
			return Color.WHITE;
		default:
			break;
		}
		return Color.PINK;
	}

	//ordinary methods
	//Show Message
	public void showMessage(String[] msg) {
		GUI.showMessage(translator.translateConcat(msg));
	}
	//Get user input string
	public String getUserString(String[] messageString) {
		return GUI.getUserString(translator.translateConcat(messageString));	
	}
	//get userSelectionnumber from dropdown box
	public int getUserSelection(String[] msg, String[] options) {
		String[] translatedOptions = translator.translate(options);
		String translatedMsg = translator.translateConcat(msg);
		String selectedOption = GUI.getUserSelection(translatedMsg, translatedOptions);
		int selectionNumber = 0;
		for (int i = 0;i<options.length;i++){
			if (selectedOption == translatedOptions[i]) 
				selectionNumber = i;
		}
		return selectionNumber;
	}
	// Get number of button Pressed
	public int getUserButtonPressed(String[] messageString, String[] buttons) {
		String[] translatedButtons = translator.translate(buttons);
		String translatedMsg = translator.translateConcat(messageString);
		String selectedButton = GUI.getUserButtonPressed(translatedMsg, translatedButtons);
		int buttonNumber = 0;
		for (int i = 0;i<buttons.length;i++){
			if (selectedButton == translatedButtons[i]) 
				buttonNumber = i;
		}
		return buttonNumber;
		//Show dice - from array of ints
	}
	public void updateDice(DiceCup diceCup) {
		GUI.setDice(diceCup.getDiceFaceValues()[0], diceCup.getDiceFaceValues()[1]);
	}

	public void updatePlayer(game.Player player){
		updatePlayerField(player);
		updatePlayerBalance(player);
		if (player.isBroke()==true) GUI.removeAllCars(player.getPlayerName());
	}
	//Move player to Field
	private void updatePlayerField(game.Player player) {
		GUI.removeAllCars(player.getPlayerName());//Get rid of unwanted cars
		GUI.setCar(player.getCurrentFieldNumber(), player.getPlayerName());//setCar for player
	}
	//Update Players Balance
	private void updatePlayerBalance(game.Player player) {
		GUI.setBalance(player.getPlayerName(), player.getAccount().getBalance());		
	}

	//Update Fieldrent
	public void updateFieldRent(game.fields.Field field) {
		if (field instanceof game.fields.Ownable){
			String[] rentText = new String[] {"Rent", ": ", String.valueOf(((game.fields.Ownable) field).getRent())};
			GUI.setLeje(BoardController.getFieldNumber(field), translator.translateConcat(rentText) );
		}
	}

	//Remove field Rent Text
	public void removeRent(game.fields.Field field){
		GUI.setLeje(BoardController.getFieldNumber(field), "");
	}
	//Update Houses
	public void updateHouses(game.fields.Field field){
		if (field instanceof game.fields.Street){
			if (((game.fields.Street)field).getBuildings()<5 && ((game.fields.Street)field).getBuildings()>=0){
				GUI.setHotel(BoardController.getFieldNumber(field), false);	
				GUI.setHouses(BoardController.getFieldNumber(field), ((game.fields.Street) field).getBuildings());
			} else if (((game.fields.Street)field).getBuildings()==5){
				GUI.setHouses(BoardController.getFieldNumber(field), 0);
				GUI.setHotel(BoardController.getFieldNumber(field), true);
			} else {
				System.err.println("Illegal HouseCount - updateHouses - decorator");
			}
			updateFieldRent(field);
		}
	}
	//Set Field owner
	public void updateFieldOwner(game.fields.Ownable field) {
		System.out.println(BoardController.getFieldNumber(field));
		if(field.getOwner()==null) {
			GUI.removeOwner(BoardController.getFieldNumber(field));;
		} else {
			System.out.println(field.getOwner().getPlayerName());
			GUI.setOwner(BoardController.getFieldNumber(field), field.getOwner().getPlayerName());
		}
		updateFieldRent(field);
	}

	public void updateFieldPrice(game.fields.Field field) {
		String[] text = new String[] {"Price", ": ", String.valueOf(((game.fields.Ownable)field).getPrice())};
		GUI.setSubText(BoardController.getFieldNumber(field), translator.translateConcat(text));
	}
	public void updatePawned (game.fields.Ownable ownable){
		String title = ownable.isPawned() ? ownable.getTitle() + " " + "Pawned" : ownable.getTitle();
		System.out.println(title);
		GUI.setTitleText(BoardController.getFieldNumber(ownable), title);
	}

	public void showChanceCard(String[] chanceText){
		String msg = translator.translateConcat(chanceText);
		GUI.displayChanceCard(msg);
	}

	//testDriver
	public static void main(String[] args){
		Decorator testDecorator = new Decorator("danish");
		@SuppressWarnings("unused")
		BoardController testbc = new BoardController(40);
		game.fields.Field[] testFields = BoardController.getBoard().getFields();
		game.fields.Field testField = testFields[1];
		Player[] testPlayers = new Player[] {new Player("Casper", 400000), new Player("Frans",300000)};
		testDecorator.setupGUI(testFields, testPlayers);
		testPlayers[0].setCurrentFieldNumber(5);
		testDecorator.updatePlayerField(testPlayers[0]);//Works
		try {testPlayers[0].getAccount().deposit(10000000);
		} catch (Exception e) {	}
		testDecorator.updatePlayerBalance(testPlayers[0]);;//Works
		System.out.println(testFields[1]+""+testPlayers[0]);

		((Ownable) testField).setOwner(testPlayers[0]);
		testDecorator.updateFieldOwner((Ownable) testFields[1]);
		//String[] testStringArray = {"BuyOut", "RollDice"};
		((Ownable)testField).setOwner(null);
		testDecorator.updateFieldOwner((Ownable) testField);
		System.out.println(((game.fields.Ownable)testFields[1]).getRent());
		((game.fields.Street)testField).addBuilding();
		testDecorator.updateHouses(testField);
		//((game.fields.Street)testField).setBuildings(5);
		testDecorator.updateHouses(testField);
		//((game.fields.Street)testField).setBuildings(0);
		testDecorator.updateHouses(testField);
		//((game.fields.Street)testField).setBuildings(6);
		testDecorator.updateHouses(testField);
		//((game.fields.Street)testField).setBuildings(-1);
		testDecorator.updateHouses(testField);
		((game.fields.Ownable)testField).setPawned(true);
		testDecorator.updatePawned((Ownable) testField);
		//		System.out.println(testDecorator.getUserButtonPressed("testButtons", testStringArray));//Works Like a Charm
		//		//System.out.println(testDecorator.getUserSelection("testText", testStringArray));//Works
	}



}
