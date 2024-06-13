/**
 * Estructuras de Datos. 2º Curso. ETSI Informática. UMA
 *
 * PRACTICA 5ª. Ejercicio 9 de la cuarta relación (montículos maxifóbicos en Java)
 *
 * (completa y sustituye los siguientes datos)
 * Titulación: Grado en Ingeniería …………………………………… [Informática | del Software | de Computadores].
 * Alumno: APELLIDOS, NOMBRE
 * Fecha de entrega:  DIA | MES | AÑO
 */

package dataStructures.heap;


/**
 * Heap implemented using maxiphobic heap-ordered binary trees.
 * @param <T> Type of elements in heap.
 */
public class MaxiphobicHeap<T extends Comparable<? super T>> implements	Heap<T> {

	private static class Tree<E> {
		private E elem;
		private int size;
		private Tree<E> left;
		private Tree<E> right;
	}

	private static int size(Tree<?> heap) {
		return heap == null ? 0 : heap.size;
	}

	private static <T> void obtenerHijo(Tree<T> winner, Tree<T> lhl, Tree<T> rhl){
		Tree<T> aux = null;
		if(lhl!=null && rhl!=null){
			if(winner.size < lhl.size || winner.size < rhl.size){
				aux = winner;
				if(lhl.size > rhl.size){
					winner = lhl;
					lhl = aux;
				}else{
					winner = rhl;
					rhl = aux;
				}

			}

		}
	}

	private static <T extends Comparable<? super T>> Tree<T> merge(Tree<T> h1,	Tree<T> h2) {
		if (h1 == null)
			return h2;
		if (h2 == null)
			return h1;

		Tree<T> hm = new Tree<T>();//Montículo maxifóbico, vacío
		Tree<T> winner;//Arbol ganador
		Tree<T> lhl;//Arbol izquierdo
		Tree<T> rhl;//Arbol derecho

		if(h1.elem.compareTo(h2.elem)<0){ //si la raíz del primer árbol es menor que la del segundo
			hm.elem = h1.elem; //la raíz del montículo maxifóbico es la raíz del primer árbol
			winner = h2; //el árbol ganador es el segundo
			lhl = h1.left; //el árbol izquierdo es el izquierdo del primer árbol
			rhl = h1.right; //el árbol derecho es el derecho del primer árbol
		}else{
			hm.elem = h2.elem; //la raíz del montículo maxifóbico es la raíz del segundo árbol
			winner = h1; //el árbol ganador es el primero
			lhl = h2.left; //el árbol izquierdo es el izquierdo del segundo árbol
			rhl = h2.right; //el árbol derecho es el derecho del segundo árbol
		}

		if(winner.size >= size(lhl) && winner.size >= size(rhl)){ //si el tamaño del árbol ganador es mayor o igual que el tamaño del árbol izquierdo y el tamaño del árbol ganador es mayor o igual que el tamaño del árbol derecho
			hm.left = winner; //el árbol izquierdo del montículo maxifóbico es el árbol ganador
			hm.right = merge(lhl, rhl); //el árbol derecho del montículo maxifóbico es la fusión del árbol izquierdo y el árbol derecho
		}else if(size(lhl) >= winner.size && size(lhl) >= size(rhl)){ //si el tamaño del árbol izquierdo es mayor o igual que el tamaño del árbol ganador y el tamaño del árbol izquierdo es mayor o igual que el tamaño del árbol derecho
			hm.left = lhl; //el árbol izquierdo del montículo maxifóbico es el árbol izquierdo
			hm.right = merge(winner, rhl); //el árbol derecho del montículo maxifóbico es la fusión del árbol ganador y el árbol derecho
		}else{ //si el tamaño del árbol derecho es mayor o igual que el tamaño del árbol ganador y el tamaño del árbol derecho es mayor o igual que el tamaño del árbol izquierdo
			hm.left = rhl; //el árbol izquierdo del montículo maxifóbico es el árbol derecho
			hm.right = merge(winner, lhl); //el árbol derecho del montículo maxifóbico es la fusión del árbol ganador y el árbol izquierdo
		}

		hm.size = 1+ size(hm.left) + size(hm.right); //el tamaño del montículo maxifóbico es 1 más el tamaño del árbol izquierdo más el tamaño del árbol derecho. 1 es por la raíz
		return hm;
	}

	private Tree<T> root;

	// copies a tree
	private static <T extends Comparable<? super T>> Tree<T> copy(Tree<T> h) {
		if (h==null)
			return null;
		else {
			Tree<T> h1 = new Tree<>();
			h1.elem = h.elem;
			h1.size = h.size;
			h1.left = copy(h.left);
			h1.right = copy(h.right);			
			return h1;		
		}
	}

	/**
	 * Creates an empty Maxiphobic Heap.
	 * <p>Time complexity: O(1)
	 */
	public MaxiphobicHeap() {
		root = null;
	}

	/**
	 * Copy constructor for Maxiphobic Heap. 
	 * <p>Time complexity: O(n)
	 */	
	public MaxiphobicHeap(MaxiphobicHeap<T> h) {
		root = copy(h.root);
	}
	

	/**
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 */
	public int size() {
		return root == null ? 0 : root.size;
	}

	/**
	 * {@inheritDoc}
	 * <p>Time complexity: O(1)
	 * @throws <code>EmptyHeapException</code> if heap stores no element.
	 */
	public T minElem() {
		if (isEmpty())
			throw new EmptyHeapException("minElem on empty heap");
		else
			return root.elem;
	}

	/**
	 * {@inheritDoc}
	 * <p>Time complexity: O(log n)
	 * @throws <code>EmptyHeapException</code> if heap stores no element.
	 */
	public void delMin() {
		if (isEmpty())
			throw new EmptyHeapException("delMin on empty heap");
		else
			root = merge(root.left, root.right);
	}

	/**
	 * {@inheritDoc}
	 * <p>Time complexity: O(log n)
	 */
	public void insert(T value) {
		Tree<T> newHeap = new Tree<T>();
		newHeap.elem = value;
		newHeap.size = 1;
		newHeap.left = null;
		newHeap.right = null;

		root = merge(root, newHeap);
	}

	public void clear() {
		root = null;
	}

	private static String toStringRec(Tree<?> tree) {
		return tree == null ? "null" : "Node<" + toStringRec(tree.left) + ","
				+ tree.elem + "," + toStringRec(tree.right) + ">";
	}

	/**
	 * Returns representation of heap as a String.
	 */
  @Override public String toString() {
    String className = getClass().getName().substring(getClass().getPackage().getName().length()+1);

  	return className+"("+toStringRec(this.root)+")";
  }

}
