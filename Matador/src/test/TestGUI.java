package test;

import boundaryToMatador.GUI;

public class TestGUI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GUI.showMessage("ARRRGH");
		GUI.addPlayer("Brian", 20000);
		GUI.showMessage("ARRRGH");
		GUI.setOwner(2, "Brian");
		//GUI.setRent
		//GUI.setPrice
		GUI.showMessage("ARRGH");
		GUI.setSubText(2, "Price 2000");
		GUI.setHotel(2, true);
		GUI.showMessage("AARRRGH");
		GUI.setDescriptionText(2, "<CENTER>Owner" + "Brian</CENTER>" + "Rent <BR><BR><BR><BR>" + "2000 " );
		GUI.setSubText(2, "SUBTEXT <BR><BR><BR><BR> ");
	}

}
