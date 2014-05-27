package spil;

public class Account
{
	private int score;
	public final static int DEFAULT_START_SCORE = 1000;
	
	public Account() {
		this(DEFAULT_START_SCORE);
	}

	public Account(int score)
	{
		this.score = score;
	}

	public int getScore()
	{
		return score;
	}

	// checks that you're not attempting to set the score below zero
	// then sets the score to the given integer
	public void setScore(int score) throws Exception
	{
		// check for negative total
		if (score < 0)
			throw new Exception("Account balance may not be negative");
		this.score = score;
	}

	// adds an amount to the score, provided that its does not bring the score
	// below zero, or above intMax
	public int deposit(int amount) throws Exception
	{
		// refuse sub-zero numbers
		if (amount < 0)
			throw new Exception("May not deposit negative amount");
		// test for integer overrun
		if (score > Integer.MAX_VALUE - amount) // Corrected from score + amount > Integer.MAX_VAlUE - would fail if amount + score exceeded INTEGER.MAXVALUE - which defies the purpose
			throw new Exception(
					"Cannot deposit amount - score will exceed Integer.MAX_VALUE");
		score = score + amount;
		return score; // returns new score
	}

	// withdraws an amount from the score, refuses negative numbers, and refuses
	// to withdraw more than there is in score.
	public int withdraw(int amount) throws Exception
	{
		//refuse negative numbers
		if (amount < 0)
			throw new Exception("May not withdraw negative amount");
		//refuse to withdraw more than total
		if (amount > this.score)
			throw new Exception("May not withdraw more than balance"); 
		this.score = this.score - amount;
		return score; // returns new balance
	}

	@Override
	public String toString()
	{
		return "Account [score=" + score + "]";
	}

}
