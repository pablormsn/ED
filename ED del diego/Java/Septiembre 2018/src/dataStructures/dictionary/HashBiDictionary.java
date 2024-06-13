package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.util.Iterator;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */
public class HashBiDictionary<K,V> implements BiDictionary<K,V>{
	private Dictionary<K,V> bKeys;
	private Dictionary<V,K> bValues;
	
	public HashBiDictionary() {
		// TODO
		// El diccionario de claves debería estar vacío
		bKeys = new HashDictionary<>();
		// Lo mismo para el de valores
		bValues = new HashDictionary<>();
	}
	
	public boolean isEmpty() {
		// TODO
		// Con que uno de ellos este vacío nos vale
		return bKeys.isEmpty();
	}
	
	public int size() {
		// TODO
		return bKeys == null ? 0: bKeys.size();
	}
	
	public void insert(K k, V v) {
		// TODO
		// Debemos insertar tanto en un diccionario como en otro
		bKeys.insert(k,v);
		bValues.insert(v,k);
	}
	
	public V valueOf(K k) {
		// TODO
		return bKeys.valueOf(k);
	}
	
	public K keyOf(V v) {
		// TODO
		return bValues.valueOf(v);
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {
		// TODO
		// Almaceno su valor
		V value = valueOf(k);
		// Elimino la asociación del diccionario de claves-valor
		bKeys.delete(k);
		// Ahora elimino la asociación del otro diccionario
		bValues.delete(value);
	}
	
	public void deleteByValue(V v) {
		// TODO
		// Igual que deleteByKey
		K key = keyOf(v);
		bValues.delete(v);
		bKeys.delete(key);
	}
	
	public Iterable<K> keys() {
		return bKeys.keys();
	}
	
	public Iterable<V> values() {
		return bValues.keys();
	}
	
	public Iterable<Tuple2<K, V>> keysValues() {
		return bKeys.keysValues();
	}
	

	private static <K,V extends Comparable<? super V>> boolean isOne2One(Dictionary<K,V> dictionary){
		// Creo un conjunto nuevo
		Set<V> values = new AVLSet<>();
		// Almaceno el tamaño de mi diccionario.
		int tam = dictionary.size();
		// Itero sobre los valores de mi diccionario y los añado al conjunto.
		for(V value: dictionary.values()){
			values.insert(value);
		}
		// Compruebo que el tamaño del conjunto y del diccionario son el mismo.
		return values.size() == tam;
	}
	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K,V> dict) {
		// TODO
		if(!isOne2One(dict)){
			throw new IllegalArgumentException("No es inyectivo el diccionario");
		}else{
			// Creo un nuevo diccionario.
			BiDictionary<K,V> dic = new HashBiDictionary<>();
			// Itero sobre el diccionario que me pasan
			for(Tuple2<K,V> edge: dict.keysValues()){
				dic.insert(edge._1(), edge._2());
			}
			// Una vez inicializado el diccionario lo devolvemos
			return dic;
		}
	}
	
	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		// TODO
		// Creo un nuevo diccionario
		BiDictionary<K,W> dic = new HashBiDictionary<>();
		// Iteramos sobre el diccionario
		for(Tuple2<V,W> kv: bdic.keysValues()){
			if(isDefinedValueAt(kv._1())){ // El valor está en el diccionario. Si no está nos da igual
				dic.insert(keyOf(kv._1()),kv._2());
			}
		}
		return dic;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		// TODO
		// Creamos nuestra variable booleana
		boolean ok = true;
		// Creamos dos conjuntos donde guardaremos las claves y los valores
		Set<K> ks = new AVLSet<>();
		Set<K> vs = new AVLSet<>();

		// Realizamos la inserción
		for(Tuple2<K,K> kv: bd.keysValues()){
			ks.insert(kv._1());
			vs.insert(kv._2());
		}

		// Ahora comprobamos que el conjunto de claves sea igual al de valores
		Iterator<K> it = ks.iterator();
		while(ok && it.hasNext()){
			if(!vs.isElem(it.next())){
				ok = false;
			}
		}
		return ok;
	}
	
	// Solo alumnos con evaluación por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		// TODO
		return null;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		// TODO
		return null;
	}

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}
