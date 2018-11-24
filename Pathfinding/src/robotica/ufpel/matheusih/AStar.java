package robotica.ufpel.matheusih;

import java.util.ArrayList;

import robotica.ufpel.feldmann.Collections;

public class AStar {
	//World Grid Size MxN, standard 8x8
	public static int M = 8;
	public static int N = 8;
	
	public static Node goal = new Node(7,7);
	
	
	public static int Manhattan(int x1, int y1, int x2, int y2) {
		return Math.abs(x2 - x1) + Math.abs(y2 - y1);
	}
	public static int Manhattan(Node target, Node pos) {
		return Math.abs(target.x - pos.x) + Math.abs(target.y - pos.y);
	}
	public static Node getSmallestF(ArrayList<Node> openlist) {
		int min = 99999;
		Node target = null;
		for(Node node : openlist) {
			if(node.F < min) {
				min = node.F;
				target = node;
			}
		}
		
		for(Node node : openlist) {
			if(node.F == target.F) 
				if(node.H < target.H)
					target = node;
		}
		return target;
	}
	public static Node getSmallestBF(ArrayList<Node> openlist) {
		int min = 99999;
		Node target = null;
		for(Node node : openlist) {
			if(node.BF < min) {
				min = node.F;
				target = node;
			}
		}
		
		for(Node node : openlist) {
			if(node.BF == target.BF) 
				if(node.BH < target.BH)
					target = node;
		}
		return target;
	}
	
