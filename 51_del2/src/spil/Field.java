package spil;

public class Field
{
	// variables for amount (for deposit and withdraw), title, subtext,
	// description and extraTurn (true only for field[8]).
	// title, subtext and description are 3 options we have in the GUI.
	private int amount;
	private int extraTurn;
	private int resultOfTurn;
	
	private String title;
	private String subText;
	private String description;

	// constructor extraTurn = 0 or 1 for false or true relatively
	public Field(String title, String subText, String description, int amount,
			int extraTurn)
	{
		this.extraTurn = extraTurn;
		this.title = title;
		this.subText = subText;
		this.amount = amount;
		this.description = description;
	}

	// getter for field title
	public String getTitle()
	{
		return title;
	}

	// setter for field title
	public void setTitle(String title)
	{
		this.title = title;
	}

	// getter for field subtext
	public String getSubText()
	{
		return subText;
	}

	// setter for field subtext
	public void setSubText(String subText)
	{
		this.subText = subText;
	}

	// getter for Description
	public String getDescription()
	{
		return description;
	}

	// also for future use. fields effect on player (for now only withdraw and
	// deposit)
	// returns the result of landing on this field
	public int playerAction(Player activePlayer)
	{
		resultOfTurn = 0 + extraTurn;
		updatePlayerAccount(activePlayer);

		return resultOfTurn; // returns the result of this field: fail = -1,0 ; success = 1,2
	}

	// method used in PlayerAction. also made like this for future use
	private void updatePlayerAccount(Player activePlayer)
	{
		if (amount < 0)
		{
			try
			{
				activePlayer.getAccount().withdraw(Math.abs(amount));
				resultOfTurn = resultOfTurn + 1;
			} catch (Exception e)
			{
				resultOfTurn = resultOfTurn - 1;
			}
		} else
			try
			{
				activePlayer.getAccount().deposit(amount);
				resultOfTurn = resultOfTurn + 1;
			} catch (Exception e)
			{
				resultOfTurn = resultOfTurn - 1;
			}
	}

	// getter for field message
	public String getMessage()
	{
		return description;
	}

	// setter for field description
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getAmount()
	{
		return amount;
	}

}
