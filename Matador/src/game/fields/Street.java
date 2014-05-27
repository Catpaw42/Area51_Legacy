package game.fields;

import java.util.Arrays;
import java.util.Properties;

import game.Player;
import game.StringTools;

public class Street extends Ownable {

	public enum Group {BLUE, PINK, GREEN, GREY, RED, WHITE, YELLOW, PURPLE}
	private Group group;
	public static final int MAX_NUMBER_OF_BUILDINGS = 5;
	private int buildings;
	private int buildingPrice;
	int[] buildingRents;

	public Street(String title, int price, Group group) {
		super(title, price);
		this.group = group;
		this.buildings = 0;
		buildingRents = new int[MAX_NUMBER_OF_BUILDINGS+1];
		setBuildingRentsFromProperties();
	}

	private void setBuildingRentsFromProperties(){
		Properties props = getFieldProperties();
		for (int i = 0;i<buildingRents.length;i++){
			String key = title + "Rent"+ String.valueOf(i);
			String propString = props.getProperty(key);
			if (propString!=null) {
				int rent = Integer.parseInt(propString);
				buildingRents[i] = rent;
			} else {
				System.err.println("No property for " + key);
			}
		}
		buildingPrice = Integer.parseInt(props.getProperty(title + "BuildingPrice"));
	}



	public String[] decoratorMessage(Player p){
		if (owner==null){
			return StringTools.add(super.decoratorMessage(p),new String[] {"RentMessage", String.valueOf(getBaseRent()),"InRent"});
		}
		return super.decoratorMessage(p);
		
	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
	}


	@Override
	public String toString() {
		return "Street [group=" + group + ", buildings=" + buildings
				+ ", buildingRents=" + Arrays.toString(buildingRents)
				+ ", price=" + price + ", owner=" + owner + ", pawned="
				+ pawned + ", title=" + title + "]";
	}


	@Override
	public int getRent() {
		return  buildingRents[buildings];
	}



	public int getBuildings() {
		return buildings;
	}
	private void setBuildings(int i) {
		this.buildings = i;
	}
	public void addBuilding(){
		setBuildings(getBuildings() + 1);
	}
	public void removeBuilding(){
		setBuildings(getBuildings() - 1);
	}
	public static void main(String[] args){
		Street testStreet = new Street("Rødovrevej", 10000, Group.BLUE);
		testStreet.setBuildingRentsFromProperties();
		System.out.println(testStreet.toString());
	}

	@Override
	public int getBaseRent() {
		return buildingRents[0];
	}
	public int getBuildingPrice(){
		return buildingPrice;
	}

	public int getBuildingSellValue() {
		int buildingSellValue = (buildingPrice/2)*buildings;
		return buildingSellValue;
	}
	public int getBuildingBuyValue(){
		return buildingPrice*buildings;
	}

}
