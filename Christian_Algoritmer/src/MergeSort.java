import java.io.*;
import java.util.*;

public class MergeSort
{
	// This method takes an array of integers as input parameter,
	// and it should then return the integers sorted
	// in ascending order using the MergeSort algorithm.
	private int[] sort(int[] numbers) {
		if (numbers.length<=1) return numbers;
		int[] A1 = new int[numbers.length/2];
		int[] A2 = new int[numbers.length-numbers.length/2];
		for (int i = 0;i<A1.length;i++)	A1[i]=numbers[i];
		for (int j=0;j<A2.length;j++) A2[j]=numbers[A1.length+j];
		return merge(sort(A1),sort(A2));
	}

	private int[] merge(int[] A1, int[] A2) {
		int i=0,j=0;
		int[] R = new int[A1.length+A2.length];
		while (i+j<R.length){
			//Still numbers in A1 and A2
			if (i<A1.length && j<A2.length){
				if (A1[i]<=A2[j]) {
					R[i+j]=A1[i];
					i++;
				} else {
					R[i+j]=A2[j];
					j++;
				}
			} else if (i<A1.length){
				R[i+j]= A1[i];
				i++;	
			} else if (j<A2.length){
				R[i+j]=A2[j];
				j++;
			}
		}
		return R;
	}

	// ##################################################
	// # You do not need to modify anything below here. #
	// ##################################################

	public static void main(String[] args) throws IOException {
		new MergeSort().run();
	}

	private void run() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int[] numbers = readIntArray(in);

		numbers = sort(numbers);
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + " ");
		}
	}

	private int[] readIntArray(BufferedReader in) throws IOException {
		int length = Integer.parseInt(in.readLine());
		int[] array = new int[length];
		StringTokenizer st = new StringTokenizer(in.readLine());

		for (int i = 0; i < length; i++) {
			array[i] = Integer.parseInt(st.nextToken());
		}
		return array;
	}
}