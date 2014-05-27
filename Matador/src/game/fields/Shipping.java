package game.fields;

import game.BoardController;
import game.Player;
import game.StringTools;

public class Shipping extends Ownable {
	private int baseRent;

	// Constructor
	public Shipping(String title, int price, int baseRent){
		super(title, price);
		this.baseRent = baseRent;
	}

	// Gets rent. The formula for the rent is (2^numberOfFleets) * baseRent
	@Override
	public int getRent() {
		
		return (int)(Math.pow(2, BoardController.getNumberOfFleets(owner))) * baseRent;
	}
	
	public String[] decoratorMessage(Player p){
		String[] first = super.decoratorMessage(p);
		if (getOwner() == null){
			return StringTools.add(first, new String[]{"RentMessage",String.valueOf(getBaseRent()),"InRent"});
		}
			return first;
	}
	@Override
	public String toString() {
		return "Fleet [baseRent=" + baseRent + ", price=" + price + ", owner="
				+ owner + ", title=" + title + "]";
	}

	@Override
	public int getBaseRent() {
		return baseRent;
	}
}
