package spil;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import boundaryToMatador.*;

public class Decorator
{
	private Translator translator = null;

	public Decorator(Field[] fields)
	{

		Object[] options = { "English", "Danish" };
		String language = (String) JOptionPane.showInputDialog(new JFrame(),
				"select language", "Language", JOptionPane.PLAIN_MESSAGE, null,
				options, "English");
		if (language == null)
		{
			System.out.println("No language was selected, exiting program");
			System.exit(1);
		}

		try
		{
			// create a translator with the selected language.
			translator = new Translator(language + ".lang");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		// create an array of fields for the GUI
		boundaryToMatador.Field[] gui_fields = new boundaryToMatador.Field[fields.length];

		// load the data from the fields into the GUI array
		for (int i = 0; i < gui_fields.length; i++)
		{
			gui_fields[i] = new Start.Builder()
									 .setTitle(translator.translateString(fields[i].getTitle()))
									 .setSubText(translator.translateString(fields[i].getSubText()) + Math.abs(fields[i].getAmount()))
									 .setDescription(translator.translateString(fields[i].getDescription()))
									 .build();
		}
		// setup the GUI with the array.
		GUI.create(gui_fields);
	}

	public void showMessage(String... message)
	{
		String message_string = "";
		for (int i = 0; i < message.length; i++)
		{
			message_string = message_string + translator.translateString(message[i]);
		}
		GUI.showMessage(message_string);
	}

	public String getUserButtonPressed(String message, String... buttons)
	{
		message = translator.translateString(message);
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = translator.translateString(buttons[i]);
		}
		return GUI.getUserButtonPressed(message, buttons);
	}

	public String getUserString(String... message)
	{
		String message_string = "";
		for (int i = 0; i < message.length; i++)
		{
			message_string = message_string + translator.translateString(message[i]);
		}
		return GUI.getUserString(message_string);
	}

	public void close()
	{
		GUI.close();
	}

	public void setDice(int i, int j)
	{
		GUI.setDice(i, j);
	}

	public void addPlayer(String playerName, int score)
	{
		GUI.addPlayer(playerName, score);
	}

	public void setCar(int fieldNumber, String playerName)
	{
		GUI.removeAllCars(playerName);
		GUI.setCar(fieldNumber, playerName);

	}

	public void setBalance(String playerName, int score)
	{
		GUI.setBalance(playerName, score);
	}

}
