package trabalho;

import java.util.Scanner;
import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;
import grafos.TipoDeRepresentacao;

public class Main {
    public static void main(String[] args) {
        Algoritmos algoritmos = new Algoritmos();
        Grafo grafo = null;
        int opcao = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Para começar coloque o Grafo na pasta CarregarGrafo e nomeie o arquivo como Grafo.txt");
        System.out.println("O grafo deve estar no seguinte formato:");
        System.out.println("Primeira linha: número de vértices");
        System.out.println("Demais linhas: vertice de origem com as arestas e pesos para os vértices de destino separados por espaço e ';'");
        System.out.println("Exemplo:");
        System.out.println("3");
        System.out.println("0 1-10; 2-10;");
        System.out.println("1 0-10; 1-10;");
        System.out.println("2 0-10; 1-10;");
        System.out.println("");
        System.out.println("==========================================================");
        System.out.println("");
       
        
        while (opcao == 0) {
            System.out.println("1 - Matriz de Adjacência");
            System.out.println("2 - Matriz de Incidência");
            System.out.println("3 - Lista de Adjacência");
            System.out.println("Escolha a representação do grafo:");
            opcao = scanner.nextInt();
            switch (opcao) {
                case 1 -> {
                    try {
                        grafo = algoritmos.carregarGrafo("CarregarGrafo/Grafo.txt", TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA);
                        System.out.println("Grafo carregado com sucesso usando Matriz de Adjacência!");
                        break;
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar o grafo usando Matriz de Adjacência: " + e.getMessage());
                    }
                }
                case 2 -> {
                    try {
                        grafo = algoritmos.carregarGrafo("CarregarGrafo/Grafo.txt", TipoDeRepresentacao.MATRIZ_DE_INCIDENCIA);
                        System.out.println("Grafo carregado com sucesso usando Matriz de Incidência!");
                        break;
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar o grafo usando Matriz de Incidência: " + e.getMessage());
                    }
                }
                case 3 -> {
                    try {
                        grafo = algoritmos.carregarGrafo("CarregarGrafo/Grafo.txt", TipoDeRepresentacao.LISTA_DE_ADJACENCIA);
                        System.out.println("Grafo carregado com sucesso usando Lista de Adjacência!");
                        break;
                    } catch (Exception e) {
                        System.out.println("Erro ao carregar o grafo usando Lista de Adjacência: " + e.getMessage());
                    }
                }
                default -> {
                    System.out.println("Opção inválida. Tente novamente."); 
                    opcao = 0;
                }
            }
        }

        if(grafo == null){
            System.out.println("Não foi possível carregar o grafo. Encerrando o programa.");
            scanner.close();
        }else{
            System.out.println("Quantidade de vértices: " + grafo.numeroDeVertices());
            System.out.println("Quantidade de arestas: " + grafo.numeroDeArestas());
            System.out.println("");
            System.out.println("==========================================================");
            System.out.println("");
        }
        
        while(true){
            System.out.println("Escolha o algoritmo a ser rodado:");
            System.out.println("1 - Busca em Profundidade (DFS)");
            System.out.println("2 - Busca em Largura (BFS)");
            System.out.println("3 - Existe ciclo?");
            System.out.println("4 - Componentes Fortemente Conexos");
            System.out.println("5 - Arvore Geradora Mínima (Kruskal)");
            System.out.println("6 - Caminho Mínimo (Dijkstra)");
            System.out.println("7 - Fluxo Máximo");
            System.out.println("8 - Sair");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1 -> {
                    ArrayList<Aresta> resultado = new ArrayList<>(algoritmos.buscaEmProfundidade(grafo));
                    System.out.println("Arestas da Busca em Profundidade (DFS):");
                    for (Aresta aresta : resultado) {
                        System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id() + " | Peso: " + aresta.peso());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");
                    System.out.println("Arestas de retorno:");
                    for (Aresta aresta : algoritmos.arestasDeRetorno(grafo)) {
                        System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id() + " | Peso: " + aresta.peso());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");
                    System.out.println("Arestas de avanço:");
                    for (Aresta aresta : algoritmos.arestasDeAvanco(grafo)) {
                        System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id() + " | Peso: " + aresta.peso());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");
                    System.out.println("Arestas de cruzamento:");
                    for (Aresta aresta : algoritmos.arestasDeCruzamento(grafo)) {
                        System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id() + " | Peso: " + aresta.peso());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");
                }
                case 2 -> {
                    System.out.println("Para a Busca em Largura (BFS), insira o vértice inicial:");
                    System.out.println("Vértices disponíveis:");
                    for (Vertice vertice : grafo.vertices()) {
                        System.out.print(vertice.id() + " ");
                    }
                    int verticeInicial = scanner.nextInt();
                    if (verticeInicial < 0 || verticeInicial >= grafo.numeroDeVertices()) {
                        System.out.println("Vértice inválido. Tente novamente.");
                        break;
                    }
                    ArrayList<Aresta> resultado = new ArrayList<>(algoritmos.buscaEmLargura(grafo, new Vertice(verticeInicial)));
                    System.out.println("Arestas da Busca em Largura (BFS):");
                    for (Aresta aresta : resultado) {
                        System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id() + " | Peso: " + aresta.peso());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");
                }
                case 3 -> {
                    System.out.println("O grafo " + (algoritmos.existeCiclo(grafo) ? "possui pelo menos um" : "não possui") + " ciclo.");
                }
                case 4 -> {
                    Grafo componentes = algoritmos.componentesFortementeConexos(grafo);
                    System.out.println("Componentes Fortemente Conexos encontrados:");   
                    System.out.println("Número de componentes: " + componentes.numeroDeVertices());
                    System.out.println("");
                    System.out.println("Vértices: ");
                    for (Vertice vertice : componentes.vertices()) {
                        System.out.println(vertice.id());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");
                    System.out.println("Arestas dos Componentes Fortemente Conexos:");
                    for (Vertice vertice : componentes.vertices()) {
                        try{
                            ArrayList<Vertice> adjacentes = componentes.adjacentesDe(vertice);
                            for (Vertice adj : adjacentes){
                                ArrayList<Aresta> arestas = componentes.arestasEntre(vertice, adj);
                                for (Aresta aresta : arestas){
                                    System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id());
                                }
                            }
                        }catch (Exception e){
                            System.out.println("Erro ao obter adjacentes do vértice " + vertice.id() + ": " + e.getMessage());
                        }
                    }
                    if(componentes.numeroDeVertices() == 1){
                        System.out.println("O grafo original é fortemente conexo.");
                    }else{
                        System.out.println("O grafo original não é fortemente conexo.");
                    }
                }
                case 5 -> {
                    ArrayList<Aresta> resultado = new ArrayList<>(algoritmos.arvoreGeradoraMinima(grafo));
                    if(resultado.isEmpty()){
                        System.out.println("O grafo não possui árvore geradora mínima.");
                        break;
                    }
                    System.out.println("Arestas da Árvore Geradora Mínima (Kruskal):");
                    for (Aresta aresta : resultado) {
                        System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id() + " | Peso: " + aresta.peso());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");

                    try {
                        double custo = algoritmos.custoDaArvoreGeradora(grafo, algoritmos.arvoreGeradoraMinima(grafo));
                        if(custo == Double.MAX_VALUE){
                            System.out.println("O grafo não possui árvore geradora mínima.");
                        }else{
                            System.out.println("Custo da Árvore Geradora Mínima: " + custo);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao calcular o custo da árvore geradora mínima: " + e.getMessage());
                        break;
                    }
                }
                case 6 -> {
                    System.out.println("Para o cálculo do caminho mínimo, insira o vértice de origem:");
                    System.out.println("Vértices disponíveis:");
                    for (Vertice vertice : grafo.vertices()) {
                        System.out.print(vertice.id() + " ");
                    }
                    int verticeInicial = scanner.nextInt();
                    if (verticeInicial < 0 || verticeInicial >= grafo.numeroDeVertices()) {
                        System.out.println("Vértice inválido. Tente novamente.");
                        break;
                    }

                    System.out.println("Insira o vértice de destino:");
                    for (Vertice vertice : grafo.vertices()) {
                        System.out.print(vertice.id() + " ");
                    }
                    int verticeDestino = scanner.nextInt();
                    if (verticeDestino < 0 || verticeDestino >= grafo.numeroDeVertices()) {
                        System.out.println("Vértice inválido. Tente novamente.");
                        break;
                    }

                    ArrayList<Aresta> resultado = new ArrayList<>(algoritmos.caminhoMinimo(grafo, new Vertice(verticeInicial), new Vertice(verticeDestino)));
                    if(resultado.isEmpty()){
                        System.out.println("Não existe caminho entre os vértices " + verticeInicial + " e " + verticeDestino + ".");
                        break;
                    }
                    System.out.println("Arestas do Caminho Mínimo (Dijkstra) entre os vértices " + verticeInicial + " e " + verticeDestino + ":");
                    for (Aresta aresta : resultado) {
                        System.out.println("Origem: " + aresta.origem().id() + " -> Destino: " + aresta.destino().id() + " | Peso: " + aresta.peso());
                    }
                    System.out.println("");
                    System.out.println("==========================================================");
                    System.out.println("");
                    try {
                        System.out.println("Custo total do caminho mínimo: " + algoritmos.custoDoCaminhoMinimo(grafo, resultado, new Vertice(verticeInicial), new Vertice(verticeDestino)));
                    } catch (Exception e) {
                        System.out.println("Erro ao calcular o custo do caminho mínimo: " + e.getMessage());
                    }
                }
                case 7 -> {
                    System.out.println("Para o cálculo do fluxo máximo, insira o vértice de origem:");
                    System.out.println("Vértices disponíveis:");
                    for (Vertice vertice : grafo.vertices()) {
                        System.out.print(vertice.id() + " ");
                    }
                    int verticeInicial = scanner.nextInt();
                    if (verticeInicial < 0 || verticeInicial >= grafo.numeroDeVertices()) {
                        System.out.println("Vértice inválido. Tente novamente.");
                        break;
                    }

                    System.out.println("Insira o vértice de destino:");
                    for (Vertice vertice : grafo.vertices()) {
                        System.out.print(vertice.id() + " ");
                    }
                    int verticeDestino = scanner.nextInt();
                    if (verticeDestino < 0 || verticeDestino >= grafo.numeroDeVertices()) {
                        System.out.println("Vértice inválido. Tente novamente.");
                        break;
                    }

                    double custoMaximo = algoritmos.fluxoMaximo(grafo, new Vertice(verticeInicial), new Vertice(verticeDestino));
                    System.out.println("Fluxo Máximo entre os vértices " + verticeInicial + " e " + verticeDestino + ": " + custoMaximo);
                }
                case 8 -> {
                    System.out.println("Encerrando o programa.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}