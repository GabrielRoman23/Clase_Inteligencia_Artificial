public class puzzle8 {

    public static String[] generarSucesores(String estadoActual) {
        int indice = estadoActual.indexOf(" ");

        // 0 1 2 
        // 3 4 5
        // 6 7 8
        int[][] vecinos = {
        	    {1, 3},        // Vecinos de 0
        	    {0, 2, 4},     // Vecinos de 1
        	    {1, 5},        // Vecinos de 2
        	    {0, 4, 6},     // Vecinos de 3
        	    {1, 3, 5, 7},  // Vecinos de 4
        	    {2, 4, 8},     // Vecinos de 5
        	    {3, 7},        // Vecinos de 6
        	    {4, 6, 8},     // Vecinos de 7
        	    {5, 7}         // Vecinos de 8
        	};

        int[] posiblesMovimientos = vecinos[indice];
        String[] sucesores = new String[posiblesMovimientos.length];

        for (int i = 0; i < posiblesMovimientos.length; i++)
        	sucesores[i] = intercambiar(estadoActual, indice, posiblesMovimientos[i]);


        return sucesores;
    }

    private static String intercambiar(String estado, int i, int j) {
        char[] caracteres = estado.toCharArray();
        char temp = caracteres[i];
        caracteres[i] = caracteres[j];
        caracteres[j] = temp;
        return new String(caracteres);
    }


}
