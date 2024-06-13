package PostFix;

public class Data extends Item{
    private int valor;

    public Data(int n){
        valor = n;
    }

    public boolean isData(){
        return true;
    }

    public int getValue(){
        return valor;
    }
}
