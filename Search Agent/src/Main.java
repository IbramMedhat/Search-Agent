import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
	
	//TODO Adding queueDT class implementation
	
	public static int lastPrint = 0;
	public static int expandedNodes = 0;
	
	public static void main(String [] args)
	{
		System.out.println(solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3", "ID", false));
	}
	
	public static String solve(String grid, String strategy, boolean visualize) {
		
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
				generalSearch(problem, QueueingFunction.ENQUEUE_GREEDY_HEURISTIC_ONE);break; //TODO 
				
			case "GR2":
				generalSearch(problem, QueueingFunction.ENQUEUE_GREEDY_HEURISTIC_TWO);break;
			
			case "A*1":
				generalSearch(problem, QueueingFunction.ENQUEUE_A_HEURISTIC_ONE);break;
			
			case "A*2":
				generalSearch(problem, QueueingFunction.ENQUEUE_A_HEURISTIC_TWO);break;
			
		}
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
		
		QueueDT<Node> toBeExpandedNodes;
		
		if(queueingFunction == QueueingFunction.SORTED_INSERT)
			toBeExpandedNodes = new QueueDT<Node>(true);
		else
			toBeExpandedNodes = new QueueDT<Node>(false);
		
		//enqueue initial state before starting the expanding process
		Node initialNode = new Node(problem.getInitialState(), null, '\0', 0, 0);
		toBeExpandedNodes.add(initialNode);
		
		//Initialzie VisitedStates
		problem.getVisitedStates().InitializeList();
		
		Node currentNode = (Node) toBeExpandedNodes.poll();
	
		while(currentNode != null && !problem.goalTest(currentNode.getCurrentState())) {
			if(currentNode.getDepth() < depthLimit || queueingFunction != QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT) {
				expand(toBeExpandedNodes, currentNode, problem, queueingFunction);
//				System.out.println("Expanded!");
			}
			if(!toBeExpandedNodes.isEmpty())
				currentNode = (Node) toBeExpandedNodes.poll();
			else
				currentNode = null;
					
		}
		return currentNode;
		
	}
	
	public static void expand(QueueDT<Node> toBeExpandedQueueDT, Node currentNode, Problem problem, QueueingFunction queueingFunction){
		
		
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
				
//				if(expandedNodes/ 500 > lastPrint){
//					
//					System.out.println(expandedNodes);
//					lastPrint++;
//				}
//				
				
				childrenNodes.add(new Node(nextState, 
												currentNode, 
												operator,
												currentNode.getDepth() + 1,
												problem.pathCost(currentNode, nextState, operator)));
			}
		
		}
		
		switch (queueingFunction) {
		case ENQUEUE_AT_END:
//			System.out.println("children Nodes Size : " + childrenNodes.size());
			enqueueAtEnd(toBeExpandedQueueDT, childrenNodes);
			break;
		case ENQUEUE_AT_FRONT_WITH_LIMIT:
		case ENQUEUE_AT_FRONT:
			enqueueAtFront(toBeExpandedQueueDT, childrenNodes);
			break;
		case SORTED_INSERT:
			orderedInsert(toBeExpandedQueueDT, childrenNodes);
			break;	
		case ENQUEUE_GREEDY_HEURISTIC_ONE:
			orderedInsertGreedyOne(toBeExpandedQueueDT, childrenNodes);
		case ENQUEUE_GREEDY_HEURISTIC_TWO:
			orderedInsertGreedyTwo(toBeExpandedQueueDT, childrenNodes);
		case ENQUEUE_A_HEURISTIC_ONE:
			orderedInsertAOne(toBeExpandedQueueDT, childrenNodes);
		case ENQUEUE_A_HEURISTIC_TWO:
			orderedInsertATwo(toBeExpandedQueueDT, childrenNodes);
		default:
			break;	
		}
	
		
	}

	public static void enqueueAtFront(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node node : childrenNodes) {
			toBeExpandedQueueDT.addFirst(node);
			
		}
	}
	
	public static void enqueueAtEnd(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node node : childrenNodes) {
			toBeExpandedQueueDT.add(node);
//			System.out.println("Queue Size: "+toBeExpandedQueueDT.size());
		}
	}
	
	public static void orderedInsert(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
//		boolean inserted = false;
		for (Node nodeToBeInserted : childrenNodes) {
			toBeExpandedQueueDT.insertAt(nodeToBeInserted.getPathCost(), nodeToBeInserted);
		}
	}
	
	public static void orderedInsertGreedyOne(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		//TODO queueing function according to greedy first heuristic
	}
	
	public static void orderedInsertGreedyTwo(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		//TODO queueing function according to greedy second heuristic
	}
	
	public static void orderedInsertAOne(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		//TODO queueing function according to A* first heuristic
	}
	
	public static void orderedInsertATwo(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		//TODO queueing function according to A* first heuristic
	}
			
//	public static void orderedInsert(QueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
//		//TODO ordered insertion based on each node path cost
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


