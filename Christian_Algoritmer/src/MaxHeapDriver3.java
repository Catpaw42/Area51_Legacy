import java.io.*;
import java.util.*;

class MaxHeap {
	
	private int[] elements = new int[10000];
	private int size = 0;

	public void delete(int i) {
		elements[i]=elements[size-1];
		size--;
		heapifyDown(i);
	}

	public void insert(int e) {
		elements[size] = e;
		int i = size;
		size++;
		heapifyUp(i);
	}

	private void heapifyUp(int i) {
		while (i>0 && elements[i] > elements[parent(i)]){
			int parentIndex = parent(i);
				int temp = elements[parentIndex];
				elements[parentIndex] = elements[i];
				elements[i] = temp;
			
			i = parentIndex;
		}
	}
	

	public int extractMax() {
		int max = elements[0];
		elements[0] = elements[size-1];
		size--;
		heapifyDown();
		return max;
	}

	private void heapifyDown() {
		heapifyDown(0);
	}
	
	private void heapifyDown(int i){
		int l = left(i);
		int r = right(i);
		int largest = 0;
		if (l<size && elements[l]>elements[i]){
			largest = l;
		} else {
			largest = i;
		}
		if (r<size && elements[r]> elements[largest]){
			largest = r;
		}
		if (largest != i){
			int temp = elements[i];
			elements[i] = elements[largest];
			elements[largest] = temp;
			heapifyDown(largest);
		}
	}

	private int parent(int i){
		return ((i+1)/2)-1;
	}
	private int left(int i){
		return (i+1)*2-1;
	}
	private int right(int i){
		return (i+1)*2;
	}

	// ##################################################
	// # You do not need to modify anything below here. #
	// ##################################################

	public void print(PrintWriter out) {
		for (int i = 0; i < size; i++) {
			out.print(elements[i] + " ");
		}
		out.println();
	}
}


public class MaxHeapDriver3
{
	public static void main(String[] args) throws IOException {
		new MaxHeapDriver3().run();
	}

	private void run() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(new BufferedOutputStream(System.out)));

		MaxHeap maxHeap = new MaxHeap();

		int N = Integer.parseInt(in.readLine());

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			String type = st.nextToken();

			if (type.charAt(0) == 'D') {
				maxHeap.delete(Integer.parseInt(st.nextToken()));
			} else if (type.charAt(0) == 'I') {
				maxHeap.insert(Integer.parseInt(st.nextToken()));
			} else if (type.charAt(0) == 'E') {
				out.println(maxHeap.extractMax());
			} else {
				maxHeap.print(out);
			}
		}
		out.flush();
	}
}