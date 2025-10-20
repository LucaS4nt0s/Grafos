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
        FileManager fileManager = new FileManager();

        ArrayList<String> conteudo = null;

        try {
            conteudo = fileManager.stringReader(path);
        } catch (Exception e) {
            throw new Exception("Caminho inválido ou arquivo fora do padrão.");
        }
        
        if (conteudo == null || conteudo.isEmpty()) {
            throw new Exception("Arquivo vazio ou fora do padrão.");
        }

        int numeroDeVertices = Integer.parseInt(conteudo.get(0));
        
        switch (t) {
            case MATRIZ_DE_ADJACENCIA -> {

                int matriz[][] = new int[numeroDeVertices][numeroDeVertices];

                for (int i = 0; i < numeroDeVertices; i++) {
                    for (int j = 0; j < numeroDeVertices; j++) {
                        // Inicializa a matriz com infinito
                        matriz[i][j] = Integer.MAX_VALUE;
                    }
                }

                for (int i = 1; i < conteudo.size(); i++) {
                    String[] partes = conteudo.get(i).split(" ");
                    for(int j = 1; j < partes.length; j++) {
                        String[] subpartes = partes[j].split("-");
                        String[] pesos = subpartes[1].split(";");
                        int peso = Integer.parseInt(pesos[0]);
                        matriz[Integer.parseInt(partes[0])][Integer.parseInt(subpartes[0])] = peso;
                    }
                }

            }
            case MATRIZ_DE_INCIDENCIA -> {

                int vertices = Integer.parseInt(conteudo.get(0));
                int count = 0;
                ArrayList<Integer> destino = new ArrayList<>();
                ArrayList<Integer> peso = new ArrayList<>();
                ArrayList<Integer> origem = new ArrayList<>();

                destino.clear();
                peso.clear();
                origem.clear();

                for (int i = 1; i < conteudo.size(); i++) {
                    String[] partes = conteudo.get(i).split(" ");
                    for(int j = 1; j < partes.length; j++) {
                        String[] subpartes = partes[j].split("-");
                        String[] pesos = subpartes[1].split(";");
                        destino.add(Integer.valueOf(subpartes[0]));
                        peso.add(Integer.valueOf(pesos[0]));
                        origem.add(Integer.valueOf(partes[0]));
                        count++;
                    }
                }

                int matriz[][] = new int[vertices][count];

                for (int i = 0; i < vertices; i++) {
                    for (int j = 0; j < count; j++) {
                        matriz[i][j] = Integer.MAX_VALUE;
                    }
                }

                for(int i = 0; i < destino.size(); i++) {
                    matriz[origem.get(i)][i] = (-peso.get(i));
                    matriz[destino.get(i)][i] = peso.get(i);
                }

            }
            case LISTA_DE_ADJACENCIA -> {

                ArrayList<ArrayList<int[]>> listaDeAdj = new ArrayList<>();
                for (int i = 0; i < numeroDeVertices; i++) {
                    listaDeAdj.add(new ArrayList<>());
                }

                for (int i = 1; i <= numeroDeVertices; i++) {

                    String[] partes = conteudo.get(i).split(" ");
                    int origem = Integer.parseInt(partes[0]);

                    for (int j = 1; j < partes.length; j++) {
                        String[] subpartes = partes[j].split("-");
                        String[] pesos = subpartes[1].split(";");

                        int destino = Integer.parseInt(subpartes[0]);
                        int peso = Integer.parseInt(pesos[0]);

                        listaDeAdj.get(origem).add(new int[]{destino, peso});
                    }
                }
            }
            default -> throw new AssertionError();
        }
        return null;
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
