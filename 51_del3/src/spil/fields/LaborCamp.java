package spil.fields;
import java.util.Arrays;

import spil.*;

public class LaborCamp extends Ownable {

	private int baseRent;
	private DiceCup diceCup;
	
	public LaborCamp(String title, int price, int baseRent, DiceCup diceCup){
		super(title, price);
		this.baseRent = baseRent;
		this.diceCup = diceCup;
	}
	
	@Override
	protected int getRent(){
		return  baseRent * diceCup.getSum();
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
	public String toString(){
		return title;
	}
	
	
	
	
}
