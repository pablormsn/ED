package Fechas;

public class fecha
{
    private int dia;
    private int mes;
    private int año;

    public fecha (int d, int m, int a){
        dia = d;
        mes = m;
        año = a;
    }
    public void mañana(){
        dia++;
    }

    public String toString(){
        return dia + "/" + mes + "/" + año;
    }
}
