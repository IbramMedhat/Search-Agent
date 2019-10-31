import java.util.ArrayList;

public class Main {
		
	public static int lastPrint = 0;
	public static int expandedNodes = 0;
	
	@SuppressWarnings("unused")
	public static void main(String [] args)
	{
//		String[] st = {"BF","DF","UC","ID","GR1","GR2","AS1","AS2"};
//		for (String string : st) {
//			System.out.println(solve("15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11,10,0", string, false));
//			System.out.println("");
//		}
		
		String projectExampleGrid = "5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		String grid5 = "5,5;2,2;4,2;4,0,1,2,3,0,2,1,4,1,2,4;3,2,0,0,3,4,4,3,4,4";
		String grid6 = "6,6;5,3;0,1;4,3,2,1,3,0,1,2,4,5,1,1;1,3,3,3,1,0,1,4,2,2";
		String grid7 = "7,7;5,4;0,1;5,0,5,6,3,1,4,3,1,2,6,3;2,5,2,6,1,0,5,5,6,5";
		String grid8 = "8,8;7,2;2,2;7,6,2,3,3,0,0,1,6,0,5,5;7,3,4,4,1,6,2,4,2,6";
		String grid9 = "9,9;2,5;3,3;6,2,5,1,3,0,2,8,8,3,0,5;5,4,5,5,1,6,6,3,4,8";
		String grid10 = "10,10;5,1;0,4;3,1,6,8,1,2,9,2,1,5,0,8;7,8,7,6,3,3,6,0,3,8";
		String grid11 = "11,11;9,5;7,1;9,0,8,8,9,1,8,4,2,3,9,10;2,0,0,10,6,3,10,6,6,2";
		String grid12 = "12,12;0,6;9,11;8,3,3,0,11,8,7,4,7,7,10,2;2,8,11,2,2,6,4,6,9,8";
		String grid13 = "13,13;4,2;2,4;6,1,1,10,8,4,9,2,2,8,9,4;6,4,3,4,3,11,1,12,1,9";
		String grid14 = "14,14;2,13;12,7;8,6,9,4,7,1,4,4,4,7,2,3;8,13,0,4,0,8,5,7,10,0";
		String grid15 = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";

		
		
		
		System.out.println(solve("15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11,10,0", "UC", false));
		
	}
	
	public static String solve(String grid, String strategy, boolean visualize) {
		
		Long startTime = System.currentTimeMillis();
		
		EndGameProblem problem = new EndGameProblem(grid);
		Node goalNode = null;
		switch(strategy) {
			case "BF":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_END);break;
			case "DF":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_FRONT);break;
			
