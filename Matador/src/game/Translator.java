package game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Translator {
	private String language;
	private Properties languageProperties;

	public Translator(String language) {
		super();
		this.setLanguage(language);
		//Load properties from file
		languageProperties = new Properties();
		try {
			InputStream inputStream = new FileInputStream("src\\" + language + ".properties");
			languageProperties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//translates String to String
	public String translate (String orgString){
		return languageProperties.getProperty(orgString, orgString);
	}

	//Translates a String [] to String [] in selected language
	public String[] translate (String[] orgStringArray){
		String[] translatedStringArray = new String[orgStringArray.length];
		for (int i = 0; i < orgStringArray.length; i++) {
			translatedStringArray[i] = translate(orgStringArray[i]);
		}
		return translatedStringArray;
	}

	public String translateConcat (String[] orgStringArray){
		String[] translatedArray = translate(orgStringArray);
		//Concatenate strings from String Array
		StringBuilder builder = new StringBuilder();
		for (String string : translatedArray){
			builder.append(string);
		}
		return builder.toString();
	}
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	//Test driver
	public static void main(String[] args){
		Translator testTranslator = new Translator("danish");
		System.out.println(testTranslator.translate("Price"));
		System.out.println(testTranslator.translate("theKingsBirthday"));
		String[] testStringArray = new String[] {"testString", "theKingsBirthday"};
		testStringArray = testTranslator.translate(testStringArray);
		System.out.println(testStringArray[0] + testStringArray[1]);
		System.out.println(testTranslator.translateConcat(testStringArray));
	}

	
}
