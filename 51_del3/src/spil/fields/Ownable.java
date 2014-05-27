package spil.fields;

import java.util.Arrays;

import spil.Decorator;
import spil.Player;

public abstract class Ownable extends Field
{

	protected int price;
	protected Player owner;

	// Constructor
	protected Ownable(String title, int price)
	{
		super(title);
		this.price = price;
		this.owner = null;
	}

	public void landOnField(Player p)
	{
		if (owner == null)
		{
			offerFieldToPlayer(p);
		} else if (owner != p)
		{
			Decorator.showMessage(decoratorMessage(p));
			payRent(p);
		} else
		{
			Decorator.showMessage(p.getPlayerName(), "ownFieldMessage");
		}
	}

	private void payRent(Player p)
	{
		try
		{
			p.getAccount().withdraw(getRent());
			owner.getAccount().deposit(getRent());
			Decorator.setBalance(owner.getPlayerName(), owner.getAccount()
					.getBalance()); //update on GUI, needed here as we modify something other than current player
		} catch (Exception e)
		{
			try
			{
				owner.getAccount().deposit(p.getAccount().getBalance());
				Decorator.setBalance(owner.getPlayerName(), owner.getAccount()
						.getBalance()); //update on GUI, needed here as we modify something other than current player
				p.goBroke();
			} catch (Exception e1)
			{
				e1.printStackTrace();
				System.exit(0);
			}

		}
	}

	private void offerFieldToPlayer(Player p)
	{
		if (Decorator
				.getUserLeftButtonPressed("Yes", "No", decoratorMessage(p)))
		{
			try
			{
				p.getAccount().withdraw(price);
				setOwner(p);
			} catch (Exception e)
			{
				Decorator.showMessage("InsufficientFunds");
			}
		}
	}

	protected String[] decoratorMessage(Player p)
	{
		String[] first = super.decoratorMessage();

		if (getOwner() == null)
		{
			String[] second = { "noOwnerFieldMessage" };
			String[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		}
		if (getOwner() == p)
		{
			String[] second = { "ownFieldMessage", getOwner().getPlayerName() };
			String[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		}
		// player is not owner, but another player is
		else
		{
			String[] second = { "ownedByAnotherPlayerMessage", getOwner().getPlayerName()};
			String[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		}
	}

	public void setOwner(Player p)
	{
		owner = p;
		if (p != null)
			p.setPlayerOwner(this);
		Decorator.setOwnerOfField(this);
	}

	public Player getOwner()
	{
		return owner;
	}

	protected abstract int getRent();

	public abstract String toString();

	public int getPrice()
	{
		return price;
	}
}
