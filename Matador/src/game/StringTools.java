package game;

public class StringTools {
	public static String[] add(String[] strings1, String[] strings2){
		String[] returnStrings = new String[strings1.length+strings2.length];
		for (int i=0;i<strings1.length;i++){
			returnStrings[i]=strings1[i];
		}
		for (int j=0;j<strings2.length;j++){
			returnStrings[strings1.length+j]=strings2[j];
		}
		return returnStrings;


	}
	public static String[] add(String[] stringArray, String string){
		String[] newArray = new String[stringArray.length+1];
		for (int i = 0; i<stringArray.length;i++){
			newArray[i]=stringArray[i];
		}
		newArray[stringArray.length]=string;
		return newArray;
	}
	
	public static void main(String[] args){
		System.out.println(StringTools.add(new String[] {"Test", "test2"}, "Fætter")[2]);
	}

}
