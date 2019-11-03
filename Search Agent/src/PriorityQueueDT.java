import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueDT<E> implements Queue<E>{
	
	PriorityQueue<Node> sortedQueue;

	public PriorityQueueDT()
	{
		Comparator<Node> nodeComparitor = new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				return o1.getQueuingCost() - o2.getQueuingCost();
			}
			
		};
		sortedQueue = new PriorityQueue<Node>(nodeComparitor);
		
	}
	
	public int size() {
		return sortedQueue.size();
	}
	
	public boolean isEmpty()
	{
		return sortedQueue.isEmpty();
	}
	
	public boolean add(Node e)
	{
		return sortedQueue.add((Node) e);
	}
	
	@Override
	public Object removeFirst() {
		
		return sortedQueue.remove();
	}

	@Override
	public Object peek() {
		return sortedQueue.peek();
	}

}
