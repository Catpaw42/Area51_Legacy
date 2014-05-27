package game.cards;

public abstract class Card
{
	private String cardDescription;
	
	public Card(String cardDescription){
		
		this.cardDescription = cardDescription;
	}
	
	public String setCardDescription(){
		
		return cardDescription;
	}
	//TODO: Implement me!!! Probably with inheritance

	public String getCardDescription() {
		// TODO Auto-generated method stub
		return cardDescription;
	}
	

}
