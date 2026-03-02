import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Estado {
    public byte[] tablero;
    public int posVacio;

    public Estado(byte[] tablero) {
        this.tablero = tablero.clone();
        for (int i = 0; i < 25; i++) {
            if (tablero[i] == 0) {
                this.posVacio = i;
                break;
            }
        }
    }

    // Heurística 1: Distancia de Manhattan (Obligatoria)
    public int heuristicaManhattan() {
        int distancia = 0;
        for (int i = 0; i < 25; i++) {
            int valor = tablero[i];
            if (valor != 0) {
                int targetX = (valor - 1) % 5;
                int targetY = (valor - 1) / 5;
                int actualX = i % 5;
                int actualY = i / 5;
                distancia += Math.abs(actualX - targetX) + Math.abs(actualY - targetY);
            }
        }
        return distancia;
    }

    // Heurística 2: Conflicto Lineal (Extra/Opcional)
    // Suma la Distancia de Manhattan + penalización por piezas en la misma fila/columna pero invertidas
    public int heuristicaConflictoLineal() {
        int heuristica = heuristicaManhattan();
        int conflictos = 0;

        // Conflictos en filas
        for (int fila = 0; fila < 5; fila++) {
            for (int i = 0; i < 4; i++) {
                for (int j = i + 1; j < 5; j++) {
                    int valA = tablero[fila * 5 + i];
                    int valB = tablero[fila * 5 + j];
                    if (valA != 0 && valB != 0 && (valA - 1) / 5 == fila && (valB - 1) / 5 == fila) {
                        if (valA > valB) conflictos++; // Están en la fila correcta pero invertidos
                    }
                }
            }
        }
        // Cada conflicto requiere al menos 2 movimientos extra para esquivarse
        return heuristica + (2 * conflictos); 
    }

    // Genera los sucesores válidos intercambiando el espacio vacío (0)
    public List<Estado> getSucesores() {
        List<Estado> sucesores = new ArrayList<>();
        int x = posVacio % 5;
        int y = posVacio / 5;

        // Movimientos: Arriba, Abajo, Izquierda, Derecha
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                int nuevaPos = ny * 5 + nx;
                byte[] nuevoTablero = tablero.clone();
                // Intercambio
                nuevoTablero[posVacio] = nuevoTablero[nuevaPos];
                nuevoTablero[nuevaPos] = 0;
                sucesores.add(new Estado(nuevoTablero));
            }
        }
        return sucesores;
    }

    public boolean esObjetivo() {
        // El objetivo es 1,2,3...24,0
        for (int i = 0; i < 24; i++) {
            if (tablero[i] != i + 1) return false;
        }
        return tablero[24] == 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Estado)) return false;
        return Arrays.equals(this.tablero, ((Estado) obj).tablero);
    }
}