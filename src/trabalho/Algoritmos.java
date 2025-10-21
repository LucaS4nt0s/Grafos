package trabalho;

import java.util.ArrayList;
import java.util.Collection;
import grafos.AlgoritmosEmGrafos;
import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;
import grafos.TipoDeRepresentacao;
import grafos.FileManager;
    
public class Algoritmos implements AlgoritmosEmGrafos {

    @Override
    public Grafo carregarGrafo(String path, TipoDeRepresentacao t) throws Exception {
        Grafo grafo = null;
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
        return null;
    }

    @Override
    public Collection<Aresta> arestasDeArvore(Grafo g) {
        return null;
    }

    @Override
    public Collection<Aresta> arestasDeRetorno(Grafo g) {
        return null;
    }

    @Override
    public Collection<Aresta> arestasDeAvanco(Grafo g) {
        return null;
    }

    @Override
    public Collection<Aresta> arestasDeCruzamento(Grafo g) {
        return null;
    }

    @Override
    public Collection<Aresta> buscaEmLargura(Grafo g) {
        return null;
    }

    @Override 
    public boolean existeCiclo(Grafo g) {
        return false;
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
