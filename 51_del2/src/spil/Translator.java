package spil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

public class Translator
{
	// variables.
	private File languageFile;
	private Scanner scan;
	private Hashtable<String, String> hTable = new Hashtable<String, String>();

	// constructor.
	public Translator(String filePosition) throws FileNotFoundException
	{
		languageFile = new File(filePosition);
		if (!languageFile.exists())
			throw new FileNotFoundException("The given language file does not exist");
		scan = new Scanner(languageFile);

		loadLanguageData();

		// close scanner
		scan.close();
	}

	// load the data from the language file into memory.
	private void loadLanguageData()
	{
		// Iterate the entire text file
		while (scan.hasNextLine())
		{
			// read one line at a time
			String input = scan.nextLine();
			
			//splits the string when it meets # and returns an array
			String[] temp = input.split("#");

			// add the two substrings to the hashtable.
			hTable.put(temp[0], temp[1]);
		}
	}

	// Gets the translation of the given string. Returns the new string, or
	// throws an error if no matching string is found
	public String translateString(String input)
	{
		String result = hTable.get(input);
		if(result == null)
		{
			return input;
		}
		return result;
	}

	

}
