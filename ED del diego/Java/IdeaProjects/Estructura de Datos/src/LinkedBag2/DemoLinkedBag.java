package LinkedBag2;
import LinkedBag2.LinkedBag.*;

public class DemoLinkedBag {
    public static void main (String[] args){
        LinkedBag l = new LinkedBag();
        l.insert(3);
        l.insert(2);
        l.insert(4);
        l.insert(3);
        if(l.isEmpty()){
            System.out.println("Esta vacia la bolsa");
        }else{
            System.out.println("No esta vacia la bolsa");
        }
    }
}
