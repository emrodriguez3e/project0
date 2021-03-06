import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/*
TODO Implement Queue BFS

TODO Implement Stack DFS

 */

public class SensorNetwork {

	//create objects
	private Map<Integer, Axis> nodes = new LinkedHashMap<Integer, Axis>();
	Map<Integer, Boolean> discovered = new HashMap<Integer, Boolean>();
	Map<Integer, Boolean> explored = new HashMap<Integer, Boolean>();
	Map<Integer, Integer> parent = new HashMap<Integer, Integer>();
	Map<Integer, Integer> connectedNodes = new HashMap<Integer, Integer>();
	Stack<Integer> s = new Stack<Integer>();

	//main method start
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the width:");
		double width = scan.nextDouble();

		System.out.println("Enter the height:");
		double height = scan.nextDouble();//grab height
		
		System.out.println("Enter the number of nodes:");
		int numberOfNodes = scan.nextInt();//input nodes
		
		System.out.println("Enter the Transmission range in meters:");
		int transmissionRange = scan.nextInt();
		scan.close();//end input scan
		
		SensorNetwork sensor = new SensorNetwork(); //create self object
		sensor.populateNodes(numberOfNodes, width, height); //call populate nodes

		System.out.println("\nNode List:");
		for(int key :sensor.nodes.keySet()) {
			Axis ax = sensor.nodes.get(key);
			System.out.println("Node:" + key + ", xAxis:" + ax.getxAxis() + ", yAxis:" + ax.getyAxis());	
		}//for loop that is set ax to node key

		//create an adjacency list
		Map<Integer, Set<Integer>> adjacencyList1 = new LinkedHashMap<Integer, Set<Integer>> (); //create map

		sensor.populateAdjacencyList(numberOfNodes, transmissionRange, adjacencyList1);
		System.out.println("\nAdjacency List: ");
			
		for(int i: adjacencyList1.keySet()) {
			System.out.print(i);
			if(!adjacencyList1.isEmpty()){
				for(int j: adjacencyList1.get(i)) { 
					System.out.print("->" + j);
				}
			}
			System.out.println();
		}
			
