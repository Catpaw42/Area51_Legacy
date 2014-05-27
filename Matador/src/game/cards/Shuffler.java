package game.cards;
import java.util.Random;
//Shuffles an array of objects using Durstenfeld algorithm
public class Shuffler {

	public static Object[] Shuffle(Object[] objectArray) {
		Random rand = new Random();
		for (int i=0; i<objectArray.length; i++) {
			int randomPosition = rand.nextInt(objectArray.length);
			//swap objects at i and randomPosition
			Object temp = objectArray[i];
			objectArray[i] = objectArray[randomPosition];
			objectArray[randomPosition] = temp;
		}
		return objectArray;
	}
	
	//Tests if the Shuffle Works
public static void test(){
	Object[] objects = {new Object(), new Object()};
	System.out.println(objects[1].toString() + objects[0].toString());
	Shuffle(objects);
	System.out.println(objects[1].toString() + objects[0].toString());	
	System.out.println("OK");
}
	//Used to test self
public static void main(String[] args) {
	test();
}
}

