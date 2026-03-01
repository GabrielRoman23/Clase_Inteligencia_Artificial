
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class ArbolBusqueda {

    private final Nodo raiz;
    private int nodosExpandidos;
    private int nodosVisitados;

    public int getNodosExpandidos() {
        return nodosExpandidos;
    }

    public int getNodosVisitados() {
        return nodosVisitados;
    }

    public ArbolBusqueda(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo busquedaPrimeroEnAnchura(String estadoObjetivo) {

        if (raiz == null) {
            return null;
        }

        nodosExpandidos = 0; 

        HashSet<String> visitados = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();

        cola.add(raiz);
        visitados.add(raiz.estado);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            nodosExpandidos++; 

            if (actual.estado.equals(estadoObjetivo)) {
                nodosVisitados = visitados.size(); 
                return actual;
            }
            
            String[] sucesores = actual.getSucesores();

            for (String sucesor : sucesores) {
                if (!visitados.contains(sucesor)) {
                    visitados.add(sucesor);
                    Nodo nuevoHijo = new Nodo(sucesor);
                    nuevoHijo.padre = actual;
                    cola.add(nuevoHijo);
                }
            }
        }
        nodosVisitados = visitados.size();
        return null;
    }

    public Nodo busquedaPrimeroEnProfundidad(String estadoObjetivo) {

        if (raiz == null) {
            return null;
        }

        nodosExpandidos = 0; 

        HashSet<String> visitados = new HashSet<>();
        Stack<Nodo> pila = new Stack<>();

        pila.add(raiz);
        visitados.add(raiz.estado);

        while (!pila.isEmpty()) {
            Nodo actual = pila.pop();
            nodosExpandidos++;

            if (actual.estado.equals(estadoObjetivo)) {
                nodosVisitados = visitados.size();
                return actual;
            }

            String[] sucesores = actual.getSucesores();

            for (String sucesor : sucesores) {
                if (!visitados.contains(sucesor)) {
                    visitados.add(sucesor);
                    Nodo nuevoHijo = new Nodo(sucesor);
                    nuevoHijo.padre = actual;
                    pila.add(nuevoHijo);
                }
            }
        }

        nodosVisitados = visitados.size();
        return null;
    }

    public Nodo busquedaAEstrella(String estadoObjetivo) {
        if (raiz == null) {
            return null;
        }

        nodosExpandidos = 0; 

        PriorityQueue<Nodo> frontera = new PriorityQueue<>();
        // Usamos un mapa para registrar el mejor costo (g) para cada estado
        HashMap<String, Integer> costosMejores = new HashMap<>();

        raiz.costo = 0;
        raiz.heuristica = calcularHeuristica(raiz.estado, estadoObjetivo);
        frontera.add(raiz);
        costosMejores.put(raiz.estado, 0);

        while (!frontera.isEmpty()) {
            Nodo actual = frontera.poll();

            nodosExpandidos++; 

            if (actual.estado.equals(estadoObjetivo)) {
                nodosVisitados = costosMejores.size();
                return actual;
            }

            // Si sacamos un nodo pero ya habíamos llegado a este estado con un costo MEJOR, lo ignoramos 
            if (actual.costo > costosMejores.getOrDefault(actual.estado, Integer.MAX_VALUE)) {
                continue;
            }

            for (String sucesor : actual.getSucesores()) {
                int nuevoCosto = actual.costo + 1;

                // Solo encolamos el sucesor si es la primera vez que lo vemos, 
                // o si encontramos un camino más corto/barato hacia él
                if (nuevoCosto < costosMejores.getOrDefault(sucesor, Integer.MAX_VALUE)) {
                    costosMejores.put(sucesor, nuevoCosto);

                    Nodo nuevoHijo = new Nodo(sucesor);
                    nuevoHijo.padre = actual;
                    nuevoHijo.costo = nuevoCosto;
                    nuevoHijo.heuristica = calcularHeuristica(sucesor, estadoObjetivo);
                    frontera.add(nuevoHijo);
                }
            }
        }
        nodosVisitados = costosMejores.size();
        return null;
    }

    private int calcularHeuristica(String estado, String objetivo) {
        int piezasFuera = 0;
        for (int i = 0; i < estado.length(); i++) {
            if (estado.charAt(i) != ' ' && estado.charAt(i) != objetivo.charAt(i)) {
                piezasFuera++;
            }
        }
        return piezasFuera;
    }

}
