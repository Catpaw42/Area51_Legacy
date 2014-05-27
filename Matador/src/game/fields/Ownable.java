package game.fields;

import game.Player;
import game.StringTools;

public abstract class Ownable extends Field
{

	private static final int PAWN_FACTOR = 2;
	protected int price;
	protected Player owner;
	protected boolean pawned;

	// Constructor
	protected Ownable(String title, int price)
	{
		super(title);
		this.price = price;
		this.owner = null;
		this.pawned = false;
	}
	
	//TODO move to Ownable
	public boolean isPawned() {
			
			return pawned ;
		}
	//TODO move to Ownable
		public void setPawned(boolean isPawned){
			this.pawned = isPawned;
		}
@	Override
		public String[] decoratorMessage(Player player){
			String[] first = super.decoratorMessage(player);
			String[] second = null;
			if (getOwner() == null){
				second = new String[] { "PriceMessage", Integer.toString(getPrice())};
			}
			else if (getOwner() != player)
			{
				second = new String[] { "YouHaveToPayMessage", Integer.toString(getRent()) };
			} else if (getOwner() == player){
				second = new String[] { "OwnFieldMessage"};
			}
			return StringTools.add(first, second);
		}


	public void setOwner(Player owner)
	{
		this.owner = owner;
	}

	public Player getOwner()
	{
		return owner;
	}

	public abstract int getBaseRent();
	
	public abstract int getRent();

	public abstract String toString();

	public int getPrice()
	{
		return price;
	}

	public int getPawnValue() {
		return price/PAWN_FACTOR;
	}
}