			case "ID":
				// Iterative Deepening can not return null node as it is complete
				int depthLimit = 0;
				while(goalNode == null || !problem.goalTest(goalNode.getCurrentState())) {
					goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT, depthLimit);
					depthLimit++;
				}
				break;
			case "UC":
				goalNode = generalSearch(problem, QueueingFunction.SORTED_INSERT);break;
				
			case "GR1":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_GREEDY_HEURISTIC_ONE);break;
				
			case "GR2":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_GREEDY_HEURISTIC_TWO);break;
			
			case "AS1":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_A_HEURISTIC_ONE);break;
			
			case "AS2":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_A_HEURISTIC_TWO);break;
			
		}
		
		System.out.println("Execution Time of "+strategy+": "+((float)(System.currentTimeMillis() - startTime)) / 1000f+" seconds");
		if(goalNode == null)
			return "There is no solution";
		else {
			return ComputeOutput(goalNode);
		}
	}
	public static Node generalSearch(Problem problem, QueueingFunction queueingFunction) {
		return generalSearch(problem, queueingFunction, 0);
	}
	
	public static Node generalSearch(Problem problem, QueueingFunction queueingFunction, int depthLimit) {
		
		Queue<Node> toBeExpandedNodes = null;
		
		
		
		if(queueingFunction == QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT ||
				queueingFunction == QueueingFunction.ENQUEUE_AT_END ||
				queueingFunction == QueueingFunction.ENQUEUE_AT_FRONT) {
			toBeExpandedNodes = new NormalQueue<Node>();
		}
		else {			
			toBeExpandedNodes = new PriorityQueue<Node>();
		}
		
		//Initialzie VisitedStates
		problem.getVisitedStates().InitializeList();
		
		//enqueue initial state before starting the expanding process
		Node initialNode = new Node(problem.getInitialState(), null, '\0', 0, 0);
		
		Node currentNode = initialNode;
	
		while(currentNode != null && !problem.goalTest(currentNode.getCurrentState())) {
			if(currentNode.getDepth() < depthLimit || queueingFunction != QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT) {
				expand(toBeExpandedNodes, currentNode, problem, queueingFunction);
//				System.out.println("Expanded!");
			}
			if(!toBeExpandedNodes.isEmpty()) {
				currentNode = (Node) toBeExpandedNodes.removeFirst();
			}
			else
				currentNode = null;
					
		}
		return currentNode;
		
	}
	
	public static void expand(Queue<Node> toBeExpandedQueue, Node currentNode, Problem problem, QueueingFunction queueingFunction){
		
		
		//Expanding Current Node and getting its children
		ArrayList<Node> childrenNodes = new ArrayList<Node>();
		for(int i = 0;i < problem.getOperators().length(); i++) {
			char operator = problem.getOperators().charAt(i); //getting the current expanded operator
			State nextState = problem.transitionFunction(currentNode.getCurrentState(), operator);
			
			if(nextState != null)
			{
				 //Creating the new node and adding it to the front of the queue
				// Changing the path cost function to take current node and current operator
//				System.out.println("Added Child " + i);
				
		
//				System.out.println(nextState);
				
				expandedNodes++;
				//Checking if this state is repeated
				
//				if(expandedNodes/ 5000 > lastPrint){
//					
//					System.out.println(expandedNodes);
//					lastPrint++;
//				}
				
				
				childrenNodes.add(new Node(nextState, 
												currentNode, 
												operator,
												currentNode.getDepth() + 1,
												problem.pathCost(currentNode, nextState, operator)));
			}
		
		}
		
		switch (queueingFunction) {
		case ENQUEUE_AT_END:
			enqueueAtEnd((NormalQueue<Node>)toBeExpandedQueue, childrenNodes);
			break;
		case ENQUEUE_AT_FRONT_WITH_LIMIT:
		case ENQUEUE_AT_FRONT:
			enqueueAtFront((NormalQueue<Node>)toBeExpandedQueue, childrenNodes);
			break;
		case SORTED_INSERT:
			orderedInsert((PriorityQueue<Node>)toBeExpandedQueue, childrenNodes);
			break;	
		case ENQUEUE_GREEDY_HEURISTIC_ONE:
			orderedInsertGreedyOne((PriorityQueue<Node>)toBeExpandedQueue, childrenNodes, problem);
			break;
		case ENQUEUE_GREEDY_HEURISTIC_TWO:
			orderedInsertGreedyTwo((PriorityQueue<Node>)toBeExpandedQueue, childrenNodes, problem);
			break;
		case ENQUEUE_A_HEURISTIC_ONE:
			orderedInsertAOne((PriorityQueue<Node>)toBeExpandedQueue, childrenNodes, problem);
			break;
		case ENQUEUE_A_HEURISTIC_TWO:
			orderedInsertATwo((PriorityQueue<Node>)toBeExpandedQueue, childrenNodes, problem);
			break;
		default:
			break;	
		}
	
		
	}

	public static void enqueueAtFront(NormalQueue<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node node : childrenNodes) {
			toBeExpandedQueueDT.addFirst(node);
		}
	}
	
	public static void enqueueAtEnd(NormalQueue<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node node : childrenNodes) {
			toBeExpandedQueueDT.addLast(node);
		}
	}
	
	public static void orderedInsert(PriorityQueue<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node nodeToBeInserted : childrenNodes) {
			toBeExpandedQueueDT.insertAt(nodeToBeInserted.getPathCost(), nodeToBeInserted);
		}
	}
	
	public static void orderedInsertGreedyOne(PriorityQueue<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(1, nodeToBeInserted);
			toBeExpandedQueueDT.insertAt(heuristicValue, nodeToBeInserted);
		}
	}
	
	public static void orderedInsertGreedyTwo(PriorityQueue<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(2, nodeToBeInserted);
			toBeExpandedQueueDT.insertAt(heuristicValue, nodeToBeInserted);
		}
	}
	
	public static void orderedInsertAOne(PriorityQueue<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(1, nodeToBeInserted);
			//System.out.println(heuristicValue);
			toBeExpandedQueueDT.insertAt(heuristicValue + nodeToBeInserted.getPathCost(), nodeToBeInserted);
		}
	}
	
	public static void orderedInsertATwo(PriorityQueue<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(2, nodeToBeInserted);
			//System.out.println(heuristicValue);
			toBeExpandedQueueDT.insertAt(heuristicValue + nodeToBeInserted.getPathCost(), nodeToBeInserted);
		}
	}
			
