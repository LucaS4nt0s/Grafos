package trabalho;

import java.util.ArrayList;
import grafos.Grafo;
import grafos.Aresta;
import grafos.TipoDeRepresentacao;
import grafos.Vertice;

public class MatrizDeIncidencia implements Grafo {

    private final Aresta[][] matriz; // define a matriz de incidência do grafo como final e privada
    private final int numVertices; // define o número de vértices do grafo como final e privado
    private final int numArestas; // define o número de arestas do grafo como final e privado
    private int arestaAtual = 0; // contador para rastrear a próxima coluna disponível na matriz

    public MatrizDeIncidencia(int numVertices, int numArestas) { // construtor que inicializa a matriz de incidência
        this.numVertices = numVertices;  // armazena o número de vértices
        this.numArestas = numArestas; // armazena o número de arestas
        this.matriz = new Aresta[numVertices][numArestas]; // cria a matriz de incidência com o tamanho V x A

        // Inicializa a matriz com infinito
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numArestas; j++) {
                this.matriz[i][j] = null;
            }
        }
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception{
        adicionarAresta(origem, destino, 1.0);
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= this.numVertices || destino.id() < 0 || destino.id() >= this.numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }

        if (this.arestaAtual >= this.numArestas) {
            throw new Exception("Erro: Tentando adicionar mais arestas (" + (this.arestaAtual + 1) + ") do que o alocado (" + this.numArestas + ").");
        }
    
        Aresta aresta = new Aresta(origem, destino, peso); // cria o objeto Aresta com o peso definido
        this.matriz[aresta.origem().id()][this.arestaAtual] = aresta; // adiciona à linha do vertice de origem a aresta com peso definido, na coluna da aresta atual
        this.arestaAtual++; // incrementa o contador de arestas
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino){
        boolean existe = false; // inicializa a variável de existência da aresta como false
        for (int i = 0; i < this.numArestas; i++) {  // percorre todas as colunas da matriz
            if (this.matriz[destino.id()][i] != null && this.matriz[destino.id()][i].origem().id() == origem.id()) { // verifica se a aresta existe entre origem e destino
                existe = true; // se existir, atualiza a variável para true
                break;
            }
        }
        return existe;
    }

    @Override
    public int grauDoVertice(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= numVertices) { // caso o vértice não exista
            throw new Exception("Vértice não existe.");
        }

        int grau = 0; // inicializa o grau do vértice
        for (int i = 0; i < this.numArestas; i++) {  // percorre todas as colunas da matriz
            if (this.matriz[vertice.id()][i] != null) { // se existir aresta na linha incrementa o grau
                grau++;
            }
        }
        return grau;
    }

    @Override
    public int numeroDeVertices(){
        return this.numVertices;
    }

    @Override
    public int numeroDeArestas(){
        return this.numArestas;
    }

    @Override
    public ArrayList<Vertice> adjacentesDe(Vertice vertice) throws Exception{
        if (vertice.id() < 0 || vertice.id() >= this.numVertices) {
            throw new Exception("Vértice não existe.");
        }

        ArrayList<Vertice> adjacentes = new ArrayList<>(); // inicializa a lista de vértices adjacentes
        for (int i = 0; i < this.arestaAtual; i++) { // percorre todas as colunas da matriz
            if (this.matriz[vertice.id()][i] != null && this.matriz[vertice.id()][i].origem().id() == vertice.id()) {  // se existir aresta na linha do vértice
                adjacentes.add(this.matriz[vertice.id()][i].destino()); // adiciona o vértice destino na lista de adjacentes
            }
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= this.numVertices || destino.id() < 0 || destino.id() >= this.numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }
        
        boolean arestaEncontrada = false; // inicializa a variável de existência da aresta como false

        for (int i = 0; i < arestaAtual; i++) { 
            Aresta arestaExistente = matriz[origem.id()][i]; // recebe a aresta na linha do vértice de origem
            if (arestaExistente != null && arestaExistente.destino().id() == destino.id()) {  // verifica se a aresta existe entre origem e destino
                arestaExistente.setarPeso(peso); // atualiza o peso da aresta
                arestaEncontrada = true; // atualiza a variável para true
            }
        }
        if (!arestaEncontrada) {
            this.adicionarAresta(origem, destino, peso); // adiciona a aresta caso não tenha sido encontrada
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= this.numVertices || destino.id() < 0 || destino.id() >= this.numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Aresta> arestas = new ArrayList<>(); // inicializa a lista de arestas entre os vértices origem e destino
        for (int i = 0; i < this.arestaAtual; i++) {
            Aresta arestaExistente = this.matriz[origem.id()][i]; // recebe a aresta na linha do vértice de origem
           if (arestaExistente != null && arestaExistente.destino().id() == destino.id()) { // verifica se a aresta existe entre origem e destino
                arestas.add(arestaExistente);  // adiciona a aresta na lista de arestas entre os vértices
            }
        }
        return arestas;
    }
    
    @Override
    public ArrayList<Vertice> vertices(){
        ArrayList<Vertice> vertices = new ArrayList<>(); // inicializa a lista de vértices
        for (int i = 0; i < this.numVertices; i++) {  // percorre o número de vértices
            vertices.add(new Vertice(i)); // adiciona o vértice à lista
        }
        return vertices;
    }

    @Override
     public TipoDeRepresentacao tipoDeRepresentacao() {
        return TipoDeRepresentacao.MATRIZ_DE_INCIDENCIA; // retorna o tipo de representação do grafo
    }
}
