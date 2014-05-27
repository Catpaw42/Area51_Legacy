package game.cards;

public class MoveToCard extends Card{

	private int fieldNumber;
	public MoveToCard(String cardDescription, int fieldNumber) {
		super(cardDescription);
		this.setFieldNumber(fieldNumber);
	}
	public int getFieldNumber() {
		return fieldNumber;
	}
	public void setFieldNumber(int fieldNumber) {
		this.fieldNumber = fieldNumber;
	}

}
