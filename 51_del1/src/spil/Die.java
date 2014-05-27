/**
 * @author Gruppe 51_E13
*/

//Ubrugt experimentel klasse til senere brug...
package spil; 
import java.util.Random;
//
public class Die {
	// Instance variable
	private int faceValue;
	private final int NUM_SIDES;
	private Random rand;
	public final static int DEFAULT_NUM_SIDES = 6; //public - må godt være public da den er final.
	
	//Constructor - Creates Die with numSides sides. - Future proofing (throws Exception ved numSides ugyldig)
	public Die(int numSides) throws Exception{
		// Constructor TODO Subclass Exception
		if (numSides < 1) throw new Exception("Wrong number of sides");
		NUM_SIDES = numSides;
		rand = new Random();
		roll();		
	}
	//Constructor - laver Die med DEFAULT_NUM_SIDES. (modtager og sender exception videre)
	public Die() throws Exception{
		this(DEFAULT_NUM_SIDES);
	}
	/**
	 * Rolls die<br>
	 */
	public int roll() {
		// New random faceValue
		setFaceValue(rand.nextInt(NUM_SIDES)+1);
		return faceValue;
	}
	/**
	 * @return the faceValue
	 */
	public int getFaceValue() {
		return faceValue;
	}
	/**
	 * @param faceValue the faceValue to set
	 */
	public void setFaceValue(int faceValue) {
		this.faceValue = faceValue;
	}
	public String toString(){ return ("faceValue: " + faceValue);
	}
	

}
