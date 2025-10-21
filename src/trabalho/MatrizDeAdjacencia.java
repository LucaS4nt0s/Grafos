package trabalho;

import java.util.ArrayList;
import grafos.Grafo;
import grafos.Aresta;
import grafos.Vertice;

public class MatrizDeAdjacencia implements Grafo {

    private final double[][] matriz;
    private final int numVertices;

    public MatrizDeAdjacencia(int numVertices) {
        this.numVertices = numVertices;
        matriz = new double[numVertices][numVertices];

        // Inicializa a matriz com infinito
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                matriz[i][j] = Double.MAX_VALUE;
            }
        }
    }
    
    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        // cria o objeto Aresta e adiciona na matriz
        Aresta aresta = new Aresta(origem, destino);
        matriz[aresta.origem().id()][aresta.destino().id()] = 1;
    }   

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        if(peso > Double.MAX_VALUE || peso < Double.MIN_VALUE){
            throw new Exception("Peso inválido.");
        }
        // cria o objeto Aresta e adiciona na matriz com o peso definido
        Aresta aresta = new Aresta(origem, destino, peso);
        matriz[aresta.origem().id()][aresta.destino().id()] = aresta.peso();
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino){
        return matriz[origem.id()][destino.id()] != Double.MAX_VALUE;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }

        int grau = 0;
        for (int j = 0; j < numVertices; j++) {
            if (matriz[vertice.id()][j] != Double.MAX_VALUE) {
                grau++;
            }
        }
        return grau;
    }

    @Override
    public int numeroDeVertices(){
        return numVertices;
    }

    @Override
    public int numeroDeArestas(){
        int count = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (matriz[i][j] != Double.MAX_VALUE) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }

        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (int j = 0; j < numVertices; j++) {
            if (matriz[vertice.id()][j] != Double.MAX_VALUE) {
                adjacentes.add(new Vertice(j));
            }
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        if(peso > Double.MAX_VALUE || peso < Double.MIN_VALUE){
            throw new Exception("Peso inválido.");
        }
        Aresta aresta = new Aresta(origem, destino, peso);
        matriz[aresta.origem().id()][aresta.destino().id()] = aresta.peso();
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Aresta> arestas = new ArrayList<>();
        if (matriz[origem.id()][destino.id()] != Double.MAX_VALUE) {
            Aresta aresta = new Aresta(origem, destino, matriz[origem.id()][destino.id()]);
            arestas.add(aresta);
        }

        if(matriz[destino.id()][origem.id()] != Double.MAX_VALUE){
            Aresta aresta = new Aresta(destino, origem, matriz[destino.id()][origem.id()]);
            arestas.add(aresta);
        }

        return arestas;
    }
    
    @Override
    public ArrayList<Vertice> vertices(){
        ArrayList<Vertice> vertices = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            vertices.add(new Vertice(i));
        }
        return vertices;
    }
}
