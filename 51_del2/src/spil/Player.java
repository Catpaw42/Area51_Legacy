/**
 * @author Gruppe 51_E13
 */
package spil;

public class Player
{
	private String playerName;
	private Account account;
	private int winStatus = 0;
	

	//constructor
	public Player(String playerName)
	{
		this.playerName = playerName;
		account = new Account();
	}

	public Account getAccount()
	{
		return account;
	}
	
	public void setAccount(Account account) {
		this.account = account;
	}

	//returns the players name
	public String getPlayerName()
	{
		return playerName;
	}
	
	//sets the players name
	public void setPlayerName(String player)
	{
		playerName = player;
	}

	@Override
	public String toString()
	{
		return playerName + " : " + account + "\t";
	}

	public int getWinStatus() {
		return winStatus;
	}

	public void setWinStatus(int status) {
		winStatus = status;
	}
}
