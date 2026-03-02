import java.util.ArrayList;
import java.util.List;

public class IDAStar {
    private int nodosExpandidos;
    private long tiempoInicio;
    private boolean usarConflictoLineal;

    public IDAStar(boolean usarConflictoLineal) {
        this.usarConflictoLineal = usarConflictoLineal;
    }

    public int getNodosExpandidos() { return nodosExpandidos; }

    public List<Estado> resolver(Estado inicial) {
        nodosExpandidos = 0;
        int limite = calcularH(inicial);
        List<Estado> camino = new ArrayList<>();
        camino.add(inicial);

        while (true) {
            int resultado = busqueda(camino, 0, limite);
            if (resultado == 0) { // 0 significa ENCONTRADO
                return camino;
            }
            if (resultado == Integer.MAX_VALUE) { // Imposible
                return null;
            }
            limite = resultado; // Aumentamos el umbral para la siguiente iteración profunda
        }
    }

    private int busqueda(List<Estado> camino, int g, int limite) {
        Estado actual = camino.get(camino.size() - 1);
        int f = g + calcularH(actual);

        if (f > limite) return f;
        if (actual.esObjetivo()) return 0;

        int minimo = Integer.MAX_VALUE;
        nodosExpandidos++;

        for (Estado sucesor : actual.getSucesores()) {
            if (!camino.contains(sucesor)) { // Evitar bucles en el camino actual
                camino.add(sucesor);
                int t = busqueda(camino, g + 1, limite);
                if (t == 0) return 0;
                if (t < minimo) minimo = t;
                camino.remove(camino.size() - 1);
            }
        }
        return minimo;
    }

    private int calcularH(Estado estado) {
        return usarConflictoLineal ? estado.heuristicaConflictoLineal() : estado.heuristicaManhattan();
    }
}