//	public static void orderedInsert(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
//		boolean inserted = false;
//		for (Node nodeToBeInserted : childrenNodes) {
//			inserted = false;
//			int currentCost = nodeToBeInserted.getPathCost();
//			
//			int x = currentCost + 1;
//	        int l = 0, r = toBeExpandedQueueDT.size() - 1;
//	        int finalM = -1;
//	        while (l <= r) { 
//	            int m = l + (r - l) / 2; 
//	           
//	            // Check if x is present at mid 
//	            if (((Node)toBeExpandedQueueDT.getItem(m)).getPathCost() == x) {	            	
//	            	if(m-1 > 0)
//	            	{
//	            		toBeExpandedQueueDT.insertAt(m-1, nodeToBeInserted);
//	            		inserted = true;
//	            	}
//	            	break;
//	            }
//	 
//	            // If x greater, ignore left half 
//	            if ( ((Node)toBeExpandedQueueDT.getItem(m)).getPathCost()  < x)
//	                l = m + 1; 
//	 
//	            // If x is smaller, ignore right half 
//	            else
//	                r = m - 1; 
//	            
//	            finalM = m;
//	        } 
////			for(int i = 0; i < toBeExpandedQueueDT.size(); i++)
////			{
////				Node queueNode = (Node) toBeExpandedQueueDT.getItem(i);
////				if(queueNode.getPathCost() > currentCost)
////				{
////					toBeExpandedQueueDT.insertAt(i, nodeToBeInserted);
////					inserted = true;
////					break;
////				}
////			}
//			// if the queue is empty or if nodeToBeInserted Cost is greater than all the queues' nodes
//			if(!inserted)
//			{
//				if(finalM == -1)
//					toBeExpandedQueueDT.add(nodeToBeInserted);
//				else
//					toBeExpandedQueueDT.insertAt(finalM, nodeToBeInserted);
//			}
//		}
//		
//	}
	
	public static String ComputeOutput(Node goalNode)
	{
		Node currentNode = goalNode;
		String operators = "";
		String states = "";
		int pathCost = goalNode.getPathCost();
		while(currentNode != null) {
			String operator = "";
			switch (currentNode.getOperator()) {
			case 'U':
				operator = "up";
				break;
			case 'D':
				operator = "down";
				break;
			case 'R':
				operator = "right";
				break;
			case 'L':
				operator = "left";
				break;
			case 'K':
				operator = "kill";
				break;
			case 'C':
				operator = "collect";
				break;
			case 'S':
				operator = "snap";
				break;
				
			default:
				break;
			}
			if(currentNode.getParentNode() == null || currentNode.getParentNode().getParentNode() == null) {
				operators = operator+operators;
			}
			else {
				operators = ","+operator+operators;
			}
			states = currentNode.getCurrentState()+"\n"+states;
			currentNode = currentNode.getParentNode();
		}
		return operators+";"+pathCost+";"+expandedNodes;
	}
	
}


