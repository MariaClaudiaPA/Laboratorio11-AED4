package graphmatrix;

public interface Grafo {

    // Inserta un vértice v en el grafo, si no existe
    void insertVertex(int v);

    // Inserta una arista entre los vértices v y z, si no existe
    void insertEdge(int v, int z);

    // Busca un vértice v en el grafo
    boolean searchVertex(int v);

    // Busca una arista entre los vértices v y z en el grafo
    boolean searchEdge(int v, int z);

    // Realiza un recorrido DFS (Depth-First Search) desde el vértice v
    void dfs(int v);
}
