package postFix;

public class Data extends Item {

    private int valor;

    public Data(int n){
        this.valor = n;
    }

    @Override
    public boolean isData() {
        return true;
    }

    public int getValue(){
        return this.valor;
    }
}
