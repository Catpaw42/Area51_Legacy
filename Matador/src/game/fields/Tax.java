package game.fields;

import game.Player;
import game.StringTools;

public class Tax extends Field
{

	private int taxAmount;
	private int taxRate;

	public Tax(String title, int taxAmount, int taxRate)
	{
		super(title);
		this.taxAmount = taxAmount;
		this.taxRate = taxRate;
	}
	//Overload
	public Tax(String title, int taxAmount)
	{
		super(title);
		this.taxRate = -1;
		this.taxAmount = taxAmount;
	}
	@Override
	public String[] decoratorMessage(Player player){
		return StringTools.add(super.decoratorMessage(player), taxDescription());

	}

	private String[] taxDescription() {
			if (taxRate <0){
		return new String[]{"YouMustPayTax",": ",String.valueOf(taxAmount)};
			} else {
				return new String[]{"YouMustPayTax",": ", String.valueOf(taxAmount)," ", "or"," ", String.valueOf(taxRate), "%"};
			}
	}

	@Override
	public String toString()
	{
		return "Tax [taxAmount=" + taxAmount + ", taxRate=" + taxRate
				+ ", title=" + title + "]";
	}

	public int getTaxrate() {
		return taxRate;
	}

	public int getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(int taxAmount) {
		this.taxAmount = taxAmount;
	}

	public int getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(int taxRate) {
		this.taxRate = taxRate;
	}

}
