import java.util.ArrayList;

public class Main {
	
	//TODO Adding queueDT class implementation
	
	public static int lastPrint = 0;
	public static int expandedNodes = 0;
	
	public static void main(String [] args)
	{
		System.out.println(solve("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3", "DF", false));
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
				while(!problem.goalTest(goalNode.getCurrentState()) || goalNode == null) {
					goalNode = generalSearch(problem, QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT, depthLimit);
					depthLimit++;
				}
				break;
			case "UC":
				goalNode = generalSearch(problem, QueueingFunction.SORTED_INSERT);break;
				
//			case "GR1":
//				generalSearch(problem, "enqueueAtEnd");break; //TODO 
//				
//			case "GR2":
//				generalSearch(problem, "enqueueAtEnd");break;
//			
//			case "A*1":
//				generalSearch(problem, "enqueueAtEnd");break;
//			
//			case "A*2":
//				generalSearch(problem, "enqueueAtEnd");break;
//			
		}
		if(goalNode == null)
			return "There is no solution";
		else {
			//TODO return sequence of operators leading to this goal node
			Node currentNode = goalNode;
			String operators = "";
			String states = "";
			String pathCost = "Path Cost: "+goalNode.getPathCost();
			while(currentNode != null) {
				operators = ","+currentNode.getOperator() + operators;
				states = currentNode.getCurrentState()+"\n"+states;
				currentNode = currentNode.getParentNode();
			}
			operators = "Solution:"+operators+"\n"+states+pathCost;
			return operators;
		}
	}
	public static Node generalSearch(Problem problem, QueueingFunction queueingFunction) {
		return generalSearch(problem, queueingFunction, 0);
	}
	
	public static Node generalSearch(Problem problem, QueueingFunction queueingFunction, int depthLimit) {
		
		QueueDT<Node> toBeExpandedNodes = new QueueDT<Node>();
		VisitedStateList visitedStates = new VisitedStateList();
		//TODO choosing between different queueing functions
		
		//enqueue initial state before starting the expanding process
		Node initialNode = new Node(problem.getInitialState(), null, '\0', 0, 0);
		System.out.println(initialNode);
		toBeExpandedNodes.add(initialNode);
		
		//add initial state to visitedStates
		visitedStates.AddInitialState(problem.getInitialState());
		
		Node currentNode = (Node) toBeExpandedNodes.poll();
	
		System.out.println(toBeExpandedNodes.isEmpty());
		while(currentNode != null && !problem.goalTest(currentNode.getCurrentState())) {
			if(currentNode.getDepth() < depthLimit || queueingFunction != QueueingFunction.ENQUEUE_AT_FRONT_WITH_LIMIT) {
				expand(toBeExpandedNodes, visitedStates,  currentNode, problem, queueingFunction);
//				System.out.println("Expanded!");
			}
			if(!toBeExpandedNodes.isEmpty())
				currentNode = (Node) toBeExpandedNodes.poll();
			else
				currentNode = null;
					
		}
		return currentNode;
		
	}
	
	public static void expand(QueueDT<Node> toBeExpandedQueueDT, VisitedStateList visitedStates, Node currentNode, Problem problem, QueueingFunction queueingFunction){
		
		
		//Expanding Current Node and getting its children
		ArrayList<Node> childrenNodes = new ArrayList<Node>();
		for(int i = 0;i < problem.getOperators().length(); i++) {
			char operator = problem.getOperators().charAt(i); //getting the current expanded operator
			State nextState = problem.transitionFunction(currentNode.getCurrentState(), operator);
			
			if(nextState != null && !visitedStates.isStateRepeated(nextState))
			{
				 //Creating the new node and adding it to the front of the queue
				// Changing the path cost function to take current node and current operator
//				System.out.println("Added Child " + i);
//				System.out.println(nextState);
				
//				expandedNodes++;
//				//Checking if this state is repeated
//				
//				if(expandedNodes/ 500 > lastPrint){
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
		//TODO ordered insertion based on each node path cost
		boolean inserted = false;
		for (Node nodeToBeInserted : childrenNodes) {
			inserted = false;
			int currentCost = nodeToBeInserted.getPathCost();
			for(int i = 0; i < toBeExpandedQueueDT.size(); i++)
			{
				Node queueNode = (Node) toBeExpandedQueueDT.getItem(i);
				if(queueNode.getPathCost() > currentCost)
				{
					toBeExpandedQueueDT.insertAt(i, nodeToBeInserted);
					inserted = true;
					break;
				}
			}
			// if the queue is empty or if nodeToBeInserted Cost is greater than all the queues' nodes
			if(!inserted)
				toBeExpandedQueueDT.add(nodeToBeInserted);
		}
		
	}
	
}


