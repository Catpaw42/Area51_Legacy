package game.cards;

public class PayCard extends Card {
	private int amount;
	public PayCard(String cardDescription, int amount) {
		super(cardDescription);
		this.amount = amount;
	}

	

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
