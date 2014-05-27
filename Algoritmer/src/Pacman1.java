import java.io.*;
import java.util.*;

public class Pacman1 {
	// Should return the total number of ghosts
	// on the map.
	public int countGhosts(int N, char[][] map) {
		int ghostCounter=0;
		for (int i =0;i<N;i++){
			for (int j = 0; j<N;j++){
				if (map[i][j] =='G') ghostCounter++;
			}
		}
		return ghostCounter;
	}

	// ##################################################
	// # You do not need to modify anything below here. #
	// ##################################################
	public static void main(String[] args) throws IOException {
		new Pacman1().run();
	}

	private void run() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(in.readLine());
		char[][] map = new char[N][N];

		for (int i = 0; i < N; i++) {
			String line = in.readLine();
			for (int j = 0; j < N; j++) {
				map[i][j] = line.charAt(j);
			}
		}

		System.out.println(countGhosts(N, map));
	}
}

