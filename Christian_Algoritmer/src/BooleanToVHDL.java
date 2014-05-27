import java.util.Scanner;


public class BooleanToVHDL {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		System.out.println("Indtast logisk udtryk - husk () rundt om not (!a)");
		while (input !="q"){
			String VHDLString = input.replace("*", " and ")
					.replace("+", " or ")
					.replaceAll("=", " <= ")
					.replaceAll("!", " not ");
			System.out.println(VHDLString);
		
			input = scan.nextLine();
		}
		scan.close();
	}

}
