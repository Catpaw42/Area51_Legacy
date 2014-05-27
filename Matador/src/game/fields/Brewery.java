package game.fields;
import game.DiceCup;
import game.Player;
import game.StringTools;

public class Brewery extends Ownable {

	private int baseRent;
	
	public Brewery(String title, int price, int baseRent){
		super(title, price);
		this.baseRent = baseRent;
	}
	
	@Override
	public int getRent(){
		return  baseRent * DiceCup.getInstance().getSum();
	}
	//PolyMorph method
	public String[] decoratorMessage(Player player){
		if (getOwner() == null){
			return StringTools.add(super.decoratorMessage(player), new String[] {"RentMessage", Integer.toString(baseRent),"TimesEyes"});
		}
		return super.decoratorMessage(player);
	}
	@Override
	public String toString(){
		return title;
	}

	@Override
	public int getBaseRent() {
		return baseRent;
	}
	
	
	
	
}
