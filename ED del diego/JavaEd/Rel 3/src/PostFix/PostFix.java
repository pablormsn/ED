package PostFix;

import dataStructures.stack.*;

public class PostFix {

    public static int evaluate(Item[] exprList){
        // Vamos a definir una donde vamos guardando los valores
        // Y otra para guardar las operaciones ya realizadas
        Stack<Integer> datos = new ArrayStack();
        Stack<Item> ops = new ArrayStack();

        for(int i = 0; i < exprList.length; i++){
            if(exprList[i].isData()){
                datos.push(exprList[i].getValue());
            }else{
                int a = datos.top();
                datos.pop();
                int b = datos.top();
                datos.pop();
                // Ya saqué los valores
                // Evaluo
                if(exprList[i] instanceof Dif){
                    if(a < b){
                        datos.push(exprList[i].evaluate(b,a));
                    }else{
                        datos.push(exprList[i].evaluate(a,b));
                    }
                }else{
                    datos.push(exprList[i].evaluate(a,b));
                }
                // Guardo la operación en la pila
                ops.push(exprList[i]);
            }
        }

        return datos.top();
    }

    public static void main(String[] args){
        // Para hacer la prueba
        Item[] sample = {
                new Data(5),
                new Data(6),
                new Data(2),
                new Dif(),
                new Data(3),
                new Mul(),
                new Add()
        };

        System.out.println(evaluate(sample));
    }
}
