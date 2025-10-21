package trabalho;

import java.util.ArrayList;
import grafos.Grafo;
import grafos.Aresta;
import grafos.Vertice;

public class MatrizDeIncidencia implements Grafo {

    private final double [][] matriz;
    private final int numVertices;
    private final int numArestas;
    private int arestaAtual = 0;

    public MatrizDeIncidencia(int numVertices, int numArestas) {
        this.numVertices = numVertices;
        this.numArestas = numArestas;
        matriz = new double[numVertices][numArestas];

        // Inicializa a matriz com infinito
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numArestas; j++) {
                matriz[i][j] = Double.MAX_VALUE;
            }
        }
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
            Aresta aresta = new Aresta(origem, destino);
            matriz[arestaAtual][aresta.destino().id()] = 1;
            matriz[arestaAtual][aresta.origem().id()] = -1;
            arestaAtual++;
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        if(peso > Double.MAX_VALUE || peso < Double.MIN_VALUE){
            throw new Exception("Peso inválido.");
        }
        Aresta aresta = new Aresta(origem, destino, peso);
        matriz[arestaAtual][aresta.destino().id()] = peso;
        matriz[arestaAtual][aresta.origem().id()] = -peso;
        arestaAtual++;
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino){
        boolean existe = false;
        for (int i = 0; i < numArestas; i++) {
            if (matriz[origem.id()][i] != Double.MAX_VALUE && matriz[destino.id()][i] != Double.MAX_VALUE) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }

        int grau = 0;
        for (int i = 0; i < numArestas; i++) {
            if (matriz[vertice.id()][i] != Double.MAX_VALUE) {
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
        return numArestas;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }

        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (int i = 0; i < numArestas; i++) {
            if (matriz[vertice.id()][i] != Double.MAX_VALUE) {
                adjacentes.add(new Vertice(i));
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
        for (int i = 0; i < numArestas; i++) {
            if (matriz[origem.id()][i] != Double.MAX_VALUE && matriz[destino.id()][i] != Double.MAX_VALUE) {
                matriz[origem.id()][i] = -peso;
                matriz[destino.id()][i] = peso;
                break;
            }
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Aresta> arestas = new ArrayList<>();
        for (int i = 0; i < numArestas; i++) {
            if (matriz[origem.id()][i] != Double.MAX_VALUE && matriz[destino.id()][i] != Double.MAX_VALUE) {
                Aresta aresta = new Aresta(origem, destino, matriz[destino.id()][i]);
                arestas.add(aresta);
            }
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
