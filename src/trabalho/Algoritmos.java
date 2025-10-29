package trabalho;

import java.util.ArrayList;
import java.util.Collection;
import grafos.AlgoritmosEmGrafos;
import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;
import grafos.TipoDeRepresentacao;
import grafos.FileManager;
import java.util.Queue;
import java.util.LinkedList;
    
public class Algoritmos implements AlgoritmosEmGrafos {
    
    private Grafo grafo;

    
    private enum Cor{
        BRANCO,
        CINZA,
        PRETO
    }
    private int[] distanciaDFS;
    private int tempoAtual;
    private Cor[] corDFS;
    private Cor[] corBFS;
    private Vertice[] paiBFS;
    private int[] distanciaBFS;
    private Queue<Vertice> filaBFS;
    private Collection<Aresta> arestasDeArvore;
    private Collection<Aresta> arestasDeRetorno;
    private Collection<Aresta> arestasDeAvanco;
    private Collection<Aresta> arestasDeCruzamento;
    private Collection<Aresta> arestasDaBuscaEmLargura;

    @Override
    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
        FileManager fileManager = new FileManager();

        ArrayList<String> conteudo = fileManager.stringReader(path);

        int numeroDeVertices = Integer.parseInt(conteudo.get(0));
        int numeroDeArestas = 0;
        for (int i = 1; i < conteudo.size(); i++) {
            String[] partes = conteudo.get(i).split(" ");
            numeroDeArestas += partes.length - 1;
        }

        ArrayList<Integer> destinosList = new ArrayList<>(numeroDeArestas);
        ArrayList<Integer> origensList = new ArrayList<>(numeroDeArestas);
        ArrayList<Double> pesosList = new ArrayList<>(numeroDeArestas);

        for (int j = 1; j < conteudo.size(); j++) {
            String[] partes = conteudo.get(j).split(" ");
            for (int k = 1; k < partes.length; k++) {
                String[] subpartes = partes[k].split("-");
                String[] pesos = subpartes[1].split(";");
                destinosList.add(Integer.valueOf(subpartes[0]));
                origensList.add(Integer.valueOf(partes[0]));
                pesosList.add(Double.valueOf(pesos[0]));
            }
        }

        switch (t) {
            case MATRIZ_DE_ADJACENCIA -> {
                grafo = new MatrizDeAdjacencia(numeroDeVertices);
                for (int i = 0; i < destinosList.size(); i++) {
                    if (pesosList.get(i) == 1.0) {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)));
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i));
                    }
                }
            }
            case MATRIZ_DE_INCIDENCIA -> {
                grafo = new MatrizDeIncidencia(numeroDeVertices, numeroDeArestas);
                for (int i = 0; i < destinosList.size(); i++) {
                    if (pesosList.get(i) == 1.0) {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)));
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i));
                    }
                }
            }
            case LISTA_DE_ADJACENCIA -> {
                grafo = new ListaDeAdjacencia(numeroDeVertices);
                for (int i = 0; i < destinosList.size(); i++) {
                    if (pesosList.get(i) == 1.0) {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)));
                    } else {
                        grafo.adicionarAresta(new Vertice(origensList.get(i)), new Vertice(destinosList.get(i)), pesosList.get(i));
                    }
                }
            }
        }
        return grafo;
    }

    @Override
    public Collection<Aresta> buscaEmProfundidade(Grafo g) {
        for (Vertice u : grafo.vertices()) {
            corDFS[u.id()] = Cor.BRANCO;
        }
        tempoAtual = 0;

        arestasDeArvore = new ArrayList<>();
        arestasDeRetorno = new ArrayList<>();
        arestasDeAvanco = new ArrayList<>();
        arestasDeCruzamento = new ArrayList<>();

        for (Vertice u : grafo.vertices()) {
            if (corDFS[u.id()] == Cor.BRANCO) {
                buscaEmProfundidadeVisitar(u, grafo);
            }
        }
        
        return arestasDeArvore;
    }

    private void buscaEmProfundidadeVisitar(Vertice u, Grafo grafo) {
        corDFS[u.id()] = Cor.CINZA;
        tempoAtual++;
        distanciaDFS[u.id()] = tempoAtual;

        try {
            for (Vertice v : grafo.adjacentesDe(u)){
                switch (corDFS[v.id()]) {
                    case BRANCO -> {
                        buscaEmProfundidadeVisitar(v, grafo);
                        Aresta arestaDeArvore = new Aresta(u, v);
                        arestasDeArvore.add(arestaDeArvore);
                    }
                    case CINZA -> {
                        Aresta arestaDeRetorno = new Aresta(u, v);
                        arestasDeRetorno.add(arestaDeRetorno);
                    }
                    case PRETO -> {
                        if ((distanciaDFS[u.id()] < distanciaDFS[v.id()])) {
                            Aresta arestaDeAvanco = new Aresta(u, v);
                            arestasDeAvanco.add(arestaDeAvanco);
                        } else {
                            Aresta arestaDeCruzamento = new Aresta(u, v);
                            arestasDeCruzamento.add(arestaDeCruzamento);
                        }
                    }
                    default -> throw new AssertionError();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

        corDFS[u.id()] = Cor.PRETO;
    }

    @Override
    public Collection<Aresta> arestasDeArvore(Grafo g) {
        if (arestasDeArvore == null) {
            buscaEmProfundidade(g);
        }
        return arestasDeArvore;
    }

    @Override
    public Collection<Aresta> arestasDeRetorno(Grafo g) {
        if (arestasDeRetorno == null) {
            buscaEmProfundidade(g);
        }
        return arestasDeRetorno;
    }

    @Override
    public Collection<Aresta> arestasDeAvanco(Grafo g) {
        if (arestasDeAvanco == null) {
            buscaEmProfundidade(g);
        }
        return arestasDeAvanco;
    }

    @Override
    public Collection<Aresta> arestasDeCruzamento(Grafo g) {
        if (arestasDeCruzamento == null) {
            buscaEmProfundidade(g);
        }
        return arestasDeCruzamento;
    }

    @Override
    public Collection<Aresta> buscaEmLargura(Grafo g, Vertice origem) {
        for (Vertice u : grafo.vertices()) {
            corBFS[u.id()] = Cor.BRANCO;
            distanciaBFS[u.id()] = Integer.MAX_VALUE;
            paiBFS[u.id()] = null;
        }

        corBFS[origem.id()] = Cor.CINZA;
        distanciaBFS[origem.id()] = 0;
        paiBFS[origem.id()] = null;

        filaBFS = new LinkedList<>();
        filaBFS.add(origem);

        while(!filaBFS.isEmpty()){
            Vertice u = filaBFS.poll();
            try {
                for (Vertice v : grafo.adjacentesDe(u)){
                    if(corBFS[v.id()] == Cor.BRANCO) {
                        corBFS[v.id()] = Cor.CINZA;
                        distanciaBFS[v.id()] = distanciaBFS[u.id()] + 1;
                        paiBFS[v.id()] = u;
                        filaBFS.add(v);
                        arestasDaBuscaEmLargura.add(new Aresta(u, v));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }
            corBFS[u.id()] = Cor.PRETO;
        }

        return arestasDaBuscaEmLargura;
    }

    @Override 
    public boolean existeCiclo(Grafo g) {
        if (arestasDeRetorno == null) {
            buscaEmProfundidade(g);
        }
        return !arestasDeRetorno.isEmpty();
    }

    @Override
    public Grafo componentesFortementeConexos (Grafo g) {
        return null;
    }

    @Override
    public Collection<Aresta> arvoreGeradoraMinima(Grafo g) {
        return null;
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
