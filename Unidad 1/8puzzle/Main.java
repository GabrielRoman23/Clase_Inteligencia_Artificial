
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        String estadoInicial = "8672543 1";
        String estadoObjetivo = "12345678 ";

        // Validación de resolubilidad 
        if (!esSolucionable(estadoInicial, estadoObjetivo)) {
            System.out.println("El estado inicial proporcionado NO tiene solución matemática.");
            return; 
        }

        Nodo raiz = new Nodo(estadoInicial);
        ArbolBusqueda busqueda = new ArbolBusqueda(raiz);

        // --- EJECUCIÓN BFS ---
        long inicioAnchura = System.nanoTime();
        Nodo resultadoAnchura = busqueda.busquedaPrimeroEnAnchura(estadoObjetivo);
        long finAnchura = System.nanoTime();
        int expAnchura = busqueda.getNodosExpandidos();
        int visAnchura = busqueda.getNodosVisitados();
        double tiempoAnchura = (finAnchura - inicioAnchura) / 1_000_000_000.0;

        // --- EJECUCIÓN DFS ---
        long inicioProfundidad = System.nanoTime();
        Nodo resultadoProfundidad = busqueda.busquedaPrimeroEnProfundidad(estadoObjetivo);
        long finProfundidad = System.nanoTime();
        int expProfundidad = busqueda.getNodosExpandidos();
        int visProfundidad = busqueda.getNodosVisitados();
        double tiempoProfundidad = (finProfundidad - inicioProfundidad) / 1_000_000_000.0;

        // --- EJECUCIÓN A* ---
        long inicioAEstrella = System.nanoTime();
        Nodo resultadoAEstrella = busqueda.busquedaAEstrella(estadoObjetivo);
        long finAEstrella = System.nanoTime();
        int expAEstrella = busqueda.getNodosExpandidos();
        int visAEstrella = busqueda.getNodosVisitados();
        double tiempoAEstrella = (finAEstrella - inicioAEstrella) / 1_000_000_000.0;

        // --- IMPRESIÓN DE MÉTRICAS ---
        System.out.println("--- RESULTADOS COMPARATIVOS ---");
        System.out.printf("%-18s | %-12s | %-15s | %-15s\n", "ALGORITMO", "TIEMPO (s)", "EXPANDIDOS", "VISITADOS (RAM)");
        System.out.println("-------------------+--------------+-----------------+-----------------");
        System.out.printf("%-18s | %-12.5f | %-15d | %-15d\n", "BFS (Anchura)", tiempoAnchura, expAnchura, visAnchura);
        System.out.printf("%-18s | %-12.5f | %-15d | %-15d\n", "DFS (Profundidad)", tiempoProfundidad, expProfundidad, visProfundidad);
        System.out.printf("%-18s | %-12.5f | %-15d | %-15d\n", "A* (Prioridad)", tiempoAEstrella, expAEstrella, visAEstrella);
        System.out.println("----------------------------------------------------------------------");

        if (resultadoAEstrella != null) {
            System.out.println("\nMostrando camino óptimo encontrado por A*:");
            imprimirCamino(resultadoAEstrella);
        }

    }

    public static void imprimirTablero(String estado) {
        System.out.println("+---+---+---+");
        for (int i = 0; i < 9; i += 3) {
            System.out.println("| " + estado.charAt(i) + " | " + estado.charAt(i + 1) + " | " + estado.charAt(i + 2) + " |");
            System.out.println("+---+---+---+");
        }
    }

    public static void imprimirCamino(Nodo nodo) {
        List<Nodo> camino = new ArrayList<>();
        Nodo actual = nodo;

        while (actual != null) {
            camino.add(actual);
            actual = actual.padre;
        }

        Collections.reverse(camino);

        System.out.println("Solución encontrada en " + (camino.size() - 1) + " movimientos.");

        for (int i = 0; i < camino.size(); i++) {
            if (i == 0) {
                System.out.println("--- ESTADO INICIAL ---");
            } else {
                System.out.println("--- PASO " + i + " ---");
            }

            imprimirTablero(camino.get(i).estado);
            System.out.println();
        }
    }

    public static boolean esSolucionable(String inicial, String objetivo) {
        // Tienen solución si ambos tienen la misma paridad de inversiones
        return (contarInversiones(inicial) % 2) == (contarInversiones(objetivo) % 2);
    }

    private static int contarInversiones(String estado) {
        int inversiones = 0;
        String estadoSinEspacio = estado.replace(" ", "");
        for (int i = 0; i < estadoSinEspacio.length() - 1; i++) {
            for (int j = i + 1; j < estadoSinEspacio.length(); j++) {
                if (estadoSinEspacio.charAt(i) > estadoSinEspacio.charAt(j)) {
                    inversiones++;
                }
            }
        }
        return inversiones;
    }

}
