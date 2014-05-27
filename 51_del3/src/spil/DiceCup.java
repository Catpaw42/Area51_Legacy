/**
 * @author Gruppe 51_E13
*/

package spil;

public class DiceCup {

	// private variable

	private int sum, twoOfaKind;
	private Die die1, die2;

	// constructor laver terning 1 og terning 2
	// constructor laver en rollDice, så man får summen af terning 1 og 2
	public DiceCup() {
		//added a try-catch to deal with the exception
		try
		{
			die1 = new Die();
			die2 = new Die();
		} catch (Exception e)
		{
			e.getStackTrace();
		}
		
	}

	// kaster terninger og returnerer summen
	public int rollDice() {
		sum = (die1.roll() + die2.roll());
		return sum;
	}

	// laver getter af sum til controller
	public int getSum() {
		return sum;
	}

	// metode - returnerer facevalue af terning 1 og 2 i en liste (array)
	public int[] getDiceFaceValues() {
		int[] DiceFaceValues = new int[2];
		DiceFaceValues[0] = die1.getFaceValue();
		DiceFaceValues[1] = die2.getFaceValue();
		return DiceFaceValues;
	}

	// finder ud af om man har slået to ens.
	// Hvis to ens -> returneres hvad du har slået to ens af (fx to 4, returneres 4)
	// ellers returnerer den 0
	public int getTwoOfAKind() {
		if (die1.getFaceValue() == die2.getFaceValue()) {
			twoOfaKind = die1.getFaceValue();
		} else {
			twoOfaKind = 0;
		}
		return twoOfaKind;

	}
	public String toString(){
		return ("Terningernes værdier " + die1.getFaceValue() + " og " + die2.getFaceValue());
	}

}
