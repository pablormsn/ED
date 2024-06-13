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
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.WBLeftistHeapPriorityQueue;
import dataStructures.tuple.Tuple2;

public class Huffman {

    // Exercise 1
    public static Dictionary<Character, Integer> weights(String s) {

        Dictionary<Character, Integer> dic = new AVLDictionary<>();

        for (int i = 0; i < s.length(); i ++){
            Character c = s.charAt(i);
            Integer v = dic.valueOf(c);
            if(v == null){
                dic.insert(c, 1);
            }else {
                dic.insert(c, v + 1);
            }
        }

        return dic;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {

        PriorityQueue<WLeafTree<Character>> pq = new WBLeftistHeapPriorityQueue<>();
        Dictionary<Character, Integer> dic = weights(s);

        for (Tuple2<Character, Integer> tup : dic.keysValues()){
            pq.enqueue(new WLeafTree<>(tup._1(), tup._2()));
        }

        return pq;
    }

    // Exercise 2.b
    public static WLeafTree<Character> huffmanTree(String s) {

        PriorityQueue<WLeafTree<Character>> pq = huffmanLeaves(s);

        return huffmanTreeRec(pq.first(), pq);

    }

    private static WLeafTree<Character> huffmanTreeRec(WLeafTree<Character> first, PriorityQueue<WLeafTree<Character>> pq){

        pq.dequeue();

        if(pq.isEmpty()){
            return first;
        }else {
            WLeafTree<Character> wl = pq.first();
            return huffmanTreeRec(merge(first, wl), pq);
        }

    }

    private static WLeafTree<Character> merge(WLeafTree<Character> left, WLeafTree<Character> right){

        return new WLeafTree<>(left, right);

    }

    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {

        for (Tuple2<Character, List<Integer>> tup : d1.keysValues()){
            d2.insert(tup._1(), tup._2());
        }

    	return d2;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {

        for (Tuple2<Character, List<Integer>> tup : d.keysValues()){
            tup._2().prepend(i);
        }

    	return d;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {

    	return huffmanCodeRec(ht, new AVLDictionary<>(), new AVLDictionary<>());
    }

    private static Dictionary<Character, List<Integer>> huffmanCodeRec(WLeafTree<Character> wlt, Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2){

        if(wlt.leftChild().isLeaf() && wlt.rightChild().isLeaf()){
            List<Integer> l1 = new LinkedList<>();
            List<Integer> l2 = new LinkedList<>();
            l1.append(0);
            l2.append(1);
            d1.insert(wlt.leftChild().elem(), l1);
            d2.insert(wlt.rightChild().elem(), l2);
            return joinDics(d1, d2);
        }else {
            List<Integer> l2 = new LinkedList<>();
            l2.append(1);
            d2.insert(wlt.rightChild().elem(), l2);
            d1 = prefixWith(0, huffmanCodeRec(wlt.leftChild(), d1, d2));
            return joinDics(d1, d2);
        }
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
