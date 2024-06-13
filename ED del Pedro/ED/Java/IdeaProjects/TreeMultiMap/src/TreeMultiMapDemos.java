import java.util.*;

public class TreeMultiMapDemos{
    public static void main(String[] args){
        int[] vars = {1,2,3,4,5,6,7};
        List<Integer> values = new ArrayList<>();
        for(int x: vars){
            values.add(x);
        }

        TreeMultiMap<String,Integer> arbol = new TreeMultiMap<>();
        arbol.insert("sergio",values);
        arbol.insert("alvaro",values);
        arbol.insert("juan",values);
        arbol.insert("dario",values);
        arbol.insert("fran",values);
        arbol.deleteKey("fran");
        arbol.deleteKey("juan");
        System.out.println(arbol);
        if(arbol.isDefinedAt("dar")){
            System.out.println("Sí pertenece");
        }else{
            System.out.println("No pertenece");
        }
        System.out.println(arbol.valuesOf("dar"));
    }
}
