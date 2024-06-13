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
		bKeys = new HashDictionary<>();
		bValues = new HashDictionary<>();
	}
	
	public boolean isEmpty() {
		// TODO
		return bKeys.isEmpty();
	}
	
	public int size() {
		// TODO
		return bKeys==null?0: bKeys.size();
	}
	
	public void insert(K k, V v) {
		// TODO
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
		V value = valueOf(k);
		bKeys.delete(k);
		bValues.delete(value);
	}
	
	public void deleteByValue(V v) {
		// TODO
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

	private static <K,V extends Comparable<? super V>> boolean esInyectivo(Dictionary<K,V> dict){
		Set<V> values = new AVLSet<>();
		int tam = dict.size();
		for(V value : dict.values()){
			values.insert(value);
		}
		return values.size()==tam;
	}

	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K,V> dict) {
		// TODO
		BiDictionary<K,V> bdic = new HashBiDictionary<>();
		if(!esInyectivo(dict)){
			throw new IllegalArgumentException("El diccionario no es inyectivo");
		}else{
			for(Tuple2<K,V> asoc : dict.keysValues()){
				bdic.insert(asoc._1(), asoc._2());
			}
		}
		return bdic;
	}


	
	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		// TODO
		BiDictionary<K,W> comp = new HashBiDictionary<>();
		for(Tuple2<V,W> kv : bdic.keysValues()){
			if(isDefinedValueAt(kv._1())){
				comp.insert(keyOf(kv._1()), kv._2());
			}
		}
		return comp;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		// TODO
		boolean ok = true;
		Set<K> dk = new AVLSet<>();
		Set<K> dv = new AVLSet<>();

		for(Tuple2<K,K> kv: bd.keysValues()){
			dk.insert(kv._1());
			dv.insert(kv._2());
		}

		Iterator<K> it = dk.iterator();
		while(ok && it.hasNext()){
			if(!dv.isElem(it.next())){
				ok=false;
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
