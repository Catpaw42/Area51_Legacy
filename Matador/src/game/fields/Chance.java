package game.fields;

public class Chance extends Field
{
	public Chance(String title)
	{
		super(title);
	}
	//TODO implement decoratorMessage
	@Override
	public String toString() {
		return "Chance [title=" + title + "]";
	}

}
