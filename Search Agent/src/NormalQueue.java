import java.util.LinkedList;

public class NormalQueue<E> implements Queue<E>{
	
	LinkedList<E> queue;
	
	public NormalQueue() {
		this.queue = new LinkedList<E>();
	}

	public int size() {
		return queue.size();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public void addLast(E e) {
	
		queue.addLast(e);
	}
	
	public void addFirst(E e)
	{
		queue.addFirst(e);
	}
	
	public Object removeFirst() {			
		return queue.removeFirst();
	}

	public Object peek() {
		return queue.getFirst();
	}

}
