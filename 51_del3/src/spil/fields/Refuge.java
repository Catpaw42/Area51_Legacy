package spil.fields;

import java.util.Arrays;

import spil.Decorator;
import spil.Player;


public class Refuge extends Field {
	private int bonus;

	public Refuge(String title, int bonus) {
		super(title);
		this.bonus=bonus;
	}

	@Override
	public void landOnField(Player p) {
		//tells GUI that player has landed on Refuge
		Decorator.showMessage(decoratorMessage(p));
		// deposit bonus for a playeraccount	
		try {
			p.getAccount().deposit(bonus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String[] decoratorMessage(Player p){
		String[] first = super.decoratorMessage();
		String[] second = {"YouReceiveMessage", Integer.toString(this.bonus)};
		String[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return  result;
	}
	@Override
	public String toString() {
		return title + "- bonus:" + bonus;
	}
}
