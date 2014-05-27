package game.fields;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import game.Player;

public abstract class Field {	
	protected final String title;
	private static Properties FieldProperties;
	
	//Constructor
	protected Field(String title){
		this.title = title;
		
	}
	
	public String getTitle() {
		return title;
	}

	protected String[] decoratorMessage(Player player) {
		return new String[] {player.getPlayerName(), ", ","YouHaveLandedOn" , " " , title,};
	}
//Inefficient - Loads Properties File
	protected synchronized Properties getFieldProperties() {
		if (FieldProperties ==null) {
			FieldProperties = new Properties();
			try {
				InputStream inputStream = new FileInputStream(
						"src\\Field.properties");
				FieldProperties.load(inputStream);
				inputStream.close();
				System.out.println("propertiesLoaded");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return FieldProperties;
	}
}
