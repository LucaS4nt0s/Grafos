package trabalho;

import java.util.ArrayList;
import java.util.Collection;
import grafos.AlgoritmosEmGrafos;
import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;
import grafos.TipoDeRepresentacao;
import grafos.FileManager;
import java.lang.reflect.Array;
import java.util.Queue;
import java.util.LinkedList;
    
public class Algoritmos implements AlgoritmosEmGrafos {
    
    private Grafo grafo; // cria uma variável do tipo Grafo para armazenar o grafo carregado
    
    private enum Cor{ // enum para representar as cores dos vértices durante as buscas
        BRANCO, // vértice não visitado
        CINZA, // vértice em exploração
        PRETO // vértice já finalizado
    }
    private int[] distanciaDFS; // armazena os tempos de descoberta dos vértices na busca em profundidade
    private int tempoAtual; // armazena o tempo atual durante a busca em profundidade
    private Cor[] corDFS; // armazena as cores dos vértices na busca em profundidade
    private Cor[] corBFS; // armazena as cores dos vértices na busca em largura
    private Vertice[] paiBFS; // armazena os pais dos vértices na busca em largura
    private int[] distanciaBFS; // armazena os tempos de descoberta dos vértices na busca em largura
    private Queue<Vertice> filaBFS; // fila para a busca em largura
    private Collection<Aresta> arestasDeArvore; // arestas que pertencem à árvore geradora
    private Collection<Aresta> arestasDeRetorno; // arestas que retornam a um vértice ancestral
    private Collection<Aresta> arestasDeAvanco; // arestas que avançam para um vértice descendente
    private Collection<Aresta> arestasDeCruzamento; // arestas que cruzam entre diferentes subárvores
    private Collection<Aresta> arestasDaBuscaEmLargura; // arestas que pertencem à busca em largura
    private Collection<ArrayList<Vertice>> conjuntoVerticesAGM; // conjunto de vértices da árvore geradora mínima
    private Collection<Aresta> arestasOrdenadasAGM; // conjunto ordenado de arestas da árvore geradora mínima

    @Override 
    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
        FileManager fileManager = new FileManager(); // cria um objeto FileManager para ler o arquivo

        ArrayList<String> conteudo = fileManager.stringReader(path); // lê o conteúdo do arquivo e armazena em uma lista de strings
 
        int numeroDeVertices = Integer.parseInt(conteudo.get(0)); // o primeiro elemento da lista é o número de vértices
        int numeroDeArestas = 0; // inicializa o número de arestas
        for (int i = 1; i < conteudo.size(); i++) { // percorre o restante da lista para contar as arestas
            String[] partes = conteudo.get(i).split(" "); // divide a linha onde há espaços
            numeroDeArestas += partes.length - 1; // o número de arestas é o total de partes menos 1 (o primeiro elemento é o vértice origem)
        }

        ArrayList<Integer> destinosList = new ArrayList<>(numeroDeArestas); // lista para armazenar os vértices destino
        ArrayList<Integer> origensList = new ArrayList<>(numeroDeArestas); // lista para armazenar os vértices origem
        ArrayList<Double> pesosList = new ArrayList<>(numeroDeArestas); // lista para armazenar os pesos das arestas

        for (int j = 1; j < conteudo.size(); j++) { // percorre o conteúdo do arquivo a partir da segunda linha
            String[] partes = conteudo.get(j).split(" "); // divide a linha onde há espaços
            for (int k = 1; k < partes.length; k++) { // percorre as partes a partir do segundo elemento
                String[] subpartes = partes[k].split("-"); // divide a parte onde há hífen
                String[] pesos = subpartes[1].split(";"); // divide a parte do peso onde há ponto e vírgula
                destinosList.add(Integer.valueOf(subpartes[0])); // adiciona o vértice destino à lista
                origensList.add(Integer.valueOf(partes[0])); // adiciona o vértice origem à lista
                pesosList.add(Double.valueOf(pesos[0])); // adiciona o peso à lista
            }
        }

