package dataStructures.Huffman;
/**
 * dataStructures.Huffman trees and codes.
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
import dataStructures.priorityQueue.LinkedPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.WBLeftistHeapPriorityQueue;
import dataStructures.set.LinkedListSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

public class Huffman {

    // Exercise 1
    public static Dictionary<Character, Integer> weights(String s) {
        Dictionary<Character, Integer> dict = new AVLDictionary<>();
        for(int i=0; i<s.length(); i++){
            if(dict.isDefinedAt(s.charAt(i))){
                dict.insert(s.charAt(i), (dict.valueOf(s.charAt(i))+1));
            }else{
                dict.insert(s.charAt(i), 1);
            }
        }

        return dict;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
        Dictionary<Character, Integer> dict = new AVLDictionary<>();
        dict = weights(s);
        PriorityQueue<WLeafTree<Character>> pq = new WBLeftistHeapPriorityQueue<>();
        for(Tuple2<Character, Integer> t:dict.keysValues()){
            WLeafTree<Character> tree = new WLeafTree<>(t._1(),t._2());
            pq.enqueue(tree);
        }

        return pq;
    }

    private static boolean minimo2(String s){
        boolean ok = false;
        Set<Character> set = new LinkedListSet<>();
        for(int i=0; i<s.length(); i++){
            set.insert(s.charAt(i));
        }
        if(set.size()>2){
            ok=true;
        }
        return ok;

    }

    // Exercise 2.b
    public static WLeafTree<Character> huffmanTree(String s) {

        if(!minimo2(s)){
            throw new HuffmanException("La cadena tiene que tener al menos dos carácteres");
        }
        PriorityQueue<WLeafTree<Character>> pq = huffmanLeaves(s);

        return huffmanTreeRec(pq);

    }

    private static WLeafTree<Character> huffmanTreeRec(PriorityQueue<WLeafTree<Character>> pq) {
        WLeafTree<Character> tree = pq.first();
        pq.dequeue();
        while(!pq.isEmpty()){
            WLeafTree<Character> right = pq.first();
            pq.dequeue();
            tree = new WLeafTree<>(tree, right);
        }
        return tree;

    }
    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {

        for(Tuple2<Character, List<Integer>> kv : d1.keysValues()){
            d2.insert(kv._1(), kv._2());
        }
        return d2;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {

        for(Tuple2<Character, List<Integer>> kv : d.keysValues()){
            kv._2().prepend(i);
            d.insert(kv._1(), kv._2());
        }

    	return null;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        List<Integer> list = new LinkedList<>();
    	return huffmanCodeRec(ht, list, new AVLDictionary<>());
    }

    private static Dictionary<Character, List<Integer>> huffmanCodeRec(WLeafTree<Character> ht,List<Integer> list, Dictionary<Character, List<Integer>> d) {
        if(ht.isLeaf()){
            d.insert(ht.elem(), list);
            return d;
        }else{
            List<Integer> l1 = new LinkedList<>();
            List<Integer> l2 = new LinkedList<>();
            l1.append(0);
            l2.append(1);
            return joinDics(huffmanCodeRec(ht.leftChild(), l1, d), huffmanCodeRec(ht.rightChild(), l2, d));
        }
        /*if(ht.leftChild().isLeaf() && ht.rightChild().isLeaf()){
            List<Integer> l1 = new LinkedList<>();
            List<Integer> l2 = new LinkedList<>();
            l1.append(0);
            l2.append(1);
            d1.insert(ht.leftChild().elem(), l1);
            d2.insert(ht.rightChild().elem(), l2);
            return joinDics(d1, d2);
        }else{
            List<Integer> l2 = new LinkedList<>();
        }*/
    }



    // Exercise 4
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        //to do 
    	return null;
    }

    // Exercise 5
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        //to do 
    	return null;
    }
}
