package game.cards;

public class PayForBuildingsCard extends Card {

	private int houseExpense;
	private int hotelExpense;

	public PayForBuildingsCard(String cardDescription, int houseExpense, int hotelExpense) {
		super(cardDescription);
		this.houseExpense = houseExpense;
		this.hotelExpense = hotelExpense;
	}


	public int getHousePrice() {
		return houseExpense;
	}

	public void setHousePrice(int housePrice) {
		this.houseExpense = housePrice;
	}

	public int getHotelPrice() {
		return hotelExpense;
	}

	public void setHotelPrice(int hotelPrice) {
		this.hotelExpense = hotelPrice;
	}
}
