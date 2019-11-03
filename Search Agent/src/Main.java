import java.util.ArrayList;

public class Main {
		
	public static int lastPrint = 0;
	public static int expandedNodes = 0;
	
	@SuppressWarnings("unused")
	public static void main(String [] args)
	{
		
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

		String[] st = {"BF","DF","UC","ID","GR1","GR2","AS1","AS2"};
		for (String string : st) {
			System.out.println(solve(projectExampleGrid, string, false));
		System.out.println("");
		
		}
//		System.out.println(solve(projectExampleGrid, "AS1", false));
		
		
	}
	
	public static String solve(String grid, String strategy, boolean visualize) {
		
		Long startTime = System.currentTimeMillis();
		
		EndGameProblem problem = new EndGameProblem(grid);
		Node goalNode = null;
		// here different queueing functions are passed to the general search according to the search strategy
		switch(strategy) {
			case "BF":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_END);break;
			case "DF":
				goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_FRONT);break;
			
			case "ID":
				// Iterative Deepening can not return null node as it is complete
				// Starting with depth 0
				int depthLimit = 0;
				// keep iterating unitl finding a goaltest satisfying node or null node indicating no solution
				while(goalNode == null || !problem.goalTest(goalNode.getCurrentState())) {
					// Applying depthLimited search using the general search with - enqueue at front with limit - queueing function
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
			// If the goal node is null that means that nodes can't be expanded anymore and no goalstate had been reached
			return "There is no solution";
		else {
			// printing the needed operators leading to goal state; the path cost; the number of expanded nodes
			return ComputeOutput(goalNode, problem, visualize);
		}
	}
	public static Node generalSearch(Problem problem, QueueingFunction queueingFunction) {
		// If the algorithm doesn't include a depth limit it is entered as 0 and not used
		return generalSearch(problem, queueingFunction, 0);
	}
	
	public static Node generalSearch(Problem problem, QueueingFunction queueingFunction, int depthLimit) {
		
		// Initialization of - the expanded nodes and not checked yet - queue
		Queue<Node> toBeExpandedNodes = null;
		
		
		// If the strategy doesn't include ordered insertion we use normalQueue structure 
		if(queueingFunction == QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT ||
				queueingFunction == QueueingFunction.ENQUEUE_AT_END ||
				queueingFunction == QueueingFunction.ENQUEUE_AT_FRONT) {
			toBeExpandedNodes = new NormalQueue<Node>();
		}
		// If the insertion done in order according to certain criteria priority queue is used
		else {			
			toBeExpandedNodes = new PriorityQueueDT<Node>();
		}
		
		//Initialization of VisitedStates
		problem.getVisitedStates().InitializeList();
		
		//enqueue initial state before starting the expanding process
		Node initialNode = new Node(problem.getInitialState(), null, '\0', 0, 0);
		
		Node currentNode = initialNode;
		
		// Checking if the node is not goal state node we continue checking and expanding
		while(currentNode != null && !problem.goalTest(currentNode.getCurrentState())) {
			// if iterative deepening we check on the depth limit
			if(currentNode.getDepth() < depthLimit || queueingFunction != QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT) {
				expand(toBeExpandedNodes, currentNode, problem, queueingFunction);
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
			
			// Repeated states are handled in the transition function
			State nextState = problem.transitionFunction(currentNode.getCurrentState(), operator);
			
			if(nextState != null)
			{
				// Adding the expanded nodes according to the transition function and path cost function
				expandedNodes++;	
				childrenNodes.add(new Node(nextState, 
												currentNode, 
												operator,
												currentNode.getDepth() + 1,
												problem.pathCost(currentNode, nextState, operator)));
			}
		
		}
		
		// Deciding on how the expanded nodes enter the queue according to different queueing functions
		switch (queueingFunction) {
		case ENQUEUE_AT_END:
			enqueueAtEnd((NormalQueue<Node>)toBeExpandedQueue, childrenNodes);
			break;
		case ENQUEUE_AT_FRONT_WITH_LIMIT:
		case ENQUEUE_AT_FRONT:
			enqueueAtFront((NormalQueue<Node>)toBeExpandedQueue, childrenNodes);
			break;
		case SORTED_INSERT:
			orderedInsert((PriorityQueueDT<Node>)toBeExpandedQueue, childrenNodes);
			break;	
		case ENQUEUE_GREEDY_HEURISTIC_ONE:
			orderedInsertGreedyOne((PriorityQueueDT<Node>)toBeExpandedQueue, childrenNodes, problem);
			break;
		case ENQUEUE_GREEDY_HEURISTIC_TWO:
			orderedInsertGreedyTwo((PriorityQueueDT<Node>)toBeExpandedQueue, childrenNodes, problem);
			break;
		case ENQUEUE_A_HEURISTIC_ONE:
			orderedInsertAOne((PriorityQueueDT<Node>)toBeExpandedQueue, childrenNodes, problem);
			break;
		case ENQUEUE_A_HEURISTIC_TWO:
			orderedInsertATwo((PriorityQueueDT<Node>)toBeExpandedQueue, childrenNodes, problem);
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
	
	public static void orderedInsert(PriorityQueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes) {
		for (Node nodeToBeInserted : childrenNodes) {
			toBeExpandedQueueDT.insertAt(nodeToBeInserted.getPathCost(), nodeToBeInserted);
		}
	}
	
	public static void orderedInsertGreedyOne(PriorityQueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(1, nodeToBeInserted);
			toBeExpandedQueueDT.insertAt(heuristicValue, nodeToBeInserted);
		}
	}
	
	public static void orderedInsertGreedyTwo(PriorityQueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(2, nodeToBeInserted);
			toBeExpandedQueueDT.insertAt(heuristicValue, nodeToBeInserted);
		}
	}
	
	public static void orderedInsertAOne(PriorityQueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(1, nodeToBeInserted);
			//System.out.println(heuristicValue);
			toBeExpandedQueueDT.insertAt(heuristicValue + nodeToBeInserted.getPathCost(), nodeToBeInserted);
		}
	}
	
	public static void orderedInsertATwo(PriorityQueueDT<Node> toBeExpandedQueueDT, ArrayList<Node> childrenNodes, Problem problem) {
		for (Node nodeToBeInserted : childrenNodes) {
			int heuristicValue = problem.calculateHeuristic(2, nodeToBeInserted);
			//System.out.println(heuristicValue);
			toBeExpandedQueueDT.insertAt(heuristicValue + nodeToBeInserted.getPathCost(), nodeToBeInserted);
		}
	}
			
	// Computing the cost of goal node and the operators leading to it	
	public static String ComputeOutput(Node goalNode, Problem problem, boolean visualize)
	{
		Node currentNode = goalNode;
		String operators = "";
		String states = "";
		String visualization = "";
		
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
			if(visualize)
			{
				visualization = "Operator: "+operator+"\n"+problem.Visualize(currentNode.getCurrentState())+"\n \n" + visualization;
			}
			states = currentNode.getCurrentState()+"\n"+states;
			currentNode = currentNode.getParentNode();
		}
		return operators+";"+pathCost+";"+expandedNodes+"\n"+visualization;
	}
	
}


