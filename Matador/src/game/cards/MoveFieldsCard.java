package game.cards;

public class MoveFieldsCard extends Card {
	private int numberOfFields;
	
	public MoveFieldsCard(String cardDescription, int numberOfFields) {
		super(cardDescription);
		this.numberOfFields = numberOfFields;
	}

	public int getNumberOfFields() {
		return numberOfFields;
	}

	public void setNumberOfFields(int numberOfFields) {
		this.numberOfFields = numberOfFields;
	}
}
