
public class Nodo implements Comparable<Nodo> {

    String estado;
    Nodo padre;
    int costo;
    int heuristica;

    public Nodo(String estado) {
        this.estado = estado;
    }

    public String[] getSucesores() {
        return puzzle8.generarSucesores(this.estado);
    }

    @Override
    public int compareTo(Nodo o) {
        return Integer.compare(this.getF(), o.getF());
    }

    public int getF() {
        return costo + heuristica;
    }

}
