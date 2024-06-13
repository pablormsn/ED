/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de febrero de 2018.
 *
 * Apellidos, Nombre:
 * Titulacion, Grupo:
 */

package dataStructures.set;

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.ArrayList;
import dataStructures.list.List;

public class DisjointSetDictionary<T extends Comparable<? super T>> implements DisjointSet<T> {

    private Dictionary<T, T> dic;

    /**
     * Inicializa las estructuras necesarias.
     */
    public DisjointSetDictionary() {
        dic = new AVLDictionary<>();
    }

    /**
     * Devuelve {@code true} si el conjunto no contiene elementos.
     */
    @Override
    public boolean isEmpty() {
        // TODO
        return dic == null;
    }

    /**
     * Devuelve {@code true} si {@code elem} es un elemento del conjunto.
     */
    @Override
    public boolean isElem(T elem) {
        // TODO
        return dic.isDefinedAt(elem);
    }

    /**
     * Devuelve el número total de elementos del conjunto.
     */

    @Override
    public int numElements() {
        // TODO
        return dic.size();
    }

    /**
     * Agrega {@code elem} al conjunto. Si {@code elem} no pertenece al
     * conjunto, crea una nueva clase de equivalencia con {@code elem}. Si
     * {@code elem} pertencece al conjunto no hace nada.
     */
    @Override
    public void add(T elem) {
        if(!dic.isDefinedAt(elem)){
            dic.insert(elem,elem);
        }
    }

    /**
     * Devuelve el elemento canonico (la raiz) de la clase de equivalencia la
     * que pertenece {@code elem}. Si {@code elem} no pertenece al conjunto
     * devuelve {@code null}.
     */
    private T root(T elem) {
        // TODO
        if(!dic.isDefinedAt(elem)){
            return null;
        }else{
            T k = elem;
            T v = dic.valueOf(elem);
            while(!k.equals(v)){
                k = v;
                v = dic.valueOf(k);
            }
            return k;
        }
    }

    /**
     * Devuelve {@code true} si {@code elem} es el elemento canonico (la raiz)
     * de la clase de equivalencia a la que pertenece.
     */
    private boolean isRoot(T elem) {
        // TODO
        return elem.equals(dic.valueOf(elem));
    }

    /**
     * Devuelve {@code true} si {@code elem1} y {@code elem2} estan en la misma
     * clase de equivalencia.
     */
    @Override
    public boolean areConnected(T elem1, T elem2) {
        // TODO
        T r1 = root(elem1);
        T r2 = root(elem2);
        if(r1 == null || r2 == null){
            return false;
        }
        return root(elem1).equals(root(elem2));
    }

    /**
     * Devuelve una lista con los elementos pertenecientes a la clase de
     * equivalencia en la que esta {@code elem}. Si {@code elem} no pertenece al
     * conjunto devuelve la lista vacia.
     */


    @Override
    public List<T> kind(T elem) {
        // TODO
        List<T> claseEquivalencia = new ArrayList<>();
        if(dic.isDefinedAt(elem)){
            T canon = root(elem);
            for(T k: dic.keys()){
                if(root(k).equals(canon)){
                    claseEquivalencia.append(k);
                }
            }
        }
        return claseEquivalencia;
    }

    /**
     * Une las clases de equivalencias de {@code elem1} y {@code elem2}. Si
     * alguno de los dos argumentos no esta en el conjunto lanzara una excepcion
     * {@code IllegalArgumenException}.
     */
    @Override
    public void union(T elem1, T elem2) {
        // TODO
        T value1 = root(elem1);
        T value2 = root(elem2);
        if(value1 == null && value2 == null){
            throw new IllegalArgumentException();
        }else{
            if(value1.compareTo(value2) < 0){
                dic.insert(value2,value1);
            }else{
                dic.insert(value1,value2);
            }
        }
    }

    // ====================================================
    // A partir de aqui solo para alumnos a tiempo parcial
    // que no sigan el proceso de evaluacion continua.
    // ====================================================

    /**
     * Aplana la estructura de manera que todos los elementos se asocien
     * directamente con su representante canonico.
     */
    @Override
    public void flatten() {
        // TODO
        // Este método debo hacerlo sobre cada uno de los elementos de mi diccionario
        Dictionary<T,T> dicFlatten = new AVLDictionary<>();
        for(T k: dic.keys()){
            // Debo saber su elemento canónico
            T canon = root(k);
            dicFlatten.insert(k,canon);
        }
        dic = dicFlatten;
    }

    /**
     * Devuelve una lista que contiene las clases de equivalencia del conjunto
     * como listas.
     */

    private static <T> boolean notIn (T root, List<List<T>> rel){
        boolean notRep = true;
        for(List<T> l : rel){
            if(l.get(0).equals(root)){
                notRep = false;
            }
        }
        return notRep;
    }

    @Override
    public List<List<T>> kinds() {
        // TODO
        List<List<T>> relEq = new ArrayList<>();
        List<T> eqClass = new ArrayList<>();
        int i = 0;
        for(T k: dic.keys()){
            if(relEq.isEmpty()){
                eqClass = kind(k);
                relEq.append(eqClass);
            }else{
                T canon = root(k);
                if(notIn(canon,relEq)){
                    eqClass = kind(k);
                    relEq.append(eqClass);
                }
            }
        }
        return relEq;
    }

    /**
     * Devuelve una representacion del conjunto como una {@code String}.
     */
    @Override
    public String toString() {
        return "DisjointSetDictionary(" + dic.toString() + ")";
    }
}