	public static ArrayList<Node> AStarBacktrack(ArrayList<Node> Graph, Node start, Node goal){
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("I will backtrack from ("+start.x+","+start.y+")" + " to ("+goal.x+","+goal.y+")");
		ArrayList<Node> shortestPath = new ArrayList<Node>();
		ArrayList<Node> OpenList = new ArrayList<Node>();
		ArrayList<Node> ClosedList = new ArrayList<Node>();
		
		Node endNode = null;
		boolean goalReached = false;
		// put all nodes in unvisited list
		for(Node node : Graph){
			node.dist = 9999;
			node.DParent = null;
		}
		
		Graph.add(goal);
		
		// distance from start is zero
		start.dist = 0;
		//shortestPath.add(start);
		
		// sort openList so we get nearest neighbor
		
		Node current = start;
		
		do {  
        	ArrayList<Node> neighbors = getNeibsInList(Graph, current);
        	 ClosedList.add(current);
        	 if(OpenList.contains(current))
        		 OpenList.remove(current);
        	 
        	 
        	for(Node neighbor : neighbors) {
        		if (ClosedList.contains(neighbor))
        			continue;
        		else if( OpenList.contains(neighbor)) {
        			if(current.BG + Manhattan(neighbor,current) < neighbor.BG)
        				neighbor.DParent = current;
        		}
        		else {
        			OpenList.add(neighbor);
        			neighbor.DParent = current;
        			neighbor.BG = neighbor.DParent.BG + Manhattan(neighbor, current);
        			neighbor.BF = neighbor.BG + neighbor.BH;
        		}
        			
        		
        		if(neighbor.x == goal.x && goal.y == neighbor.y) {
        			goalReached = true;
        			endNode = neighbor;
        			endNode.DParent = current;
        		}
        			
        	}
        	
        	Node nextPosition = getSmallestBF(OpenList);
        	current = nextPosition;


        } while(goalReached == false || OpenList.isEmpty() == true);
        

		System.out.println("BACKTRACK ::::: ");
		Node iter = endNode;
        while(iter != null) {
        	shortestPath.add(iter);
        	System.out.println(iter.x + " " + iter.y);
        	iter = iter.DParent;
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
		return shortestPath;
	}
	
	
	public static ArrayList<Node> getNeibsInList(ArrayList<Node> list, Node Position){
		ArrayList<Node> neibs = new ArrayList<Node>();
		for(Node node : list)
		{
			if(Position.x + 1 == node.x && Position.y == node.y) neibs.add(node);
			else if(Position.x - 1 == node.x && Position.y == node.y) neibs.add(node);
			else if(Position.x == node.x && Position.y + 1 == node.y) neibs.add(node);
			else if(Position.x == node.x && Position.y - 1 == node.y) neibs.add(node);
		}
		return neibs;
	}
	
	
	public static void main(String[] args) {
		
		Node[][] Grid = new Node[8][8];
		
		/** Start world Grid  **/
		for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++) {
            	Grid[i][j] = new Node(i,j);
            	Grid[i][j].H = Manhattan(i,j,goal.x,goal.y);
            	Grid[i][j].BH = Manhattan(i,j,goal.x,goal.y);
            }
		
        /** Add Obstacles **/
		/*  test1
		Grid[0][5].blocked = true;
		Grid[5][0].blocked = true;
		Grid[1][4].blocked = true;
		Grid[4][1].blocked = true;
		Grid[3][2].blocked = true;
		Grid[4][3].blocked = true;
		Grid[3][5].blocked = true;*/
		/*obstacles test 2*/
		for(int i =7;i>0;--i) {
			Grid[i][6].blocked = true;
		}
		Grid[0][1].blocked = true;
		Grid[1][1].blocked = true;
		Grid[2][1].blocked = true;
		Grid[3][1].blocked = true;
		
        ArrayList<Node> OpenList = new ArrayList<Node>();  // Unchecked Nodes
        ArrayList<Node> ClosedList = new ArrayList<Node>(); //  Checked Nodes
        ArrayList<Node> ObstaclesList = new ArrayList<Node>(); //  Checked Nodes
        
        boolean goalReached = false;
        Node start = Grid[0][0];
        Node Position = start;  // My current Position	
        
        Node endNode = null;
        do {  // Main Loop
        	
        	ArrayList<Node> neighbors = Position.getNeighbors(Grid);
        	System.out.println("I am at (" + Position.x +"," + Position.y + ")");
        	 ClosedList.add(Position);
        	 if(OpenList.contains(Position))
        		 OpenList.remove(Position);
        	 
        	 
        	for(Node neighbor : neighbors) {
        		if (ClosedList.contains(neighbor) || ObstaclesList.contains(neighbor))
        			continue;
        		else if(neighbor.blocked)
        			ObstaclesList.add(neighbor);
        		else if( OpenList.contains(neighbor)) {
        			if(Position.G + Manhattan(neighbor,Position) < neighbor.G)
        				neighbor.Parent = Position;
        		}
        		else {
        			OpenList.add(neighbor);
        			neighbor.Parent = Position;
        			neighbor.G = neighbor.Parent.G + Manhattan(neighbor, Position);
        			neighbor.F = neighbor.G + neighbor.H;
        		}
        			
        		
        		if(neighbor.x == goal.x && goal.y == neighbor.y) {
        			goalReached = true;
        			endNode = neighbor;
        			endNode.Parent = Position;
        		}
        			
        	}
        	
        	Node nextPosition = getSmallestF(OpenList);
        	
        	if(Position.isNeib(nextPosition))
        	{
        		// ROBO ANDA AT� NEXT POSITION
        		
        		/*************************
        		 * ************************
        		 *  Implementar aqui ******
        		 * ***********************
        		 * ***********************
        		 **/
        		Position = nextPosition;
        	}
        	else
        	{
        		// BACKTRACKING !!
        		ArrayList<Node> backtrackList = AStarBacktrack(ClosedList, Position, nextPosition);
        		Collections.reverse(backtrackList);
        		
        		// Robo percorre backtrackList at� nextPosition
        		
        		/******************************
        		 * *************************
        		 *  Implementar aqui ******
        		 * *************************
        		 * ***********************
        		 ****************************/
        		Position = nextPosition;
        	}
        	
        	
        	
        } while(goalReached == false || OpenList.isEmpty() == true);
        
        System.out.println("Goal reached: ("+endNode.x+","+endNode.y+")");
        System.out.println("Printing Shortest Path::::");
        Node iter = endNode;
        while(iter != null) {
        	System.out.println(iter.x + " " + iter.y);
        	iter = iter.Parent;
        }
	}
	
	
	

}
