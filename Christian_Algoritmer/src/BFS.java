import java.io.*;
import java.util.*;
class node{
	public int dist,id;
	public boolean visited;
	public node(int dist, int id) {
		super();
		this.dist = dist;
		this.id = id;
	}
}

class Graph3 {
	// The number of vertices
	private final int V;

	// Contains edges represented as adjacency lists
	private LinkedList<Integer>[] edges;

	// Adds a directed edge between v1 and v2
	public void addEdge(int v1, int v2) {
		edges[v1].add(v2);

	}

	// Should return the length of the shortest path between
	// the start and the end node in the graph.
	public int bfs(int start, int end) {
		if (start == end) return 0; 
		int distance = 0;
		LinkedList<node> visitingList = new LinkedList<>();
		visitingList.add(new node(distance, start));
		LinkedList<Integer> visited = new LinkedList<>();
		while (!visitingList.isEmpty()){
			node cur = visitingList.removeFirst();
			for (int edge : edges[cur.id]){
				if (edge == end){
					return cur.dist+1;
				}
				if (!visited.contains(edge)){
					visitingList.add(new node(cur.dist+1, edge));
				}
				
				
			}
			visited.add(cur.id);

		}
		return -1;


	}

	// ##################################################
	// # You do not need to modify anything below here. #
	// ##################################################

	// Creates a graph with V vertices and no edges.
	public Graph3(int V) {
		this.V = V;
		edges = (LinkedList<Integer>[]) new LinkedList[V];

		for (int i = 0; i < V; i++) {
			edges[i] = new LinkedList<Integer>();
		}
	}
}

public class BFS {
	public static void main(String[] args) throws IOException {
		new BFS().run();
	}

	private void run() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		int V = Integer.parseInt(in.readLine());
		Graph3 graph = new Graph3(V);

		int E = Integer.parseInt(in.readLine());

		for (int i = 0; i < E; i++) {
			StringTokenizer st = new StringTokenizer(in.readLine());
			graph.addEdge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}

		System.out.println(graph.bfs(0, 1));
	}
}
