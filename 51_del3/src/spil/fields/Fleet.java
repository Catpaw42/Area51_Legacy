package spil.fields;

import java.util.Arrays;

import spil.Player;

public class Fleet extends Ownable {
	private int baseRent;

	// Constructor
	public Fleet(String title, int price, int baseRent){
		super(title, price);
		this.baseRent = baseRent;
	}

	// Gets rent. The formula for the rent is (2^numberOfFleets) * baseRent
	@Override
	protected int getRent() {
		return (int)(Math.pow(2, owner.getNumberOfFleets())) * baseRent;
	}
	
	protected String[] decoratorMessage(Player p){
		String[] first = super.decoratorMessage(p);
		if (getOwner() == null){
			String[] second = { "PriceMessage", Integer.toString(getPrice()), "fleetRentMessage", Integer.toString((int)(Math.pow(2, p.getNumberOfFleets() + 1)) * baseRent) };
			String[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		}
		else
		{
			String[] second = { "YouHaveToPayMessage", Integer.toString(getRent()) };
			String[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		}
	}
	@Override
	public String toString() {
		return "Fleet [baseRent=" + baseRent + ", price=" + price + ", owner="
				+ owner + ", title=" + title + "]";
	}
}
