package game.fields;

import game.Player;

public class GotoJail extends Field {

	public GotoJail(String title) {
		super(title);
	}

	@Override
	public String toString() {
		return "GotoJail [title=" + title + "]";
	}
	@Override
	public String[] decoratorMessage(Player player) {
		// TODO Auto-generated method stub
		return super.decoratorMessage(player);
	}

}
