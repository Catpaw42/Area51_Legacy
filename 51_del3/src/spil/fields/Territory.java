package spil.fields;

import java.util.Arrays;

import spil.Player;

public class Territory extends Ownable {
	private int rent;
	
	public Territory(String title, int price, int rent) {
		super(title, price);
		this.rent=rent;
	}

	@Override
	protected int getRent() {
		return rent;
	}
	
	protected String[] decoratorMessage(Player p){
		String[] first = super.decoratorMessage(p);
		if (getOwner() == null){
			String[] second = { "PriceMessage", Integer.toString(getPrice()), "RentMessage", Integer.toString(getRent()) };
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
		return "Territory [rent=" + rent + ", price=" + price + ", owner="
				+ owner + ", title=" + title + "]";
	}	
}
