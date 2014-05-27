package game.cards;

public class ReceiveCard extends Card {

	public ReceiveCard(String cardDescription, int amount) {
		super(cardDescription);
		this.amount = amount;
	}

	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
