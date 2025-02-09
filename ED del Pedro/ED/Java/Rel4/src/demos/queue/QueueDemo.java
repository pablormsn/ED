/**
 * @author Pepe Gallardo, Data Structures, Grado en Informática. UMA.
 *
 * Simple Queue demo
 */

package demos.queue;

import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.myLinkedPriorityQueue;
import dataStructures.queue.Queue;
import dataStructures.queue.ArrayQueue;
import dataStructures.queue.LinkedQueue;

public class QueueDemo {
	public static void main(String[] args) {
		PriorityQueue<Integer> q1 = new myLinkedPriorityQueue<>();
		q1.enqueue(3);
		q1.enqueue(1);
		q1.enqueue(2);
		int x = q1.first();
		q1.dequeue();
		
		Queue<Integer> q2 = new ArrayQueue<>();
		q2.enqueue(1);
		q2.enqueue(2);
		q2.enqueue(3);
		
		System.out.println(q1);
		System.out.println(q2);
	}
}
