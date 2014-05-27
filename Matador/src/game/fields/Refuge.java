package game.fields;

import game.Player;
import game.StringTools;

import java.util.Arrays;

public class Refuge extends Field
{
	private String[] subText;
	private String[] description;
	public Refuge(String title, String[] subText, String[] description)
	{
		super(title);
		this.subText = subText;
		this.description = description;
	}

	public String[] getSubText() {
		return subText;
	}

	public void setSubText(String[] strings) {
		this.subText = strings;
	}

	public String[] getDescription() {
		return description;
	}
	public String[] decoratorMessage(Player player){
		return StringTools.add(super.decoratorMessage(player),description);
		
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String[] description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Refuge [subText=" + Arrays.toString(subText) + ", description="
				+ Arrays.toString(description) + "]";
	}




}
