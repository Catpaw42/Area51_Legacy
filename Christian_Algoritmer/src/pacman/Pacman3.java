package pacman;

import java.io.*;
import java.util.*;

public class Pacman3 {
	// Should output the shortest path to a ghost from
	// Pacman. The path should be printed using
	// N S E W to represent up, down, right and left.
	// All symbols in the path should be separated by a whitespace.


	private class Node{
		public int X = 0;
		public int Y = 0;

		public Node(int X, int Y) {
			this.X = X;
			this.Y = Y;
		}
	}
	private char[][] map;
	private LinkedList<Node> queue = null;

	public void findClosestGhost(int N, char[][] map) {
		this.map = map;
		queue = new LinkedList<Node>();
		Node pacman = null;
		// finding pacman and adding him to queue
		for(int i = 0; i < N; i++)
		{
			for(int j = 0; j < N; j++)
			{
				if(this.map[i][j] == 'P')
				{
					queue.add(new Node( i, j));
					break;
				}
				if(pacman != null)
					break;
			}
		}
		// running while there still is a field in queue. if the popped node is a ghost, 
		// its path to pacman is printed, else all of its neighbours are added to queue
		while(!queue.isEmpty()){
			Node pop = queue.remove();
			char c = addNeighboursToQueue(pop);
			if(c != 'F'){
				printPath(pop, c);
				break;
			}
		}
	
	}
	
	private String printPath(Node node, char last)
	{
		int i = node.X;
		int j = node.Y;
		char c = map[i][j];
		LinkedList<Character> list = new LinkedList<Character>();
		
		while(c != 'P')
		{
			switch (c) {
			case 'N':
				i++;
				break;
			case 'S':
				i--;
				break;
			case 'W':
				j++;
				break;
			case 'E':
				j--;
				break;
			default:
				break;
			}
			list.addFirst(c);
			c= map[i][j];

		}
		String path = "";
		for(Character ch : list){
			System.out.print(ch + " ");	
		}
		System.out.println(last);
		return path;
	}

	// adding all neighbours to queue if they are not walls or already checked.
	// all checked are being marked as walls
	private char addNeighboursToQueue(Node node){
		int N = map.length-1;
		int x = node.X;
		int y = node.Y;

		if(x < N)
		{
			if(map[x+1][y] == 'G')
			{
				return 'S';
			}
			if(map[x+1][y] == ' ')
			{
				queue.add(new Node(x+1, y));
				map[x+1][y] = 'S';
			}
		}
		if(x > 0)
		{
			if(map[x-1][y] == 'G')
			{
				return 'N';
			}
			if(map[x-1][y] == ' ')
			{
				queue.add(new Node(x-1, y));
				map[x-1][y] = 'N';
			}
		}
		if(y < N)
		{
			if(map[x][y+1] == 'G')
			{
				return 'E';
			}
			if(map[x][y+1] == ' ')
			{
				queue.add(new Node(x, y+1));
				map[x][y+1] = 'E';
			}	
		}
		if(y > 0)
		{
			if(map[x][y-1] == 'G')
			{
				return 'W';
			}
			if(map[x][y-1] == ' ')
			{
				queue.add(new Node(x, y-1));
				map[x][y-1] = 'W';
			}
		}
		return 'F';
	}


	// ##################################################
	// # You do not need to modify anything below here. #
	// ##################################################
	public static void main(String[] args) throws IOException {
		new Pacman3().run();
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
		findClosestGhost(N, map);
	}
}


