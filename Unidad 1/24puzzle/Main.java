
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Generar un estado aleatorio RESOLUBLE mezclando desde la meta
        Estado inicial = generarEstadoResoluble(35); // 35 movimientos aleatorios

        System.out.println("=== 24-PUZZLE (5x5) ===");
        System.out.println("Estado Inicial generado aleatoriamente:");
        imprimirTablero(inicial.tablero);

        // --- PRUEBA 1: IDA* con Manhattan ---
        System.out.println("\nEjecutando IDA* (Distancia de Manhattan)...");
        IDAStar idaManhattan = new IDAStar(false);
        long inicio1 = System.currentTimeMillis();
        List<Estado> solucion1 = idaManhattan.resolver(inicial);
        long fin1 = System.currentTimeMillis();

        // --- PRUEBA 2: IDA* con Conflicto Lineal ---
        System.out.println("Ejecutando IDA* (Conflicto Lineal)...");
        IDAStar idaConflicto = new IDAStar(true);
        long inicio2 = System.currentTimeMillis();
        List<Estado> solucion2 = idaConflicto.resolver(inicial);
        long fin2 = System.currentTimeMillis();

        // --- ANÁLISIS DE RENDIMIENTO ---
        System.out.println("\n--- ANÁLISIS DE RENDIMIENTO ---");
        System.out.printf("%-20s | %-12s | %-15s | %-15s\n", "HEURÍSTICA", "TIEMPO (ms)", "EXPANDIDOS", "MOVIMIENTOS");
        System.out.println("---------------------+--------------+-----------------+-----------------");

        if (solucion1 != null) {
            System.out.printf("%-20s | %-12d | %-15d | %-15d\n", "Manhattan", (fin1 - inicio1), idaManhattan.getNodosExpandidos(), solucion1.size() - 1);
        }
        if (solucion2 != null) {
            System.out.printf("%-20s | %-12d | %-15d | %-15d\n", "Conflicto Lineal", (fin2 - inicio2), idaConflicto.getNodosExpandidos(), solucion2.size() - 1);
        }

        System.out.println("\nMostrando camino de IDA* (Conflicto Lineal):");
        imprimirCamino(solucion2);

    }

    // Genera un estado que matemáticamente tiene solución
    // partiendo del objetivo y haciendo movimientos aleatorios hacia atrás
    private static Estado generarEstadoResoluble(int movimientosAleatorios) {
        byte[] meta = {
            1, 2, 3, 4, 5,
            6, 7, 8, 9, 10,
            11, 12, 13, 14, 15,
            16, 17, 18, 19, 20,
            21, 22, 23, 24, 0
        };
        Estado estado = new Estado(meta);
        Random rand = new Random();

        Estado previo = null;
        for (int i = 0; i < movimientosAleatorios; i++) {
            List<Estado> sucesores = estado.getSucesores();
            Estado siguiente;
            do {
                siguiente = sucesores.get(rand.nextInt(sucesores.size()));
            } while (siguiente.equals(previo));

            previo = estado;
            estado = siguiente;
        }
        return estado;
    }

    public static void imprimirTablero(byte[] tablero) {
        for (int i = 0; i < 25; i++) {
            if (tablero[i] == 0) {
                System.out.printf("[  ] ");
            } else {
                System.out.printf("[%2d] ", tablero[i]);
            }
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
    }

    public static void imprimirCamino(List<Estado> camino) {
        System.out.println("\n--- DETALLE DE LA SOLUCIÓN ---");
        for (int i = 0; i < camino.size(); i++) {
            if (i == 0) {
                System.out.println("ESTADO INICIAL:");
            } else {
                System.out.println("MOVIMIENTO " + i + ":");
            }
            imprimirTablero(camino.get(i).tablero);
            System.out.println();
        }
    }
}
