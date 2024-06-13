import wellBalanced.WellBalanced;
import dataStructures.stack.*;

public class pruebaWellBalanced {

    public static void main(String[] args){
        String cadena = "vv(hg(jij)hags{ss[dd]dd})";
        Stack s = new ArrayStack();
        // Ya he creado mi cadena de prueba
        // Ahora a probar el método
        boolean balanceada = WellBalanced.wellBalanced(cadena,s);
        if(balanceada){
            System.out.println("Está balanceada");
        }else{
            System.out.println("No está balanceada");
        }
    }

}
