package dataStructures.huffman;
/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name:
 * Student's group:
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

import java.sql.SQLIntegrityConstraintViolationException;

public class Huffman {

    // Exercise 1  
    public static Dictionary<Character, Integer> weights(String s) {
    	// Creamos un diccionario vacio
       Dictionary<Character,Integer> dic = new AVLDictionary<>();
       // Recorremos la cadena
        for(char c : s.toCharArray()){
            // Mirar si esta dentro del dic o no
            // if(dic.isDefinedAt(c)){
                // dic.insert(c, dic.valueOf(c)+1);
            // }else{
                // dic.insert(c,1);
            // }
            Integer cont = dic.valueOf(c);
            if(cont == null){
                dic.insert(c, 1);
            }else{
                dic.insert(c,cont+1);
            }
        }
        return dic;
    }

    // Exercise 2.a 
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
    	// Creamos el diccionario con los pesos
        Dictionary<Character, Integer> dic = weights(s);
        // Creamos la cola donde vamos a meter los WLeafTree
        PriorityQueue<WLeafTree<Character>> pq = new BinaryHeapPriorityQueue<>();
        for(Tuple2<Character,Integer> kv: dic.keysValues()){
            // Encolamos
            pq.enqueue(new WLeafTree<>(kv._1(), kv._2()));
        }
        return pq;
    }

    // Exercise 2.b  
    public static WLeafTree<Character> huffmanTree(String s) {
    	// s debe tener dos caracteres distintos. Si no, excepción
        if(!hayMasDeUno(s)){
            throw  new HuffmanException("No hay más de un carácter en el texto");
        }
        PriorityQueue<WLeafTree<Character>> pq = huffmanLeaves(s);
        WLeafTree<Character> w1 = pq.first();
        pq.dequeue();
        while(!pq.isEmpty()){
            WLeafTree<Character> w2 = pq.first();
            pq.dequeue();
            pq.enqueue(new WLeafTree<>(w1,w2));
            w1 = pq.first();
            pq.dequeue();
        }
    	return w1;
    }

    private static boolean hayMasDeUno(String s){
        Set<Character> set = new AVLSet<>();
        for(char c: s.toCharArray()){
            set.insert(c);
        }
        return set.size() > 1;
    }

    // Exercise 3.a 
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        Dictionary<Character,List<Integer>> sol = new AVLDictionary<>();
        for(Tuple2<Character, List<Integer>> par: d1.keysValues()){
            sol.insert(par._1(), par._2());
        }
        for(Tuple2<Character,List<Integer>> par: d2.keysValues()){
            sol.insert(par._1(), par._2());
        }
    	return sol;
    }

    // Exercise 3.b  
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        Dictionary<Character, List<Integer>> dic = new AVLDictionary<>();
        for(Tuple2<Character, List<Integer>> par: d.keysValues()){
            List<Integer> list = new LinkedList<>();
            list.append(i);
            for(int j: par._2()){
                list.append(j);
            }
            dic.insert(par._1(),list);
        }
    	return dic;
    }

    // Exercise 3.c  
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        Dictionary<Character, List<Integer>> dic;
        if(ht.isLeaf()){
            dic = new AVLDictionary<>();
            dic.insert(ht.elem(),new LinkedList<>());
        }else{
            dic = joinDics(prefixWith(0,huffmanCode(ht.leftChild())),prefixWith(1, huffmanCode(ht.rightChild())));
        }
        return dic;
    }

    // Exercise 4  
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
    	List<Integer> sol = new LinkedList<>();
        for(char c: s.toCharArray()){
            for(int i: hc.valueOf(c))
                sol.append(i);
        }
        return sol;
    }

    // Exercise 5 
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        String texto = "";
        WLeafTree<Character> current = ht;
        for(int i : bits){
            if(i == 0){
                current = current.leftChild();
            }else{
                current = current.rightChild();
            }
            if (current.isLeaf()){
                texto += current.elem();
                current = ht;
            }
        }
    	return texto;
    }
}
