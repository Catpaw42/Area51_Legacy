package test;
import spil.DiceCup;

public class Test {

	public static void main(String[] args) {
		// Test af Raflebægeret
		final int NUM_THROWS = 1000; //Antal kast med terningerne
		int[] testArray = new int[11]; //Array til at holde antal kastede 
		DiceCup diceCup1 = new DiceCup(); //Raflebægeret
		
		for (int i = 0; i <NUM_THROWS; i++) {  //slår 100 slag og lægger det til i testArray
			int faceValue = diceCup1.rollDice();
			// System.out.println(diceCup1); //printer terningerne
			testArray[faceValue-2]++;
		}
		
		//Udskriver antal af hvert slag
		for (int j = 0; j < testArray.length; j++) {
			System.out.println("Antal "+ (j+1) +"'ere: " + testArray[j]);
		}
		
		float chiArray[] = new float[11]; //Array til at holde chi^2 værdier for hvert enkelt slag
		float expected; //Forventet frekvens af et givent slag
		for (int i = 0; i < chiArray.length; i++) {
			switch (i) {
			case 0: //slaget 2
				expected = (float) ((float)NUM_THROWS/36.0); //Forventet frekvens for slaget 2
				break;
			case 1: //slaget 3
				expected = (float) (2*(float)NUM_THROWS/36.0);
				break;
			case 2: //slaget 4
				expected = (float) (3*(float)NUM_THROWS/36.0);
				break;
			case 3: //slaget 5
				expected = (float) (4*(float)NUM_THROWS/36.0);
				break;
			case 4: //slaget 6
				expected = (float) (5*(float)NUM_THROWS/36.0);
				break;
			case 5: //slaget 7
				expected = (float) (6*(float)NUM_THROWS/36.0);
				break;
			case 6: //slaget 8
				expected = (float) (5*(float)NUM_THROWS/36.0);
				break;
			case 7: //slaget 9
				expected = (float) (4*(float)NUM_THROWS/36.0);
				break;
			case 8: //slaget 10
				expected = (float) (3*(float)NUM_THROWS/36.0);
				break;
			case 9: //slaget 11
				expected = (float) (2*(float)NUM_THROWS/36.0);
				break;
			case 10: //slaget 12
				expected = (float) ((float)NUM_THROWS/36.0);
				break;
			default:
				expected = 0;
				break;
			}
			if (expected != 0) chiArray[i] = (float) ((Math.pow(expected-(float)testArray[i], 2))/expected);
		}
		for (int j = 0; j < chiArray.length; j++) {
			System.out.println("Differens mellem forventet frekvens og observeret frekvens opløftet til 2. potens for "+ (j+2) +"'ere: " + chiArray[j]);
		}
		
		float chiSquared = 0;
		for (float value : chiArray){
			chiSquared +=value;
		}
		System.out.println("Chi i anden: " + chiSquared);
		System.out.println("For to terninger der fungerer korrekt, vil vi forvente in Chi^2 værdi <25.188 (i 95% af tilfædene)");
	}

}
