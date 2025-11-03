package trabalho;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.TipoDeRepresentacao;
import grafos.Vertice;

public class ListaDeAdjacencia implements Grafo{

    private final ArrayList<ArrayList<Aresta>> adjacencias; // lista de adjacências representando o grafo
    private final int numVertices; // número de vértices no grafo

    public ListaDeAdjacencia(int numeroDeVertices) { 
        this.numVertices = numeroDeVertices; // inicializa o número de vértices
        adjacencias = new ArrayList<>(numVertices); // cria a lista de adjacências
        for (int i = 0; i < numVertices; i++) { 
            adjacencias.add(new ArrayList<>());  // inicializa a lista de adjacências para cada vértice
        }
    }
    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe."); // lança uma exceção
        }
        Aresta aresta = new Aresta(origem, destino); // cria o objeto Aresta sem peso definido
        adjacencias.get(origem.id()).add(aresta); // adiciona a aresta na lista de adjacências do vértice de origem
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe."); 
        }
        Aresta aresta = new Aresta(origem, destino, peso); // cria o objeto Aresta com o peso definido
        adjacencias.get(origem.id()).add(aresta); // adiciona a aresta na lista de adjacências do vértice de origem
    }
    

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino){
        for (Aresta aresta : adjacencias.get(origem.id())) { // percorre a lista de adjacências do vértice de origem
            if (aresta.destino().id() == destino.id()) { // se encontrar a aresta para o vértice de destino
                return true; // retorna true
            }
        }
        return false;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) { // caso o vértice não exista
            throw new Exception("Vértice não existe."); // lança uma exceção
        }
        return adjacencias.get(vertice.id()).size(); // retorna o tamanho da lista de adjacências do vértice
    }

    @Override
    public int numeroDeVertices(){
        return numVertices;
    }

    @Override
    public int numeroDeArestas(){
        int count = 0;
        for (ArrayList<Aresta> lista : adjacencias) { // percorre todas as listas de adjacências
            count += lista.size(); // soma o tamanho de cada lista ao contador
        }
        return count;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) { // caso o vértice não exista
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Vertice> adjacentes = new ArrayList<>(); // inicializa a lista de vértices adjacentes
        for (Aresta aresta : adjacencias.get(vertice.id())) { // percorre a lista de adjacências do vértice
            adjacentes.add(aresta.destino()); // adiciona o vértice de destino à lista de adjacentes
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }
        for (Aresta aresta : adjacencias.get(origem.id())) { // percorre a lista de adjacências do vértice de origem
            if (aresta.destino().id() == destino.id()) { // se encontrar a aresta para o vértice de destino
                aresta.setarPeso(peso); // seta o peso da aresta
                return;
            }
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) {
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Aresta> arestas = new ArrayList<>(); // inicializa a lista de arestas entre os vértices origem e destino
        for (Aresta aresta : adjacencias.get(origem.id())) { // percorre a lista de adjacências do vértice de origem
            if (aresta.destino().id() == destino.id()) { // se existir aresta de origem para destino
                arestas.add(aresta); // adiciona a aresta na lista
            }
        }
        return arestas;
    }
    
    @Override
    public ArrayList<Vertice> vertices(){
        ArrayList<Vertice> vertices = new ArrayList<>(); // inicializa a lista de vértices
        for (int i = 0; i < numVertices; i++) { // percorre o número de vértices
            vertices.add(new Vertice(i)); // adiciona o vértice na lista
        }
        return vertices;
    }

    @Override
    public TipoDeRepresentacao tipoDeRepresentacao() {
        return TipoDeRepresentacao.LISTA_DE_ADJACENCIA; // retorna o tipo de representação do grafo
    }
}
