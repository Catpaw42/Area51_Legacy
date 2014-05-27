package spil.fields;

public abstract class Field
{	
	public final String title;
	
	protected Field(String title){
		this.title = title;
	}

	public abstract void landOnField(spil.Player p);
	
	//general for all implementations of this: Changed to operate on String[]
	//otherwise works exactly as it did on String.
	protected String[] decoratorMessage(){
		return new String[] {"LandedOnFieldMessage", this.title};
	}
	
	@Override
	public abstract String toString();
}
