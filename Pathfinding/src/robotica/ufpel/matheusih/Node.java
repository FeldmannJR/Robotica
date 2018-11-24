package robotica.ufpel.matheusih;

import java.util.ArrayList;

public class Node{
	public static int STEP = 10; //StepCost (The G cost)
	Node Parent = null;
	int H, BH;
	int G, BG;
	int F, BF;
	int x,y;
	int dist = 9999;
	Node DParent = null;
	boolean blocked = false;
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public ArrayList<Node> getNeighbors(Node[][] Grid) {
		/** Returns 4-adjacency neighbors of node target  **/
		ArrayList<Node> neighbors = new ArrayList<Node>();
		int x = this.x;
		int y = this.y;
		if(x+1 < AStar.M) neighbors.add(Grid[x+1][y]);
		if(x-1 >= 0) neighbors.add(Grid[x-1][y]);
		if(y+1 < AStar.M) neighbors.add(Grid[x][y+1]);
		if(y-1 >= 0) neighbors.add(Grid[x][y-1]);
		
		return neighbors;
	}

	public boolean isNeib(Node target){
		if(this.x + 1 == target.x && this.y == target.y) return true;
		else if(this.x - 1 == target.x && this.y == target.y) return true;
		else if(this.x == target.x && this.y + 1 == target.y) return true;
		else if(this.x == target.x && this.y - 1 == target.y) return true;
		return false;
	}

}
