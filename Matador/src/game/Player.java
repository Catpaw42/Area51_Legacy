/**
 * @author Gruppe 51_E13
 */
package game;


public class Player
{
	private String playerName;
	private Account account;
	private int currentField;
	private int twoOfAKindCount = 0;
	private int inJail;
	private boolean isBroke;
	private final int DEFAULT_START_FIELD = 1;
	private int numberOfJailCards = 0;
	
	//constructor
	public Player(String playerName, int balance)
	{
		this.playerName = playerName;
		this.currentField = DEFAULT_START_FIELD ;
		this.inJail = 0;
		account = new Account();
		try {
			account.setBalance(balance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//returns the players name
	public String getPlayerName()
	{
		return playerName;
	}
	
	//sets the players name
	public void setPlayerName(String player)
	{
		playerName = player;
	}
	
	public Account getAccount(){
		return account;
	}
	

	public int getCurrentFieldNumber(){
		return currentField;
	}
	public void setCurrentFieldNumber(int field){
		currentField = field;
	}
	
	public int getNumberOfJailCards() {
		return numberOfJailCards;
	}

	public void setNumberOfJailCards(int numberOfJailCards) {
		this.numberOfJailCards = numberOfJailCards;
	}
	
	//Deprecated
//	public void goBroke(){
//		// set balance = 0
//		try {
//			getAccount().setBalance(0);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// sets all owned fields owner to null
//		// and empties his array of owned fields
//		for (Ownable o : ownedFields) {
//			o.setOwner(null);
//		}
//		// deletes his array of ownedfields
//		ownedFields = null;
//
//		// makes the player inactive
//		isBroke = true;
//		//Tells the sad message
//		//Decorator.showMessage("" + this.getPlayerName(), "brokeMessage");
//		
//		
//	}
	@Override
	public String toString()
	{
		return playerName + " : " + account + "\t";
	}

	public int getTwoOfAKindCount() {
		return twoOfAKindCount;
	}

	public void setTwoOfAKindCount(int twoOfAKindCount) {
		this.twoOfAKindCount = twoOfAKindCount;
	}

	public void setInJail(int b) {
		this.inJail = b;
	}

	public int getInJail() {
		return inJail;
	}

	public void setIsBroke(boolean isBroke) {
		this.isBroke = isBroke;
	}
	
	public boolean isBroke() {
		// TODO Auto-generated method stub
		return isBroke;
	}

}
