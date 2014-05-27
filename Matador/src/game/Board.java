package game;

import game.fields.*;
import game.fields.Street.Group;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Board {

	private Properties fieldProperties;
	private Field[] fields;
	private final int DEFAULT_NUMBER_OF_FIELDS = 40;

	public Board(int numberOfFields)
	{
		System.out.println(fieldProperties);
		if (numberOfFields == DEFAULT_NUMBER_OF_FIELDS) {
			setupDefaultBoard();
		} else {
			//Future possibility for loading board from propertiesfile
			loadPropertiesFile();
		}
	}

	public void setupDefaultBoard(){
		fields = new Field[DEFAULT_NUMBER_OF_FIELDS];
		fields[0] = new Refuge("Start",new String[] {"Receive",": ","4000"}, new String[]{"Receive"," 4000 ", "WhenPassingStart"});
		fields[1] = new Street("Rødovrevej", 1200, Group.BLUE);
		fields[2] = new Chance("Chance");
		fields[3] = new Street("Hvidovrevej", 1200, Group.BLUE);
		fields[4] = new Tax("Betal indkomstskat", 4000, 10);
		fields[5] = new Shipping("Scandlines Helsingør-Helsingborg", 4000, 500);
		fields[6] = new Street("Roskildevej", 2000, Group.PINK);
		fields[7] = new Chance("Chance");
		fields[8] = new Street("ValbyLanggade", 2000, Group.PINK);
		fields[9] = new Street("Allégade", 2400, Group.PINK);
		fields[10] = new Jail("Jail", 1000, new String[] {"Jail"}, new String[]{"VisitingJail"});
		fields[11] = new Street("FrederiksbergAllé", 2800, Group.GREEN);
		fields[12] = new Brewery("Tuborg Squash", 3000, 100);
		fields[13] = new Street("Bülowsvej", 2800, Group.GREEN);
		fields[14] = new Street("GLKongevej", 3200, Group.GREEN);
		fields[15] = new Shipping("Mols Linjen", 4000, 500);
		fields[16] = new Street("Bernstorffsvej", 3600, Group.GREY);
		fields[17] = new Chance("Chance");
		fields[18] = new Street("Hellerupvej", 3600, Group.GREY);
		fields[19] = new Street("Strandvejen", 4000, Group.GREY);
		fields[20] = new Refuge("Parkering", new String[] {"Refuge"}, new String[] {"TakeBreak"});
		fields[21] = new Street("Trianglen", 4400, Group.RED);
		fields[22] = new Chance("Chance");
		fields[23] = new Street("Østerbrogade", 4400, Group.RED);
		fields[24] = new Street("Grønningen", 4800, Group.RED);
		fields[25] = new Shipping("Scandlines Gedser-Rostock", 4000, 500);
		fields[26] = new Street("Bredgade", 5200, Group.WHITE);
		fields[27] = new Street("KgsNytorv", 5200, Group.WHITE);
		fields[28] = new Brewery("Coca Cola", 3000, 100);
		fields[29] = new Street("Østergade", 5600, Group.WHITE);
		fields[30] = new GotoJail("GotoJail");
		fields[31] = new Street("Amagertorv", 6000, Group.YELLOW);
		fields[32] = new Street("Vimmelskaftet", 6000, Group.YELLOW);
		fields[33] = new Chance("Chance");
		fields[34] = new Street("Nygade", 6400, Group.YELLOW);
		fields[35] = new Shipping("Scandlines Rødby-PuttGarden", 4000, 500);
		fields[36] = new Chance("Chance");
		fields[37] = new Street("Frederiksberggade", 7000, Group.PURPLE);
		fields[38] = new Tax("Betal ekstraordinær statsskat", 2000);
		fields[39] = new Street("Rådhuspladsen", 8000, Group.PURPLE);

	}
	private void loadPropertiesFile() {
		fieldProperties = new Properties();
		try {
			InputStream inputStream = new FileInputStream("src\\Field.properties");
			fieldProperties.load(inputStream);
			inputStream.close();
		}	
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public  Field[] getFields() {
		return fields;
	}
	public Field getFieldByNumber(int fieldnumber) {
		return fields[fieldnumber-1];
	}
	public static void main(String [] args){
		Board board = new Board(40);
		System.out.println(board.getFields()[1].toString());

	}

}
