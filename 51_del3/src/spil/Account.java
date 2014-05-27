package spil;

public class Account
{
	private int balance;
	private final static int DEFAULT_START_BALANCE = 30000;
	
	public Account() {
		this(DEFAULT_START_BALANCE);
	}

	public Account(int score)
	{
		this.balance = score;
	}

	public int getBalance()
	{
		return balance;
	}

	// checks that you're not attempting to set the score below zero
	// then sets the score to the given integer
	public void setBalance(int balance) throws Exception
	{
		// check for negative total
		if (balance < 0)
			throw new Exception("Account balance may not be negative");
		this.balance = balance;
	}

	// adds an amount to the score, provided that its does not bring the score
	// below zero, or above intMax
	public int deposit(int amount) throws Exception
	{
		// refuse sub-zero numbers
		if (amount < 0)
			throw new Exception("May not deposit negative amount");
		// test for integer overrun
		if (balance > Integer.MAX_VALUE - amount) // Corrected from score + amount > Integer.MAX_VAlUE - would fail if amount + score exceeded INTEGER.MAXVALUE - which defies the purpose
			throw new Exception(
					"Cannot deposit amount - balance will exceed Integer.MAX_VALUE");
		balance = balance + amount;
		return balance; // returns new score
	}

	// withdraws an amount from the score, refuses negative numbers, and refuses
	// to withdraw more than there is in score.
	public int withdraw(int amount) throws Exception
	{
		//refuse negative numbers
		if (amount < 0)
			throw new Exception("May not withdraw negative amount");
		//refuse to withdraw more than total
		if (amount > this.balance)
			throw new Exception("May not withdraw more than balance"); 
		this.balance = this.balance - amount;
		return balance; // returns new balance
	}

	@Override
	public String toString()
	{
		return "Account [balance = " + balance + "]";
	}

}
