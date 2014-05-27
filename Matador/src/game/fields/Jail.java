package game.fields;

public class Jail extends Refuge
{
	private int bail;
	
	public Jail(String title, int bail, String[] subText, String[] description)
	
	{
		super(title, subText, description);
		this.bail = bail;
	}

	
	public int getBail() {
		return bail;
	}

	public void setBail(int bail) {
		this.bail = bail;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
