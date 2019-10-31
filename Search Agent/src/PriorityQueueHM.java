import java.util.LinkedList;

public class PriorityQueueHM<E> implements Queue<E>{
	
	LinkedList<LinkedList<E>> sortedQueue;
	private int elementCount;
	private int minimumIndex;
	
	private int numberOfQueues;
	
	public PriorityQueueHM()
	{
		this(250);
	}
	
	public PriorityQueueHM(int numOfQueues)
	{
		this.numberOfQueues = numOfQueues;
		sortedQueue = new LinkedList<LinkedList<E>>();
		for(int i = 0; i < this.numberOfQueues; i++)
		{
			sortedQueue.add(new LinkedList<E>());
		}
		elementCount = 0;
		minimumIndex = 0;
	}
	
	public int size() {
		return elementCount;
	}
	
	public boolean isEmpty()
	{
		return elementCount <= 0? true : false;
	}
	
	public boolean insertAt(int i, E e)
	{
		if(sortedQueue.get(i).add(e))
		{
			minimumIndex = elementCount == 0 || i < minimumIndex? i : minimumIndex;
			elementCount++;
			return true;
		}
		return false;
	}
	
	public Object peek()
	{
		return sortedQueue.get(minimumIndex).peekFirst();
	}
	
	public Object removeFirst()
	{
		Object e = sortedQueue.get(minimumIndex).removeFirst();
		elementCount--;
		if(sortedQueue.get(minimumIndex).isEmpty())
		{
			for(int i = minimumIndex + 1; i < sortedQueue.size(); i++)
			{
				if(!sortedQueue.get(i).isEmpty())
				{
					minimumIndex = i;
					break;
				}
			}
			//to make sure minimumIndex is less Than sortedQueue size
			minimumIndex %= sortedQueue.size();
		}
		return e;
	}
}