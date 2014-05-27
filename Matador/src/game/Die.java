/**
 * @author Gruppe 51_E13
*/

//Ubrugt experimentel klasse til senere brug...
package game; 
import java.util.Random;

public class Die {
	private int faceValue;
	private final int NUM_SIDES;
	private Random rand;
	private final static int DEFAULT_NUM_SIDES = 6;
	
	//Constructor - Creates Die with numSides sides. - Future proofing (throws Exception at illegal numSides)
	/**
	 * @param numSides
	 * @throws Exception
	 */
	public Die(int numSides) throws Exception{
		// Constructor TODO Subclass Exception
		if (numSides < 1) throw new Exception("Wrong number of sides");
		this.NUM_SIDES = numSides;
		this.rand = new Random();
		roll();		
	}
	//Constructor - creates Die with DEFAULT_NUM_SIDES.
	/**
	 * @throws Exception
	 */
	public Die() throws Exception{
		this(DEFAULT_NUM_SIDES);
	}
	/**
	 * Rolls die<br>
	 * @return the faceValue
	 */
	public int roll() {
		// New random faceValue
		this.faceValue = rand.nextInt(NUM_SIDES)+1;
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
	@Override
	public String toString(){ return ("faceValue: " + faceValue);
	}
	
}
