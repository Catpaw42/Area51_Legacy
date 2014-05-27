/**
 * @author Gruppe 51_E13
 */
package spil;

import java.util.ArrayList;

import spil.fields.*;

public class Player
{
	private String playerName;
	private ArrayList<Ownable> ownedFields = new ArrayList<Ownable>();
	private boolean active = true;
	private Account account;
	private int currentField;
	
	
	
	//constructor
	public Player(String playerName, int balance)
	{
		this.playerName = playerName;
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
	
	public boolean getActive(){
		return active;
	}

	public int getCurrentFieldNumber(){
		return currentField;
	}
	public void setCurrentFieldNumber(int field){
		currentField = field;
	}
	
	public void setPlayerOwner(Ownable field ){
		ownedFields.add(field);
	}
	public ArrayList<Ownable> getOwnedFields(){
		return ownedFields;
	}
	
	public int getTotalAssets(){
		int totalAssets = 0;
		for (Ownable o : ownedFields) {
			totalAssets = totalAssets + o.getPrice();
		}
		totalAssets = totalAssets + getAccount().getBalance();
		return totalAssets;
	}
	public int getNumberOfFleets(){
		int numberOfFleets = 0;
		for (Ownable o : ownedFields) {
			if(o.getClass().equals(Fleet.class)){
				numberOfFleets++;
			}
		}
		return numberOfFleets;
	}
	
	public void goBroke(){
		// set balance = 0
		try {
			getAccount().setBalance(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// sets all owned fields owner to null
		// and empties his array of owned fields
		for (Ownable o : ownedFields) {
			o.setOwner(null);
		}
		// deletes his array of ownedfields
		ownedFields = null;

		// makes the player inactive
		active = false;
		//Tells the sad message
		Decorator.showMessage("" + this.getPlayerName(), "brokeMessage");
		
		
	}
	@Override
	public String toString()
	{
		return playerName + " : " + account + "\t";
	}

}
