package dataStructures.priorityQueue;

public class NonSortedLinkedPriorityQueue <T extends Comparable<? super T>> implements PriorityQueue<T>{
	// Debo especificar siempre el tipo genérico
	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void enqueue(T x) {

	}

	@Override
	public T first() {
		return null;
	}

	@Override
	public void dequeue() {

	}
}
