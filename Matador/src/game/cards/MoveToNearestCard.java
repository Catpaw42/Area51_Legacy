package game.cards;

public class MoveToNearestCard extends Card {
	private int rentModifier;
	public MoveToNearestCard(String cardDescription, int rentModifier) {
		super(cardDescription);
		this.rentModifier = rentModifier;
	}
	public int getRentModifier() {
		return rentModifier;
	}
	public void setRentModifier(int rentModifier) {
		this.rentModifier = rentModifier;
	}
}
