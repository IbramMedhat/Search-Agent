import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class QueueDT<E> implements Queue{
	
	LinkedList<LinkedList<E>> sortedQueue;
	LinkedList<E> queue;
	int elementCount;
	int minimumIndex;
	private boolean sorted;
	
	public QueueDT()
	{
		this(false);
	}
	public QueueDT(boolean sorted) {
		this.sorted = sorted;
		if(sorted)
		{
			sortedQueue = new LinkedList<LinkedList<E>>();
			for(int i = 0; i < 250; i++)
			{
				sortedQueue.add(new LinkedList<E>());
			}
			elementCount = 0;
			minimumIndex = 0;
		}
		else
		{			
			this.queue = new LinkedList<E>();
		}
	}

	@Override
	public int size() {
		if(sorted)
			return elementCount;
		else
			return queue.size();
	}

	@Override
	public boolean isEmpty() {
		if(sorted)
			return elementCount == 0? true : false;
		else
			return queue.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return queue.contains(o);
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean add(Object e) {
		// TODO Auto-generated method stub
		if(sorted)
		{
			return this.insertAt(minimumIndex, (E)e);
		}
		else
		{			
			return queue.add((E)e);
		}
	}

	@Override
	public boolean offer(Object e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void addFirst(Object e) {
		queue.offerFirst((E)e);
	}

	@Override
	public Object remove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object poll() {
		if(sorted)
		{		
			Object e = sortedQueue.get(minimumIndex).removeFirst();
			elementCount--;
			if(sortedQueue.get(minimumIndex).isEmpty())
			{
				for(int i = minimumIndex+1; i < sortedQueue.size(); i++)
				{
					if(!sortedQueue.get(i).isEmpty())
					{
						minimumIndex = i;
						break;
					}
				}
			}
			return e;
		}
		else
		{			
			return queue.removeFirst();
		}
	}

	@Override
	public Object element() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object peek() {
		return queue.getFirst();
	}
	
	public boolean insertAt(int i ,E element)
	{
		if(sorted)
		{
			minimumIndex = i < minimumIndex? i : minimumIndex;
			elementCount++;
			return sortedQueue.get(i).add(element);
		}
		else
		{			
			queue.add(i, element);
			return true;
		}
	}
	
	public Object getItem(int i)
	{
		return queue.get(i);
	}

}
