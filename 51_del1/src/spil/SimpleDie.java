/**
 * @author Gruppe 51_E13
*/

package spil;

import java.util.Random;

public class SimpleDie {
	//int testint = 1; Anvendes til at snyde med terningen
	private int faceValue;
	private Random rand;
	public final static int DEFAULT_NUM_SIDES = 6; // må godt være public

	// Constructor - Laver terning med DEFAULT_NUM_SIDES.
	public SimpleDie() {
		rand = new Random(); //laver en randomizer og
		roll(); //ruller med terningerne
	}

	/**
	 * Rolls die<br>
	 */
	public int roll() {
		// New random faceValue
		//setFaceValue(testint++);
		//if (testint == 8) setFaceValue(1);
		setFaceValue(rand.nextInt(DEFAULT_NUM_SIDES) + 1);
		return faceValue;
	}

	/**
	 * @return the faceValue
	 */
	public int getFaceValue() {
		return faceValue;
	}

	/**
	 * @param faceValue
	 *            the faceValue to set
	 */
	//Kun til snyd ;)
	public void setFaceValue(int faceValue) {
		this.faceValue = faceValue;
	}

	public String toString() {
		return ("faceValue: " + faceValue);
	}

}
