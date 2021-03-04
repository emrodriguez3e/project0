import java.awt.Dimension;
import java.util.*;
import java.util.Queue;

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
	Queue<Integer> q = new LinkedList<Integer>();

	//main method start
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
//		System.out.println("Enter the width:");
//		double width = scan.nextDouble();
		double width = 50;
//		System.out.println("Enter the height:");
//		double height = scan.nextDouble();//grab height
		double height = 50;
//		System.out.println("Enter the number of nodes:");
//		int numberOfNodes = scan.nextInt();//input nodes
		int numberOfNodes = 50;
//		System.out.println("Enter the Transmission range in meters:");
//		int transmissionRange = scan.nextInt();
		int transmissionRange = 15;
//		System.out.println("Please enter a traversal technique");
//		System.out.println("0--recursiveDFS\n1--StackDFS\n2--QueueBFS");
//		int input = scan.nextInt();

		scan.close();//end input scan

		SensorNetwork sensor = new SensorNetwork(); //create self object
		sensor.populateNodes(numberOfNodes, width, height); //call populate nodes

		System.out.println("\nNode List:");
		//Access axis member
		for(int key :sensor.nodes.keySet()) {
			Axis ax = sensor.nodes.get(key);
			System.out.println("Node:" + key + ", xAxis:" + ax.getxAxis() + ", yAxis:" + ax.getyAxis());
		}//for loop that is set ax to node key

		//create an adjacency list
		Map<Integer, Set<Integer>> adjacencyList = new LinkedHashMap<Integer, Set<Integer>> (); //create map


		sensor.populateAdjacencyList(numberOfNodes, transmissionRange, adjacencyList);
		System.out.println("\nAdjacency List: ");

		for(int i: adjacencyList.keySet()) {
			System.out.print(i);
			if(!adjacencyList.isEmpty()){
				for(int j: adjacencyList.get(i)) {
					System.out.print("->" + j);
				}
			}
			System.out.println();
		}

		sensor.executeDepthFirstSearchAlg(width, height, adjacencyList);
	}// end of main method



	//BFS algorithm for the queue
	void executeBreadthFirstSearchAlg(double width, double height, Map<Integer, Set<Integer>> adjList){
		System.out.println("\nExecuting BFS Algorithm");
		List<Set<Integer>> connectedNodes = new ArrayList<Set<Integer>>();

		for(int node: adjList.keySet()) {
			Set<Integer> connectedNode = new LinkedHashSet<Integer>();
//			Queue connectedNode2 = new LinkedList();
			queueBFS(node, connectedNode,adjList);

			if(!connectedNode.isEmpty()) {
				connectedNodes.add(connectedNode); // error; seeking Set. See executeDepthFirstSearchAlg
			}
		}//end of for loop


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
//			recursiveDFS(node, connectedNode, adjList); // call recursiveDFS

			if(!connectedNode.isEmpty()) {
				connectedNodes.add(connectedNode);
			}
		}//end of for loop

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

//		drawGraph(width, height, adjList);

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

	//
	void queueBFS(int numberOfNodes, Set<Integer> connectedNode, Map<Integer, Set<Integer>> adjList){

		//I need to add the root?

		System.out.println(adjList.values());//this returns a collection view
		Collection tmp = adjList.values();
		System.out.println();
		System.out.println(tmp.iterator());

		System.exit(0);

	}

	void stackDFS(int u, Set<Integer> connectedNode, Map<Integer, Set<Integer>> adjList){

		//s is stack
		/*
		* Initialize S to be a stack with one element s
		* Whatever the fuck s is supposed to be within S
		* */
		s.add(u);

		while(!s.isEmpty()){
			//take node u from S

			if(!explored.containsKey(u)) {
				explored.put(u, true);

				//for each edge (u,v) to u

				//add v to stack S
				//add adjList.value() to stack?

			}//end of the if loop
		}//end of while loop

//		drawGraph(double width, double height, Map<Integer, Set<Integer>> adjList);
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

		//for every value got
		for(int node1: nodes.keySet()) {
			Axis axis1 = nodes.get(node1); //make the axis object to node1
			for(int node2: nodes.keySet()) {
				Axis axis2 = nodes.get(node2);//make axis object to node2

				if(node1 == node2) {
					continue; //skip rest of loop
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