		sensor.executeDepthFirstSearchAlg(width, height, adjacencyList1);
	}// end of main method



	//BFS algorithm for the queue
	void executeBreadthFirstSearchAlg(double width, double height, Map<Integer, Set<Integer>> adjList){
		System.out.println("\nExecuting BFS Algorithm");
		List<Set<Integer>> connectedNodes = new ArrayList<Set<Integer>>();



		/*
		Guts of algorithm should go here


		*/


		if(connectedNodes.size() == 1) {
			System.out.println("Graph is fully connected with one connected component.");
		} else {
			System.out.println("Graph is not fully connected");
		}

		System.out.println("There are " + connectedNodes.size() + " connected components");
		for(Set<Integer> list: connectedNodes) {
			System.out.println(list);
		}

		drawGraph(width, height, adjList); //draw graph method

	}//end of BFS


	//dfs algorithm for recursion & stack
	void executeDepthFirstSearchAlg(double width, double height, Map<Integer, Set<Integer>> adjList) {
		System.out.println("\nExecuting DFS Algorithm");
		List<Set<Integer>> connectedNodes = new ArrayList<Set<Integer>>();

		//create a node for every adjList
		for(int node: adjList.keySet()) {
			Set<Integer> connectedNode = new LinkedHashSet<Integer>();
			recursiveDFS(node, connectedNode, adjList); // call recursiveDFS


			if(!connectedNode.isEmpty()) {
				connectedNodes.add(connectedNode);
			}
		}
		
		if(connectedNodes.size() == 1) {
			System.out.println("Graph is fully connected with one connected component.");
		} else {
			System.out.println("Graph is not fully connected");
		}
		
		System.out.println("There are " + connectedNodes.size() + " connected components");
		for(Set<Integer> list: connectedNodes) {
			System.out.println(list);
		}

		//Draw sensor network graph
//		SensorNetworkGraph graph = new SensorNetworkGraph();
//		graph.setGraphWidth(width);
//		graph.setGraphHeight(height);
//		graph.setNodes(nodes);
//		graph.setAdjList(adjList);
//		graph.setPreferredSize(new Dimension(960, 800));
//		Thread graphThread = new Thread(graph);
//		graphThread.start();
		drawGraph(width, height, adjList);

	}// end of depth first sorting algorithm


	//moved stuff into a call
	void drawGraph(double width, double height, Map<Integer, Set<Integer>> adjList){
		SensorNetworkGraph graph = new SensorNetworkGraph();
		graph.setGraphWidth(width);
		graph.setGraphHeight(height);
		graph.setNodes(nodes);
		graph.setAdjList(adjList);
		graph.setPreferredSize(new Dimension(960, 800));
		Thread graphThread = new Thread(graph);
		graphThread.start();
	}


	//recursion
	void recursiveDFS(int u, Set<Integer> connectedNode, Map<Integer, Set<Integer>> adjList) {
		
		if(!s.contains(u) && !explored.containsKey(u)) {
			s.add(u);
			discovered.put(u, true);
		} 

			while(!s.isEmpty()) {
				if(!explored.containsKey(u)) {
					List<Integer> list = new ArrayList<Integer>(adjList.get(u));
					for(int v: list) {
						
						if(!discovered.containsKey(v)) {
							s.add(v);
							discovered.put(v, true);
							
							if(parent.get(v) == null) {
								parent.put(v, u);
							}
							recursiveDFS(v, connectedNode, adjList);
						} else if(list.get(list.size()-1) == v) {
							if( parent.containsKey(u)) {
								explored.put(u, true);
								s.removeElement(u);
								
								connectedNode.add(u);
								recursiveDFS(parent.get(u), connectedNode, adjList);
							}
						}
					}
				if(!explored.containsKey(u))
					explored.put(u, true);
					s.removeElement(u);
					connectedNode.add(u);
				}
			}
			
	}

	void queueBFS(int u, Set<Integer> connectedNode, Map<Integer, Set<Integer>> adjList){

	}

	void stackDFS(int u, Set<Integer> connectedNode, Map<Integer, Set<Integer>> adjList){

	}


	//randomly generate location points for each node
	void populateNodes(int nodeCount, double width, double height) {
		Random random = new Random();
		
		for(int i = 1; i <= nodeCount; i++) {
			Axis axis = new Axis();
			int scale = (int) Math.pow(10, 1);
			double xAxis =(0 + random.nextDouble() * (width - 0));
			double yAxis = 0 + random.nextDouble() * (height - 0);
			
			xAxis = (double)Math.floor(xAxis * scale) / scale;
			yAxis = (double)Math.floor(yAxis * scale) / scale;
			
			axis.setxAxis(xAxis);
			axis.setyAxis(yAxis);
			
			nodes.put(i, axis);	
		}
	}





	void populateAdjacencyList(int nodeCount, int tr, Map<Integer, Set<Integer>> adjList) {

		//this counts the nodes
		for(int i=1; i<= nodeCount; i++) {
			adjList.put(i, new HashSet<Integer>());
		}
		
		for(int node1: nodes.keySet()) {
			Axis axis1 = nodes.get(node1);
			for(int node2: nodes.keySet()) {
				Axis axis2 = nodes.get(node2);
				
				if(node1 == node2) {
					continue;
				}
				double xAxis1 = axis1.getxAxis();
				double yAxis1 = axis1.getyAxis();
					
				double xAxis2 = axis2.getxAxis();
				double yAxis2 = axis2.getyAxis();
				
				double distance =  Math.sqrt(((xAxis1-xAxis2)*(xAxis1-xAxis2)) + ((yAxis1-yAxis2)*(yAxis1-yAxis2))); //get the distance between two nodes
				
				if(distance <= tr) {
					Set<Integer> tempList = adjList.get(node1);
					tempList.add(node2);
					adjList.put(node1, tempList);
						
					tempList = adjList.get(node2);
					tempList.add(node1);
					adjList.put(node2, tempList);
				}
			}
		}
	}//end of adjacencyList


}// end of class
