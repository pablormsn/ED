package dataStructures.bag;

import java.util.ArrayList;
import java.util.List;

public class BagUtils {

    /**
     * Returns the most frequent element in the bag {@code b}. If there are several
     * elements with the same number of occurrences, returns the maximum of them. If
     * the bag is empty returns {@code null} (which is usually a bad idea).
     *
     * @param b the bag to scan for the most frequent element
     * @return the most frequent element in {@code b}
     */

    private static <T> List<T> bagValues2List(Bag<T> b){
        List<T> auxLista = new ArrayList<>();
        for(T aux: b){
            auxLista.add(aux);
        }
        return auxLista;
    }

    public static <T extends Comparable<? super T>> T mostFrequent(Bag<T> b) {
        List<T> values = bagValues2List(b);
        int cnt = 0;
        int maxOc = 0;
        T mF = null;
        // Caso de empty
        if(values.isEmpty()){
            return mF;
        }else if(values.size() == 1){ // Un solo elemento
            return values.get(0);
        }else{ // Más de un elemento
            int i = 0;
            while(i < values.size()-1){
                cnt++;
                if(values.get(i) != values.get(i+1)){
                    if(maxOc < cnt){
                        maxOc = cnt;
                        mF = values.get(i);
                    }else if(maxOc == cnt){
                        if(values.get(i).compareTo(mF) > 0) mF = values.get(i);
                    }
                    cnt = 0;
                }
                i++;
            }

            if(values.get(values.size()-1).compareTo(values.get(values.size()-2)) == 0){
                cnt++;
            }else{
                cnt = 1;
            }

            if(maxOc < cnt){
                maxOc = cnt;
                mF = values.get(i);
            }else if(maxOc == cnt){
                if(values.get(values.size()-1).compareTo(mF) > 0) mF = values.get(values.size()-1);
            }
        }
        return mF;
    }
}
