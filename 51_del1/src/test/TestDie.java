package test;
import spil.SimpleDie;

public class TestDie {

	public static void main(String[] args) {
		final int NUM_THROWS = 100;
		int[] testArray = new int[6];

		for (int i = 0; i <NUM_THROWS; i++) {
			SimpleDie d1 = new SimpleDie();
			System.out.println(d1);
			testArray[d1.getFaceValue()-1]++;
		}
		for (int j = 0; j < testArray.length; j++) {
			System.out.println("Antal "+ (j+1) +"'ere: " + testArray[j]);
		}
		float chiArray[] = new float[6];
		
		for (int i = 0; i < chiArray.length; i++) {
			chiArray[i] = (float) ((Math.pow(((float)NUM_THROWS/6.0)-(float)testArray[i], 2))/((float)NUM_THROWS/6.0));
		}
		for (int j = 0; j < chiArray.length; j++) {
			System.out.println("Differens mellem forventet frekvens og observeret frekvens opl�ftet til 2. potens for "+ (j+1) +"'ere: " + chiArray[j]);
		}
		
		float chiSquared = 0;
		for (float value : chiArray){

			chiSquared +=value;
		}
		System.out.println("Chi i anden: " + chiSquared);
		System.out.println("For en terning der fungerer korrekt, vil vi forvente in Chi^2 v�rdi <16,75 (i 95% af tilf�dene)");
	}

}
