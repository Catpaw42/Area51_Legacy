package game.cards;

import game.Player;

public class GetOutOfJailCard extends Card {
	private Player owner;
	public GetOutOfJailCard(String cardDescription) {
		super(cardDescription);
		this.setOwner(null);
	}
	
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}

}
