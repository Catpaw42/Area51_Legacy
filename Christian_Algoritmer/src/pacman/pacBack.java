package pacman;
 
import java.io.*;
import java.util.*;
 
public class pacBack {
    // Should output the shortest path to a ghost from
    // Pacman. The path should be printed using
    // N S E W to represent up, down, right and left.
    // All symbols in the path should be separated by a whitespace.
 
 
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
 
 
    public void findClosestGhost(int N, char[][] map) {
    	long now = System.nanoTime();//Timing
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
                    queue.add(new Node('P', i, j));
                    break;
                }
            }
        }
        System.out.println("Time Elapsed : " + ((System.nanoTime()-now)/1000000D) + " ms" );
        // running while there still is a field in queue. if the popped node is a ghost, 
        // its path to pacman is printed, else all of its neighbours are added to queue
        while(!queue.isEmpty()){
            Node pop = queue.remove();
            if(pop.value == 'G'){
            	System.out.println("Time Elapsed : " + ((System.nanoTime()-now)/1000000D) + " ms" );
                System.out.println(getPath(pop));
                break;
            }
            addNeighboursToQueue(pop);
        }
     System.out.println("Time Elapsed : " + ((System.nanoTime()-now)/1000000D) + " ms" );
 
    }
     
    private String getPath(Node node)
    {
        int i = node.X;
        int j = node.Y;
        char c = map[i][j];
        LinkedList<Character> list = new LinkedList<Character>();
         
        while(c != 'P')
        {
            if(c == 'N')
            {
                i++;
            }
            else if(c == 'S')
            {
                i--;
            }
            else if(c == 'W'){
                j++;
            }
            else if(c == 'E'){
                j--;
            }
            list.addFirst(c);
            c = map[i][j];
        }
        String path = "";
        for(Character ch : list){
            path = path + ch + " ";
        }
        return path;
    }
 
    // adding all neighbours to queue if they are not walls or already checked.
    // all checked are being marked as walls
    private void addNeighboursToQueue(Node node){
        int N = map.length-1;
        int x = node.X;
        int y = node.Y;
 
        if(x < N)
        {
            if(map[x+1][y] == ' ' || map[x+1][y] == 'G')
            {
                queue.add(new Node(map[x+1][y],x+1, y));
                map[x+1][y] = 'S';
            }
        }
        if(x > 0)
        {
            if(map[x-1][y] == ' ' || map[x-1][y] == 'G')
            {
                queue.add(new Node(map[x-1][y], x-1, y));
                map[x-1][y] = 'N';
            }
        }
        if(y < N)
        {
            if(map[x][y+1] == ' ' || map[x][y+1] == 'G')
            {
                queue.add(new Node(map[x][y+1], x, y+1));
                map[x][y+1] = 'E';
            }
        }
        if(y > 0)
        {
            if(map[x][y-1] == ' ' || map[x][y-1] == 'G')
            {
                queue.add(new Node(map[x][y-1], x, y-1));
                map[x][y-1] = 'W';
            }
        }
    }
 
 
    // ##################################################
    // # You do not need to modify anything below here. #
    // ##################################################
    public static void main(String[] args) throws IOException {
        new pacBack().run();
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