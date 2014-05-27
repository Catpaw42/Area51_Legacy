package spil;

import spil.fields.*;

public class Board
{

	private Field[] fields;

	public Board(DiceCup dc)
	{
		fields = new Field[21];
		
		fields[0] = new Territory("Encampment", 1000, 100);
		fields[1] = new Territory("Crater", 1500, 300);
		fields[2] = new Territory("Mountain", 2000, 500);
		fields[3] = new Territory("ColdDesert", 3000, 700);
		fields[4] = new Territory("BlackCave", 4000, 1000);
		fields[5] = new Territory("Werewolf", 4300, 1300);
		fields[6] = new Territory("Village", 4750, 1600);
		fields[7] = new Territory("SouthCitadel", 5000, 2000);
		fields[8] = new Territory("PalaceGates", 5500, 2600);
		fields[9] = new Territory("Tower", 6000, 3200);
		fields[10] = new Territory("Castle", 8000, 4000);
		fields[11] = new Refuge("WalledCity", 5000);
		fields[12] = new Refuge("Monastery", 500);
		fields[13] = new LaborCamp("Huts", 2500, 100, dc);
		fields[14] = new LaborCamp("ThePit", 2500, 100, dc);
		fields[15] = new Tax("GoldMine", 2000);
		fields[16] = new Tax("Caravan", 4000, 10);
		fields[17] = new Fleet("SecondSail", 4000, 250);
		fields[18] = new Fleet("SeaGrover", 4000, 250);
		fields[19] = new Fleet("TheBuccaneers", 4000, 250);
		fields[20] = new Fleet("PrivateerArmada", 4000, 250);
		//Shuffles Fields to randomize Board
		fields = (Field[]) Shuffler.Shuffle(fields); //Casts to Field[] beacuse return type is Object[]
	
	}

	public Field[] getFields()
	{
		return fields;
	}

}
