package trabalho;

import java.util.ArrayList;
import grafos.Grafo;
import grafos.Aresta;
import grafos.Vertice;

public class MatrizDeIncidencia implements Grafo {

    private final Aresta[][] matriz; // define a matriz de incidência do grafo como final e privada
    private final int numVertices; // define o número de vértices do grafo como final e privado
    private final int numArestas; // define o número de arestas do grafo como final e privado
    private int arestaAtual = 0; // contador para rastrear a próxima coluna disponível na matriz
    private final Aresta[][] matrizTransposta; // define a matriz transposta do grafo como final e privada

    public MatrizDeIncidencia(int numVertices, int numArestas) { // construtor que inicializa a matriz de incidência
        this.numVertices = numVertices;  // armazena o número de vértices
        this.numArestas = numArestas; // armazena o número de arestas
        matriz = new Aresta[numVertices][numArestas]; // cria a matriz de incidência com o tamanho V x A
        matrizTransposta = new Aresta[numArestas][numVertices]; // cria a matriz transposta com o tamanho A x V

        // Inicializa a matriz com infinito
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numArestas; j++) {
                matriz[i][j] = null;
            }
        }

        // Inicializa a matriz transposta com infinito
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numArestas; j++) {
                matrizTransposta[i][j] = null;
            }
        }
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }
            Aresta aresta = new Aresta(origem, destino, 1); // cria o objeto Aresta
            matriz[aresta.destino().id()][arestaAtual] = aresta; // adiciona à linha do vertice de destino a aresta como peso padrão 1, na coluna da aresta atual
            matriz[aresta.origem().id()][arestaAtual] = aresta; // adiciona à linha do vertice de origem a aresta como peso padrão 1, na coluna da aresta atual
            arestaAtual++; // incrementa o contador de arestas
    }

    @Override
    public void adicionarAresta(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }
    
        Aresta aresta = new Aresta(origem, destino, peso); // cria o objeto Aresta com o peso definido
        matriz[aresta.origem().id()][arestaAtual] = aresta; // adiciona à linha do vertice de origem a aresta com peso definido, na coluna da aresta atual
        matriz[aresta.destino().id()][arestaAtual] = aresta; // adiciona à linha do vertice de destino a aresta com o peso definido, na coluna da aresta atual
        arestaAtual++; // incrementa o contador de arestas
    }

    @Override
    public boolean existeAresta(Vertice origem, Vertice destino){
        boolean existe = false; // inicializa a variável de existência da aresta como false
        for (int i = 0; i < numArestas; i++) {  // percorre todas as colunas da matriz
            if (matriz[destino.id()][i] != null && matriz[destino.id()][i].origem().id() == origem.id()) { // verifica se a aresta existe entre origem e destino
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
        for (int i = 0; i < numArestas; i++) {  // percorre todas as colunas da matriz
            if (matriz[vertice.id()][i] != null) { // se existir aresta na linha incrementa o grau
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

        ArrayList<Vertice> adjacentes = new ArrayList<>(); // inicializa a lista de vértices adjacentes
        for (int i = 0; i < numArestas; i++) { // percorre todas as colunas da matriz
            if (matriz[vertice.id()][i] != null && matriz[vertice.id()][i].origem().id() == vertice.id()) { // se existir aresta na linha
                adjacentes.add(matriz[vertice.id()][i].destino()); // adiciona o vértice de destino à lista de adjacentes
            }
        }
        return adjacentes;
    }

    @Override
    public void setarPeso(Vertice origem, Vertice destino, double peso) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }
        
        Aresta aresta = new Aresta(origem, destino, peso); // cria o objeto Aresta com o peso definido
        for (int i = 0; i < numArestas; i++) { // percorre todas as colunas da matriz
            if (matriz[destino.id()][i] != null && matriz[destino.id()][i].origem().id() == origem.id()) { // se encontrar a aresta entre origem e destino
                matriz[destino.id()][i] = aresta; // atualiza o peso da aresta na linha do destino
                matriz[origem.id()][i] = aresta; // atualiza o peso da aresta na linha da origem
                break;
            }
        }
    }

    @Override
    public ArrayList<Aresta> arestasEntre(Vertice origem, Vertice destino) throws Exception{
        if (origem.id() < 0 || origem.id() >= numVertices || destino.id() < 0 || destino.id() >= numVertices) { // caso os vértices não existam
            throw new Exception("Vértice não existe.");
        }
        ArrayList<Aresta> arestas = new ArrayList<>(); // inicializa a lista de arestas entre os vértices origem e destino
        for (int i = 0; i < numArestas; i++) {
            if (matriz[origem.id()][i] != null && matriz[origem.id()][i].destino().id() == destino.id()) { // se existir aresta de origem para destino
                Aresta aresta = new Aresta(origem, destino, matriz[origem.id()][i].peso());  // cria o objeto Aresta com o peso definido
                arestas.add(aresta); // adiciona a aresta na lista

                // Aresta aresta2 = new Aresta(destino, origem, matriz[origem.id()][i].peso());
                // arestas.add(aresta2);
            }
        }
        return arestas;
    }
    
    @Override
    public ArrayList<Vertice> vertices(){
        ArrayList<Vertice> vertices = new ArrayList<>(); // inicializa a lista de vértices
        for (int i = 0; i < numVertices; i++) {  // percorre o número de vértices
            vertices.add(new Vertice(i)); // adiciona o vértice à lista
        }
        return vertices;
    }
}
