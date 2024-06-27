package hash;

public class TestA {

    public static void main(String[] args) {

        HashA<String> tablaHash = new HashA<>(7);
        int[] claves = {34, 3, 7, 30, 11, 8, 7, 23, 41, 16, 34};
        String[] nombres = {"Manzana", "Uva", "Banana", "Pera", "Papaya", "Pi�a", "Mandarina", "Cereza", "Guayaba", "Lucuma", "Palta"};
        //Inserci�n
        for (int i = 0;
                i < claves.length;
                i++) {
            String nombre = nombres[i];
            Register<String> registro = new Register<>(claves[i], nombre);
            tablaHash.insert(claves[i], registro);
        }
        System.out.println(tablaHash);
        //B�squeda
        System.out.println("Tabla Hash:");
        System.out.println(tablaHash.toString());
        Register<String> busqueda = tablaHash.search(8);
        //ELiminaci�n
        System.out.println("Busqueda:" + busqueda);
        tablaHash.remove(11);
        System.out.println(tablaHash.toString());
    }
}
