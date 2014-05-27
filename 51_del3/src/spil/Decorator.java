package spil;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import boundaryToMatador.GUI;

public class Decorator
{
	private static Translator t = null;
	private static spil.fields.Field[] f = null;
	
	static
	{
		//setup a disposable input panel, to select language
		//using this method to avoid having to show the GUI before it is setup correctly
		Object[] options = { "English", "Danish", "Faroese" };
		String language = (String) JOptionPane.showInputDialog(new JFrame(),
				"select language", "Language", JOptionPane.PLAIN_MESSAGE, null,
				options, "English");
		if (language == null)
		{
			//someone pressed the windows "close" or the "cancel" button on the selection window
			//instead of selecting a language. We terminate the program.
			System.err.println("No language was selected, exiting program");
			//abandon ship!!
			System.exit(1);
		}

		//try contains the code we "try to run", if any errors occurs in this code-block
		//we handle them in catch.
		//catch is never run if try is successful.
		try
		{
			// create a translator(private class) with the selected language.
			t = new Translator();
			InputStream in = new FileInputStream(language + ".properties"); //throws FileNotFoundException
			t.load(in); //throws IOException
			in.close(); //throws IOException
		}
		//If FileInputStream can't find the specified file, it
		//throws a FileNotFoundException which we catch here.
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.exit(0);
		} catch (IOException e) //t.load and in.close both throw this exception if something goes wrong.
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
			System.exit(0);
		}
	}
	
	public static void setupGUI(spil.fields.Field[] fields)
	{
		f = fields;
		
		// create an array of fields for the GUI
		boundaryToMatador.Field[] gui_fields = new boundaryToMatador.Field[fields.length];
		Random rand = new Random();

		// load the data from the fields into the GUI array
		for (int i = 0; i < gui_fields.length; i++)
		{
			float r = (float) ((rand.nextFloat()/3.0) + 0.5);
			float g = (float) ((rand.nextFloat()/3.0) + 0.5);
			float b = (float) ((rand.nextFloat()/3.0) + 0.5);
			//If fields is a territory
			if(fields[i].getClass() == spil.fields.Territory.class)
			{
				gui_fields[i] = new boundaryToMatador.Street.Builder()
									 .setTitle(t.getProperty(fields[i].title))
									 .setSubText(t.getProperty("priceMessage") + ((spil.fields.Ownable) fields[i]).getPrice())
									 .setDescription(t.getProperty(fields[i].title + "Desc"))
									 .setBgColor(new Color(r,g,b))
									 .build();
			}
			//If fields is a refuge
			else if(fields[i].getClass() == spil.fields.Refuge.class)
			{
				gui_fields[i] = new boundaryToMatador.Refuge.Builder()
						 			 .setTitle(t.getProperty(fields[i].title))
						 			 .setSubText(t.getProperty("refugeType"))
						 			 .setDescription(t.getProperty(fields[i].title + "Desc"))
						 			 .build();
			}
			//If fields is a laborcamp
			else if(fields[i].getClass() == spil.fields.LaborCamp.class)
			{
				gui_fields[i] = new boundaryToMatador.Brewery.Builder()
									 .setTitle(t.getProperty(fields[i].title))
									 .setSubText(t.getProperty("priceMessage") + ((spil.fields.Ownable) fields[i]).getPrice())
									 .setDescription(t.getProperty(fields[i].title + "Desc"))
									 .build();
			}
			//If fields is a tax
			else if(fields[i].getClass() == spil.fields.Tax.class)
			{
				gui_fields[i] = new boundaryToMatador.Tax.Builder()
						 			 .setDescription(t.getProperty(fields[i].title))
						 			 .setTitle(t.getProperty("taxType"))
						 			 .setSubText(t.getProperty(fields[i].title + "Desc"))
						 			 .setBgColor(new Color(r,g,b))
						 			 .build();
			}
			//If fields is a fleet
			else if(fields[i].getClass() == spil.fields.Fleet.class)
			{
				gui_fields[i] = new boundaryToMatador.Shipping.Builder()
									 .setTitle(t.getProperty(fields[i].title))
									 .setSubText(t.getProperty("priceMessage") + ((spil.fields.Ownable) fields[i]).getPrice())
									 .setDescription(t.getProperty(fields[i].title + "Desc"))
									 .build();
			}	
		}
		// setup the GUI with the array.
		GUI.create(gui_fields);
	}

	public static void showMessage(String... message)
	{
		GUI.showMessage(translateString(message));
	}
	
	public static String getUserString(String... message)
	{
		return GUI.getUserString(translateString(message));
	}
	
	private static String translateString(String... message)
	{
		String message_string = "";
		for (int i = 0; i < message.length; i++)
		{
			message_string = message_string + t.getProperty(message[i]);
		}
		return message_string;
	}
	
//	public static String getUserButtonPressed(String message, String... buttons)
//	{
//		message = t.getProperty(message);
//		String[] transButtons = new String[buttons.length];
//		
//		for (int i = 0; i < buttons.length; i++)
//			transButtons[i] = t.getProperty(buttons[i]);
//		
//		String selection = GUI.getUserButtonPressed(message, transButtons);
//		
//		for (int i = 0; i < transButtons.length; i++)
//		{
//			if(transButtons[i].equals(selection))
//				return buttons[i];
//		}
//		//should never be reachable
//		return null;
//	}
	
	public static String getUserSelection(String message, String... options){
		
		message = t.getProperty(message);
		String[] transOptions = new String[options.length];
		
		for (int i = 0; i < options.length; i++)
			transOptions[i] = t.getProperty(options[i]);
		
		String selection = GUI.getUserSelection(message, transOptions);
		
		for (int i = 0; i < transOptions.length; i++)
		{
			if(transOptions[i].equals(selection))
				return options[i];
		}
		//should never be reachable
		return null;
	}
	
	public static boolean getUserLeftButtonPressed(String leftButton, String rightButton, String... message)
	{
		return GUI.getUserLeftButtonPressed(translateString(message), t.getProperty(leftButton), t.getProperty(rightButton));
	}

	public static void close()
	{
		GUI.close();
	}

	public static void setDice(int i, int j)
	{
		GUI.setDice(i, j);
	}

	public static void addPlayer(String playerName, int score)
	{
		GUI.addPlayer(playerName, score);
	}
	
	public static void addPlayer(String playerName, int balance, Color color) {
		GUI.addPlayer(playerName, balance, color);
	}

	public static void setCar(int fieldNumber, String playerName)
	{
		GUI.removeAllCars(playerName);
		GUI.setCar(fieldNumber, playerName);

	}

	public static void setBalance(String playerName, int score)
	{
		GUI.setBalance(playerName, score);
	}
	
	public static void setOwnerOfField(spil.fields.Ownable o)
	{
		if(o.getOwner() == null)
		{
			for (int i = 0; i < f.length; i++)
			{
				if(f[i].title == o.title)
					GUI.setSubText(i + 1, t.getProperty("priceMessage") + o.getPrice());
			}
		}
		else
		{
			for (int i = 0; i < f.length; i++)
			{
				if(f[i].title == o.title)
					GUI.setSubText(i + 1, o.getOwner().getPlayerName());
			}
		}
	}
	
	//private class, extends Properties to handle translating non-existing strings properly
	@SuppressWarnings("serial")
	private static class Translator extends Properties{
		
		//overrides the properties get method, to handle null
		@Override
		public String getProperty(String key)
		{
			String temp;
			return (temp = super.getProperty(key)) == null ? key : temp;
		}
	}
}
