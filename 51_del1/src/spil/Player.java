/**
 * @author Gruppe 51_E13
*/

package spil;

public class Player {
	// variable
	private int score = 0;

	private int[] lastRoll = new int[2]; //Spillerens sidste slag

	private String playerName = null;

	public Player(String playerName) {
		this.playerName = playerName;
	}

	public int[] getLastRoll() {
		return lastRoll;
	}

	public int getScore() {
		return score;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setPlayerName(String player) {
		playerName = player;
	}

	public void addToScore(int points) {
		score = score + points;
	}

	public void setLastRoll(int[] lastRoll) {
		this.lastRoll = lastRoll;
	}

	public String toString() {
		return playerName + " : " + score + "\t" + lastRoll[0] + ","
				+ lastRoll[1];
	}
}
