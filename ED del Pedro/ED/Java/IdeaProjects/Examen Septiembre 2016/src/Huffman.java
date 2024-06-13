
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
import dataStructures.list.ArrayList;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.priorityQueue.WBLeftistHeapPriorityQueue;
import dataStructures.tuple.Tuple2;

public class Huffman {

    // Exercise 1

    private static int frec(Character c, String s){
        int f = 0;
        int i = 0;
        while(i < s.length()){
            if(c.equals(s.charAt(i))){
                f++;
            }
            i++;
        }
        return f;
    }

    private static boolean isElem(Character c, List<Character> charsVisited){
        boolean encontrado = false;
        for(int i = 0; i < charsVisited.size() && !encontrado; i++){
            if(c.equals(charsVisited.get(i))){
                encontrado = true;
            }
        }
        return encontrado;
    }

    public static Dictionary<Character, Integer> weights(String s) {
    	Dictionary<Character,Integer> dict = new AVLDictionary<>();
        List<Character> charsVisited = new ArrayList<>();
        for(Character c : s.toCharArray()){
            if(!isElem(c,charsVisited)){
                dict.insert(c,frec(c,s));
                charsVisited.append(c);
            }
        }
        return dict;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
        PriorityQueue<WLeafTree<Character>> colaPesos = new WBLeftistHeapPriorityQueue<>();
        for(Tuple2<Character,Integer> kV: weights(s).keysValues()){
            colaPesos.enqueue(new WLeafTree<Character>(kV._1(), kV._2()));
        }
        return colaPesos;
    }

    // Exercise 2.b

    private static WLeafTree<Character> buildTree(PriorityQueue<WLeafTree<Character>> aux){
        WLeafTree<Character> auxNuevo = aux.first();
        aux.dequeue();
        if(!aux.isEmpty()){
            aux = buildTree(auxNuevo);
        }else{
            return aux.first();
        }
    }

    public static WLeafTree<Character> huffmanTree(String s) {
        if(weights(s).size() < 2){
            throw new HuffmanException("the string must have at least 2 different symbols");
        }
        WLeafTree<Character> Wtree = buildTree(huffmanLeaves(s));
        return Wtree;
    }

    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        //to do 
    	return null;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        //to do 
    	return null;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        //to do 
    	return null;
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
