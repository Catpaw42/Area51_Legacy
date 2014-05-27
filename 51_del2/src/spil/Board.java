package spil;



import spil.Field;

public class Board
{
	// you have all the needed fields right here.
	private Field[] fields;

	// constructor initializes an array of 11
	public Board ()

	{
		fields = new Field[11];
		setupFields();
	}

	// initalizes all fields with message, amount, and true or false for extra turn.
	private void setupFields()
	{
		fields[0] = new Field("Tower","getMoneySub","TowerDesc", 250, 0);
		fields[1] = new Field("Crater","loseMoneySub","CraterDesc", -200, 0);
		fields[2] = new Field("Palace Gates","loseMoneySub","Palace GatesDesc", -100, 0);
		fields[3] = new Field("Cold Desert","loseMoneySub","Cold DesertDesc",-20, 0);
		fields[4] = new Field("Walled City","getMoneySub","Walled CityDesc", 180, 0);
		fields[5] = new Field("Mona- stary","getMoneySub","Mona- staryDesc", 0, 0);
		fields[6] = new Field("Black Cave","loseMoneySub","Black CaveDesc", -70, 0);
		fields[7] = new Field("Huts","loseMoneySub","HutsDesc", -60, 0);
		fields[8] = new Field("Were- wolf","loseMoneySub","Were- wolfDesc", -80, 1);
		fields[9] = new Field("The Pit","loseMoneySub","The PitDesc", -90, 0);
		fields[10] = new Field("Gold- mine","getMoneySub","Gold- mineDesc", 650, 0);

	}
	public Field[] getFields() {
		return fields;
	}

	//Gets field number 'field' from fields[]
	public Field getField(int field) {
		return fields[field];
	}
	//Sets fields[fieldnumber] to Field 'field' 
	public void setField(int fieldNumber, Field field ) {
		this.fields[fieldNumber] = field;
	}



}
