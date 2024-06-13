package postFix;

public class PostFix {

    static Item[] simplificar(int res, Item[] expr, int pos){
        Item[] aux = new Item[expr.length];
        int i = 0,j = 0;
        while(i < aux.length){
            if(i == pos-2){
                aux[j] = new Data(res);
                i = pos+1;
            }else{
                aux[j] = expr[i];
                i++;
            }
            j++;
        }
        return aux;
    }
    static int evaluate(Item[] exprList){
        int res = 0;
        int i = 0;
        while(exprList[1] != null){
            if(exprList[i].isOperation()){
                res = exprList[i].evaluate(exprList[i-2].getValue(),exprList[i-1].getValue());
                exprList = simplificar(res,exprList,i);
                i = 0;
            }
            i++;
        }
        return res;
    }

    public static void main(String[] args){
        Item[] sample = {
                new Data(5),
                new Data(6),
                new Data(2),
                new Dif(),
                new Data(3),
                new Mul(),
                new Add()
        };

        System.out.println("El resultado de evaluar la expresión es " + evaluate(sample));
    }
}