        switch (t) { // cria o grafo conforme o tipo de representação escolhido
            case MATRIZ_DE_ADJACENCIA -> { 
                grafo = new MatrizDeAdjacencia(numeroDeVertices); // cria o grafo como matriz de adjacência
                for (int i = 0; i < destinosList.size(); i++) {  // percorre a lista de destinos
                    if (pesosList.get(i) == 1.0) {  // se o peso for 1.0, adiciona a aresta sem peso
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i))); // adiciona a aresta ao grafo com peso padrão 1
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i)); // adiciona a aresta ao grafo com o peso definido
                    }
                }
            }
            case MATRIZ_DE_INCIDENCIA -> {
                grafo = new MatrizDeIncidencia(numeroDeVertices, numeroDeArestas); // cria o grafo como matriz de incidência
                for (int i = 0; i < destinosList.size(); i++) {  // percorre a lista de destinos
                    if (pesosList.get(i) == 1.0) { // se o peso for 1.0, adiciona a aresta sem peso
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i))); // adiciona a aresta ao grafo com peso padrão 1
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i)); // adiciona a aresta ao grafo com o peso definido
                    }
                }
            }
            case LISTA_DE_ADJACENCIA -> {
                grafo = new ListaDeAdjacencia(numeroDeVertices); // cria o grafo como lista de adjacência
                for (int i = 0; i < destinosList.size(); i++) { // percorre a lista de destinos
                    if (pesosList.get(i) == 1.0) { // se o peso for 1.0, adiciona a aresta sem peso
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i))); // adiciona a aresta ao grafo com peso padrão 1
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i)); // adiciona a aresta ao grafo com o peso definido
                    }
                }
            }
        }
        return grafo; 
    }

    @Override
    public Collection<Aresta> buscaEmProfundidade(Grafo g) {
        for (Vertice u : grafo.vertices()) { // para cada vértice do grafo 
            corDFS[u.id()] = Cor.BRANCO; // inicializa a cor como branco
        }
        tempoAtual = 0; // inicializa o tempo atual como 0

        arestasDeArvore = new ArrayList<>(); // inicializa a coleção de arestas de árvore
        arestasDeRetorno = new ArrayList<>(); // inicializa a coleção de arestas de retorno
        arestasDeAvanco = new ArrayList<>(); // inicializa a coleção de arestas de avanço
        arestasDeCruzamento = new ArrayList<>(); // inicializa a coleção de arestas de cruzamento

        for (Vertice u : grafo.vertices()) { // para cada vértice do grafo
            if (corDFS[u.id()] == Cor.BRANCO) { // se a cor for branco, inicia a visita
                buscaEmProfundidadeVisitar(u, grafo); // chama o método de visita
            }
        }
        
        return arestasDeArvore; // retorna as arestas de árvore como resultado da busca em profundidade
    }

    private void buscaEmProfundidadeVisitar(Vertice u, Grafo grafo) { // função complementar da DFS para visitar os vértices   
        corDFS[u.id()] = Cor.CINZA; // marca o vértice como em exploração
        tempoAtual++; // incrementa o tempo atual
        distanciaDFS[u.id()] = tempoAtual; // define o tempo de descoberta do vértice

        try { // tenta percorrer os vértices adjacentes (tratamento de exceção necessário para a função adjacentesDe)
            for (Vertice v : grafo.adjacentesDe(u)){ // para cada vértice adjacente
                switch (corDFS[v.id()]) { // verifica a cor do vértice adjacente
                    case BRANCO -> { // se a cor for branco, é uma aresta de árvore
                        buscaEmProfundidadeVisitar(v, grafo); // chama recursivamente a função para visitar o vértice adjacente
                        Aresta arestaDeArvore = new Aresta(u, v); // cria a aresta de árvore
                        arestasDeArvore.add(arestaDeArvore); // adiciona a aresta de árvore à coleção
                    }
                    case CINZA -> { // se a cor for cinza, é uma aresta de retorno
                        Aresta arestaDeRetorno = new Aresta(u, v); // cria a aresta de retorno
                        arestasDeRetorno.add(arestaDeRetorno); // adiciona a aresta de retorno à coleção
                    }
                    case PRETO -> { // se a cor for preto, pode ser aresta de avanço ou de cruzamento
                        if ((distanciaDFS[u.id()] < distanciaDFS[v.id()])) { // se o tempo de descoberta de u for menor que o de v, é uma aresta de avanço
                            Aresta arestaDeAvanco = new Aresta(u, v); // cria a aresta de avanço
                            arestasDeAvanco.add(arestaDeAvanco); // adiciona a aresta de avanço à coleção
                        } else { // caso contrário, é uma aresta de cruzamento
                            Aresta arestaDeCruzamento = new Aresta(u, v); // cria a aresta de cruzamento
                            arestasDeCruzamento.add(arestaDeCruzamento); // adiciona a aresta de cruzamento à coleção
                        }
                    }
                    default -> throw new AssertionError(); 
                }
            }
        } catch (Exception e) {
            e.getMessage(); // captura a exceção se os vértices adjacentes não puderem ser acessados
        }

        corDFS[u.id()] = Cor.PRETO; // marca o vértice como finalizado
    }

    @Override
    public Collection<Aresta> arestasDeArvore(Grafo g) { 
        if (arestasDeArvore == null) { // se as arestas de árvore ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return arestasDeArvore; 
    }

    @Override
    public Collection<Aresta> arestasDeRetorno(Grafo g) {
        if (arestasDeRetorno == null) { // se as arestas de retorno ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return arestasDeRetorno;
    }

    @Override
    public Collection<Aresta> arestasDeAvanco(Grafo g) {
        if (arestasDeAvanco == null) { // se as arestas de avanço ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return arestasDeAvanco;
    }

    @Override
    public Collection<Aresta> arestasDeCruzamento(Grafo g) {
        if (arestasDeCruzamento == null) { // se as arestas de cruzamento ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return arestasDeCruzamento;
    }

    @Override
    public Collection<Aresta> buscaEmLargura(Grafo g, Vertice origem) {
        for (Vertice u : grafo.vertices()) { // para cada vértice do grafo
            corBFS[u.id()] = Cor.BRANCO; // inicializa a cor como branco
            distanciaBFS[u.id()] = Integer.MAX_VALUE; // inicializa a distância como infinito
            paiBFS[u.id()] = null; // inicializa o pai como null
        }

        corBFS[origem.id()] = Cor.CINZA; // marca o vértice origem como em exploração
        distanciaBFS[origem.id()] = 0; // define a distância do vértice origem como 0
        paiBFS[origem.id()] = null; // o pai do vértice origem é null

        filaBFS = new LinkedList<>(); // inicializa a fila para a busca em largura
        filaBFS.add(origem); // adiciona o vértice origem à fila

        arestasDaBuscaEmLargura = new ArrayList<>(); // inicializa a coleção de arestas da busca em largura

        while(!filaBFS.isEmpty()){ // enquanto a fila não estiver vazia
            Vertice u = filaBFS.poll(); // remove o vértice da frente da fila
            try { // tenta percorrer os vértices adjacentes (tratamento de exceção necessário para a função adjacentesDe)
                for (Vertice v : grafo.adjacentesDe(u)){ // para cada vértice adjacente
                    if(corBFS[v.id()] == Cor.BRANCO) { // se a cor for branco
                        corBFS[v.id()] = Cor.CINZA; // marca o vértice como em exploração
                        distanciaBFS[v.id()] = distanciaBFS[u.id()] + 1; // define a distância do vértice adjacente
                        paiBFS[v.id()] = u; // define o pai do vértice adjacente
                        filaBFS.add(v); // adiciona o vértice adjacente à fila
                        arestasDaBuscaEmLargura.add(new Aresta(u, v)); // adiciona a aresta à coleção de arestas da busca em largura
                    }
                }
            } catch (Exception e) {
                e.getMessage(); // captura a exceção se os vértices adjacentes não puderem ser acessados
            }
            corBFS[u.id()] = Cor.PRETO; // marca o vértice como finalizado
        }

        return arestasDaBuscaEmLargura; 
    }

    @Override 
    public boolean existeCiclo(Grafo g) { 
        if (arestasDeRetorno == null) { // se as arestas de retorno ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return !arestasDeRetorno.isEmpty(); // se houver arestas de retorno, existe ciclo
    }

    @Override
    public Grafo componentesFortementeConexos (Grafo g) {
        return null;
    }

    private Collection<Aresta> todasAsArestas(Grafo g){
        Collection<Aresta> arestas = new ArrayList<>();
        for (Vertice u : g.vertices()) {
            try {
                for (Vertice v : g.adjacentesDe(u)) {
                    ArrayList<Aresta> arestasEntre = g.arestasEntre(u, v);
                    arestas.addAll(arestasEntre);
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return arestas;
    }

    @Override
    public Collection<Aresta> arvoreGeradoraMinima(Grafo g) {
        arestasOrdenadasAGM = new ArrayList<>(); // lista para armazenar as arestas da árvore geradora mínima
        conjuntoVerticesAGM = new ArrayList<>(); // lista para armazenar lista dos vértices da árvore geradora mínima

        for (Vertice v : g.vertices()) { // para cada vértice do grafo
            ArrayList<Vertice> novoConjunto = new ArrayList<>(); // cria um novo conjunto de vértices
            novoConjunto.add(v); // adiciona o vértice ao novo conjunto
            conjuntoVerticesAGM.add(novoConjunto); // adiciona o novo conjunto à coleção de conjuntos
        }

        arestasOrdenadasAGM = todasAsArestas(g); // armazena todas as arestas do grafo na lista
        arestasOrdenadasAGM.stream().sorted((a1, a2) -> Double.compare(a1.peso(), a2.peso())); // ordena as arestas pelo peso

        return arestasOrdenadasAGM;
    }

    @Override
    public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {
        return 0;
    }

    @Override
    public ArrayList<Aresta> caminhoMinimo(Grafo g, Vertice origem, Vertice destino ) {
        return null;
    }
    
    @Override
    public double custoDoCaminhoMinimo (Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino ) throws Exception {
        return 0;
    }

    @Override
    public double fluxoMaximo (Grafo g) {
        return 0;
    }
}
