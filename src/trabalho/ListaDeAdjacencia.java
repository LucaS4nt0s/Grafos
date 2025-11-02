package trabalho;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.TipoDeRepresentacao;
import grafos.Vertice;

public class ListaDeAdjacencia implements Grafo{

    private final ArrayList<ArrayList<Aresta>> adjacencias;
    private final int numVertices;

    public ListaDeAdjacencia(int numeroDeVertices) {
        this.numVertices = numeroDeVertices;
        adjacencias = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            adjacencias.add(new ArrayList<>());
        }
    }
    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        Aresta aresta = new Aresta(origem, destino);
        adjacencias.get(origem.id()).add(aresta);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        if (peso < 0) {
            throw new Exception("Peso inválido.");
        }
        Aresta aresta = new Aresta(origem, destino, peso);
        adjacencias.get(origem.id()).add(aresta);
    }
    

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino){
        for (Aresta aresta : adjacencias.get(origem.id())) {
            if (aresta.destino().equals(destino)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        return adjacencias.get(vertice.id()).size();
    }

    @Override
    public int numeroDeVertices(){
        return numVertices;
    }

    @Override
    public int numeroDeArestas(){
        int count = 0;
        for (ArrayList<Aresta> lista : adjacencias) {
            count += lista.size();
        }
        return count;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Vertice> adjacentes = new ArrayList<>();
        for (Aresta aresta : adjacencias.get(vertice.id())) {
            adjacentes.add(aresta.destino());
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        if (peso < 0) {
            throw new Exception("Peso inválido.");
        }
        for (Aresta aresta : adjacencias.get(origem.id())) {
            if (aresta.destino().equals(destino)) {
                aresta.setarPeso(peso);
                return;
            }
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Aresta> arestas = new ArrayList<>();
        for (Aresta aresta : adjacencias.get(origem.id())) {
            if (aresta.destino().equals(destino)) {
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

    @Override
    public TipoDeRepresentacao tipoDeRepresentacao() {
        return TipoDeRepresentacao.LISTA_DE_ADJACENCIA; // retorna o tipo de representação do grafo
    }
}
