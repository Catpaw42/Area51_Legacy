package spil.fields;

import java.util.Arrays;

import spil.Decorator;

public class Tax extends Field
{

	private int taxAmount;
	private int taxRate = -1;

	public Tax(String title, int taxAmount, int taxRate)
	{
		super(title);
		this.taxAmount = taxAmount;
		this.taxRate = taxRate;
	}

	public Tax(String title, int taxAmount)
	{
		super(title);
		this.taxAmount = taxAmount;
	}

	public void landOnField(spil.Player p)
	{
		if (taxRate != -1)
		{
			if (Decorator.getUserLeftButtonPressed("" + taxAmount, taxRate + "%", decoratorMessage()))
			{
				try
				{
					p.getAccount().withdraw(taxAmount);
				} catch (Exception e)
				{
					// fallit
					p.goBroke();
				}

			} else
			{
				try
				{
					p.getAccount().withdraw((p.getTotalAssets() * taxRate) / 100); // rounds down
				} catch (Exception e)
				{
					p.goBroke();
				}
			}
		} else
		{
			Decorator.showMessage(decoratorMessage());
			try
			{
				p.getAccount().withdraw(taxAmount);
			} catch (Exception e)
			{
				// fallit
				p.goBroke();
			}
		}
	}

	protected String[] decoratorMessage()
	{
		String[] first = super.decoratorMessage();
		if (taxRate != -1)
		{
			String[] second = { "TaxChoiseMessage" };
			String[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
		} else
		{
			String[] second = { "taxPayMessage", Integer.toString(taxAmount) };
			String[] result = Arrays.copyOf(first, first.length + second.length);
			System.arraycopy(second, 0, result, first.length, second.length);
			return result;
			
		}
	}

	@Override
	public String toString()
	{
		return "Tax [taxAmount=" + taxAmount + ", taxRate=" + taxRate
				+ ", title=" + title + "]";
	}

}
