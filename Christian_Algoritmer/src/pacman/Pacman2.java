package pacman;

import java.io.*;
import java.util.*;

public class Pacman2 {
	// Should return the total number of ghost that can
	// possibly reach Pacman.
	private class Node{
		public int X = 0;
		public int Y = 0;
		public char value;

		public Node(char value, int X, int Y){
			this.X = X;
			this.Y = Y;
			this.value = value;
		}
	}
	private char[][] map;
	private LinkedList<Node> queue = null;
	public int countReachableGhosts(int N, char[][] map) {
		this.map = map;
		queue = new LinkedList<Node>();
		int ghostCount = 0;
		Node pacman = null;
		System.out.println(new Date().getTime());
		// finding pacman and adding him to queue
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
			{
				if(this.map[i][j] == 'P')
				{
					queue.push(new Node('P', i, j));
					break;
				}
				if(pacman != null)
					break;
			}
		}
		// while there still is a field in queue all of its neighbours who haven't been added to queue will be added
		// the field that is being popped is checked if it is a ghost, if so the counter will be raised by one
		while(!queue.isEmpty()){
			Node pop = queue.pop();
			addNeighboursToQueue(pop);
			if(pop.value == 'G')
				ghostCount++;
		}
		System.out.println(new Date().getTime());
		return ghostCount;
	}

	// adding all neighbours to queue
	private void addNeighboursToQueue(Node node){
		int N = map.length-1;
		int x = node.X;
		int y = node.Y;
		
		if(x < N)
		{
			if(map[x+1][y] != '#')
			{
				queue.push(new Node(map[x+1][y], x+1, y));
				map[x+1][y] = '#';
			}
		}
		if(x > 0)
		{
			if(map[x-1][y] != '#')
			{
				queue.push(new Node(map[x-1][y], x-1, y));
				map[x-1][y] = '#';
			}
		}
		if(y < N)
		{
			if(map[x][y+1] != '#')
			{
				queue.push(new Node(map[x][y+1], x, y+1));
				map[x][y+1] = '#';
			}
		}
		if(y > 0)
		{
			if(map[x][y-1] != '#')
			{
				queue.push(new Node(map[x][y-1], x, y-1));
				map[x][y-1] = '#';
			}
		}
	}


	// ##################################################
	// # You do not need to modify anything below here. #
	// ##################################################
	public static void main(String[] args) throws IOException {
		new Pacman2().run();
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

		System.out.println(countReachableGhosts(N, map));
	}
}


