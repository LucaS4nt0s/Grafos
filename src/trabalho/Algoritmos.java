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

    private int[] distanciaDijkstra; // armazena as distâncias mínimas dos vértices na busca de Dijkstra
    private Vertice[] paiDijkstra; // armazena os pais dos vértices na busca de Dijkstra

    @Override 
    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
        FileManager fileManager = new FileManager(); // cria um objeto FileManager para ler o arquivo

        ArrayList<String> conteudo = fileManager.stringReader(path); // lê o conteúdo do arquivo e armazena em uma lista de strings
        
        if (conteudo.isEmpty()) { // verifica se o arquivo está vazio
            throw new Exception("Caminho inválido ou arquivo vazio."); // lança uma exceção se o arquivo estiver vazio
        }

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
                this.grafo = new MatrizDeAdjacencia(numeroDeVertices); // cria o grafo como matriz de adjacência
                for (int i = 0; i < destinosList.size(); i++) {  // percorre a lista de destinos
                    if (pesosList.get(i) == 1.0) {  // se o peso for 1.0, adiciona a aresta sem peso
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i))); // adiciona a aresta ao grafo com peso padrão 1
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i)); // adiciona a aresta ao grafo com o peso definido
                    }
                }
            }
            case MATRIZ_DE_INCIDENCIA -> {
                this.grafo = new MatrizDeIncidencia(numeroDeVertices, numeroDeArestas); // cria o grafo como matriz de incidência
                for (int i = 0; i < destinosList.size(); i++) {  // percorre a lista de destinos
                    if (pesosList.get(i) == 1.0) { // se o peso for 1.0, adiciona a aresta sem peso
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i))); // adiciona a aresta ao grafo com peso padrão 1
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i)); // adiciona a aresta ao grafo com o peso definido
                    }
                }
            }
            case LISTA_DE_ADJACENCIA -> {
                this.grafo = new ListaDeAdjacencia(numeroDeVertices); // cria o grafo como lista de adjacência
                for (int i = 0; i < destinosList.size(); i++) { // percorre a lista de destinos
                    if (pesosList.get(i) == 1.0) { // se o peso for 1.0, adiciona a aresta sem peso
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i))); // adiciona a aresta ao grafo com peso padrão 1
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i)); // adiciona a aresta ao grafo com o peso definido
                    }
                }
            }
        }
        return this.grafo; 
    }

    @Override
    public Collection<Aresta> buscaEmProfundidade(Grafo g) {
        this.corDFS = new Cor[g.numeroDeVertices()]; // inicializa o array de cores para os vértices
        this.distanciaDFS = new int[g.numeroDeVertices()]; // inicializa o array de distâncias para os vértices
        for (Vertice u : g.vertices()) { // para cada vértice do grafo 
            this.corDFS[u.id()] = Cor.BRANCO; // inicializa a cor como branco
        }
        this.tempoAtual = 0; // inicializa o tempo atual como 0

        this.arestasDeArvore = new ArrayList<>(); // inicializa a coleção de arestas de árvore
        this.arestasDeRetorno = new ArrayList<>(); // inicializa a coleção de arestas de retorno
        this.arestasDeAvanco = new ArrayList<>(); // inicializa a coleção de arestas de avanço
        this.arestasDeCruzamento = new ArrayList<>(); // inicializa a coleção de arestas de cruzamento

        for (Vertice u : g.vertices()) { // para cada vértice do grafo
            if (this.corDFS[u.id()] == Cor.BRANCO) { // se a cor for branco, inicia a visita
                buscaEmProfundidadeVisitar(u, g); // chama o método de visita
            }
        }

        return this.arestasDeArvore; // retorna as arestas de árvore como resultado da busca em profundidade
    }

    private void buscaEmProfundidadeVisitar(Vertice u, Grafo grafo) { // função complementar da DFS para visitar os vértices
        this.corDFS[u.id()] = Cor.CINZA; // marca o vértice como em exploração
        this.tempoAtual++; // incrementa o tempo atual
        this.distanciaDFS[u.id()] = this.tempoAtual; // define o tempo de descoberta do vértice

        try { // tenta percorrer os vértices adjacentes (tratamento de exceção necessário para a função adjacentesDe)
            for (Vertice v : grafo.adjacentesDe(u)){ // para cada vértice adjacente
                switch (this.corDFS[v.id()]) { // verifica a cor do vértice adjacente
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
                        if ((this.distanciaDFS[u.id()] < this.distanciaDFS[v.id()])) { // se o tempo de descoberta de u for menor que o de v, é uma aresta de avanço
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
            System.out.println(e.getMessage()); // captura a exceção se os vértices adjacentes não puderem ser acessados
        }

        this.corDFS[u.id()] = Cor.PRETO; // marca o vértice como finalizado
    }

    @Override
    public Collection<Aresta> arestasDeArvore(Grafo g) {
        if (this.arestasDeArvore == null) { // se as arestas de árvore ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return this.arestasDeArvore;
    }

    @Override
    public Collection<Aresta> arestasDeRetorno(Grafo g) {
        if (this.arestasDeRetorno == null) { // se as arestas de retorno ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return this.arestasDeRetorno;
    }

    @Override
    public Collection<Aresta> arestasDeAvanco(Grafo g) {
        if (this.arestasDeAvanco == null) { // se as arestas de avanço ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return this.arestasDeAvanco;
    }

    @Override
    public Collection<Aresta> arestasDeCruzamento(Grafo g) {
        if (this.arestasDeCruzamento == null) { // se as arestas de cruzamento ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return this.arestasDeCruzamento;
    }

    @Override
    public Collection<Aresta> buscaEmLargura(Grafo g, Vertice origem) {
        this.corBFS = new Cor[g.numeroDeVertices()]; // inicializa o array de cores para os vértices
        this.paiBFS = new Vertice[g.numeroDeVertices()]; // inicializa o array de pais para os vértices
        this.distanciaBFS = new int[g.numeroDeVertices()]; // inicializa o array de distâncias para os vértices

        for (Vertice u : g.vertices()) { // para cada vértice do grafo
            this.corBFS[u.id()] = Cor.BRANCO; // inicializa a cor como branco
            this.distanciaBFS[u.id()] = Integer.MAX_VALUE; // inicializa a distância como infinito
            this.paiBFS[u.id()] = null; // inicializa o pai como null
        }

        this.corBFS[origem.id()] = Cor.CINZA; // marca o vértice origem como em exploração
        this.distanciaBFS[origem.id()] = 0; // define a distância do vértice origem como 0
        this.paiBFS[origem.id()] = null; // o pai do vértice origem é null

        this.filaBFS = new LinkedList<>(); // inicializa a fila para a busca em largura
        this.filaBFS.add(origem); // adiciona o vértice origem à fila

        this.arestasDaBuscaEmLargura = new ArrayList<>(); // inicializa a coleção de arestas da busca em largura

        while(!this.filaBFS.isEmpty()){ // enquanto a fila não estiver vazia
            Vertice u = this.filaBFS.poll(); // remove o vértice da frente da fila
            try { // tenta percorrer os vértices adjacentes (tratamento de exceção necessário para a função adjacentesDe)
                for (Vertice v : g.adjacentesDe(u)){ // para cada vértice adjacente
                    if(this.corBFS[v.id()] == Cor.BRANCO) { // se a cor for branco
                        this.corBFS[v.id()] = Cor.CINZA; // marca o vértice como em exploração
                        this.distanciaBFS[v.id()] = this.distanciaBFS[u.id()] + 1; // define a distância do vértice adjacente
                        this.paiBFS[v.id()] = u; // define o pai do vértice adjacente
                        this.filaBFS.add(v); // adiciona o vértice adjacente à fila
                        this.arestasDaBuscaEmLargura.add(new Aresta(u, v)); // adiciona a aresta à coleção de arestas da busca em largura
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage()); // captura a exceção se os vértices adjacentes não puderem ser acessados
            }
            this.corBFS[u.id()] = Cor.PRETO; // marca o vértice como finalizado
        }

        return this.arestasDaBuscaEmLargura;
    }

    @Override
    public boolean existeCiclo(Grafo g) {
        if (this.arestasDeRetorno == null) { // se as arestas de retorno ainda não foram calculadas
            buscaEmProfundidade(g); // realiza a busca em profundidade para calcular as arestas
        }
        return !this.arestasDeRetorno.isEmpty(); // se houver arestas de retorno, existe ciclo
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
                System.out.println(e.getMessage());
            }
        }
        return arestas;
    }

    @Override
    public Collection<Aresta> arvoreGeradoraMinima(Grafo g) {
        Collection<ArrayList<Vertice>> conjuntoVerticesAGM = new ArrayList<>(); // conjunto de vértices da árvore geradora mínima
        Collection<Aresta> arestasOrdenadasAGM; // conjunto ordenado de arestas da árvore geradora mínima
        Collection<Aresta> arestasX = new ArrayList<>(); // conjunto de arestas finais da árvore geradora mínima

        for (Vertice v : g.vertices()) { // para cada vértice do grafo
            ArrayList<Vertice> novoConjunto = new ArrayList<>(); // cria um novo conjunto de vértices
            novoConjunto.add(v); // adiciona o vértice ao novo conjunto
            conjuntoVerticesAGM.add(novoConjunto); // adiciona o novo conjunto à coleção de conjuntos
        }

        arestasOrdenadasAGM = todasAsArestas(g); // armazena todas as arestas do grafo na lista
        arestasOrdenadasAGM.stream().sorted((a1, a2) -> Double.compare(a1.peso(), a2.peso())); // ordena as arestas pelo peso

        for (Aresta a : arestasOrdenadasAGM) { // para cada aresta na lista ordenada
            ArrayList<Vertice> conjuntoU = null; // conjunto do vértice origem
            ArrayList<Vertice> conjuntoV = null; // conjunto do vértice destino

            for (ArrayList<Vertice> conjunto : conjuntoVerticesAGM) { // para cada conjunto de vértices
                if (conjunto.contains(a.origem())) { // se o conjunto contém o vértice origem
                    conjuntoU = conjunto; // atribui o conjunto ao conjuntoU
                }
                if (conjunto.contains(a.destino())) { // se o conjunto contém o vértice destino
                    conjuntoV = conjunto; // atribui o conjunto ao conjuntoV
                }
            }

            if (conjuntoU != conjuntoV) { // se os conjuntos são diferentes, a aresta pode ser adicionada à árvore geradora mínima
                arestasX.add(a); // adiciona a aresta à lista final de arestas da árvore geradora mínima
                if(conjuntoU != null && conjuntoV != null) {
                    conjuntoU.addAll(conjuntoV); // une os dois conjuntos caso não sejam nulos
                }
                conjuntoVerticesAGM.remove(conjuntoV); // remove o conjuntoV da coleção de conjuntos
            }
        }
        return arestasX;
    }

    @Override
    public double custoDaArvoreGeradora(Grafo g, Collection<Aresta> arestas) throws Exception {

        if (arestas == null || arestas.isEmpty()) {
            throw new Exception("A árvore apresentada não é geradora do grafo.");
        }

        double custoTotal = 0; // inicializa o custo total como 0
        for (Aresta a : arestas) { // para cada aresta na coleção
            custoTotal += a.peso(); // soma o peso da aresta ao custo total
        }
        return custoTotal; // retorna o custo total da árvore geradora mínima
    }

    @Override
    public ArrayList<Aresta> caminhoMinimo(Grafo g, Vertice origem, Vertice destino ) {
        this.distanciaDijkstra = new int[g.numeroDeVertices()]; // inicializa o array de distâncias
        this.paiDijkstra = new Vertice[g.numeroDeVertices()]; // inicializa o array

        for (Vertice v : g.vertices()){
            this.distanciaDijkstra[v.id()] = Integer.MAX_VALUE; // inicializa a distância como infinito
            this.paiDijkstra[v.id()] = null; // inicializa o pai como null
        }

        this.distanciaDijkstra[origem.id()] = 0; // define a distância do vértice origem como 0

        ArrayList<Vertice> verticesNaoDescobertos = new ArrayList<>(); // inicializa a lista de vértices não visitados

        Vertice minimo;

        for (Vertice v : g.vertices()){
            verticesNaoDescobertos.add(v); // adiciona todos os vértices à lista de não visitados
        }

        while (!verticesNaoDescobertos.isEmpty()) { // enquanto o destino não for alcançado e houver vértices não visitados
            minimo = null;
            for (Vertice v : verticesNaoDescobertos) {
                if (minimo == null || this.distanciaDijkstra[v.id()] < this.distanciaDijkstra[minimo.id()]) {
                    minimo = v; // encontra o vértice com a menor distância
                }
            }

            verticesNaoDescobertos.remove(minimo); // remove o vértice mínimo da lista de não visitados

            try {
                for (Vertice v : g.adjacentesDe(minimo)) { // para cada vértice adjacente ao vértice mínimo
                    ArrayList<Aresta> arestasEntre = g.arestasEntre(minimo, v); // obtém as arestas entre os dois vértices
                    for (Aresta a : arestasEntre) {
                        relaxarAresta(minimo, v, a.peso()); // relaxa a aresta
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage()); // captura a exceção se os vértices adjacentes não puderem ser acessados
            }
        }

        ArrayList<Aresta> caminhoMinimo = new ArrayList<>(); // inicializa a lista de arestas do caminho mínimo
        Vertice atual = destino; // começa do vértice destino
        Double pesoAresta;
        while (atual != null && this.paiDijkstra[atual.id()] != null)
        {
            try {
                pesoAresta = g.arestasEntre(this.paiDijkstra[atual.id()], atual).get(0).peso(); // obtém o peso da aresta entre o pai e o vértice atual
            } catch (Exception e) {
                System.out.println(e.getMessage());
                pesoAresta = 1.0;
            }
            Aresta aresta = new Aresta(this.paiDijkstra[atual.id()], atual, pesoAresta); // cria a aresta entre o pai e o vértice atual
            caminhoMinimo.add(0, aresta); // adiciona a aresta no início da lista do caminho mínimo
            atual = this.paiDijkstra[atual.id()]; // move para o pai do vértice atual
        }

        return caminhoMinimo;
    }

    private void relaxarAresta(Vertice u, Vertice v, double peso) {
        if (this.distanciaDijkstra[v.id()] > this.distanciaDijkstra[u.id()] + peso) {
            this.distanciaDijkstra[v.id()] = (int) (this.distanciaDijkstra[u.id()] + peso);
            this.paiDijkstra[v.id()] = u;
        }
    }
    
    @Override
    public double custoDoCaminhoMinimo (Grafo g, ArrayList<Aresta> arestas, Vertice origem, Vertice destino ) throws Exception {

        if (arestas == null || arestas.isEmpty()) {
            throw new Exception("A sequência apresentada não é um caminho entre origem e destino.");
        }

        double custoTotal = 0; // inicializa o custo total como 0
        Vertice atual = origem; // começa do vértice origem

        for (Aresta a : arestas) { // para cada aresta na coleção
            if (a.origem().id() != atual.id()) { // verifica se a aresta é válida no caminho
                throw new Exception("A sequência apresentada não é um caminho entre origem e destino.");
            }
            custoTotal += a.peso(); // soma o peso da aresta ao custo total
            atual = a.destino(); // move para o vértice destino da aresta
        }

        if (atual.id() != destino.id()) { // verifica se o último vértice é o destino
            throw new Exception("A sequência apresentada não é um caminho entre origem e destino.");
        }

        return custoTotal; // retorna o custo total do caminho mínimo
    }

    @Override
    public double fluxoMaximo (Grafo g) {
        return 0;
    }
}
