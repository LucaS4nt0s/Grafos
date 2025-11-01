package trabalho;

import grafos.TipoDeRepresentacao;
import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        Algoritmos algoritmos = new Algoritmos();
        TipoDeRepresentacao tipo = TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA;
        Grafo grafo = null;
        ArrayList<Vertice> vertices = new ArrayList<>();

        try {
            grafo = algoritmos.carregarGrafo("test/teste.txt", tipo);
            System.out.println("Grafo carregado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao carregar o grafo: " + e.getMessage());
        }

        if(grafo != null) {
            vertices.addAll(grafo.vertices());
        }
        try {
            for (Vertice v : vertices) {
                if(grafo != null) {
                    System.out.println("Vértice ID: " + v.id() + ", Grau: " + grafo.grauDoVertice(v));
                    System.out.println("Vértices adjacentes: ");
                    for (Vertice adj : grafo.adjacentesDe(v)) {
                        System.out.println(adj.id() + " ");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar arestas entre 0 e 1: " + e.getMessage());
        }

        for (Aresta aresta : algoritmos.buscaEmProfundidade(grafo)) {
            System.out.println("Aresta: " + aresta.origem().id() + " -> " + aresta.destino().id());
            
        }
        System.out.println("Arestas da árvore: ");
        for (Aresta a : algoritmos.arestasDeArvore(grafo)) {
            System.out.println(a.origem().id() + " -> " + a.destino().id());
        }
        System.out.println("Arestas da retorno: ");
        for (Aresta a : algoritmos.arestasDeRetorno(grafo)) {
            System.out.println(a.origem().id() + " -> " + a.destino().id());
        }
        System.out.println("Arestas da avanco: ");
        for (Aresta a : algoritmos.arestasDeAvanco(grafo)) {
            System.out.println(a.origem().id() + " -> " + a.destino().id());
        }
        System.out.println("Arestas da cruzamento: ");
        for (Aresta a : algoritmos.arestasDeCruzamento(grafo)) {
            System.out.println(a.origem().id() + " -> " + a.destino().id());
        }
        System.out.println("Ciclo: " + algoritmos.existeCiclo(grafo));

        System.out.println("Busca em largura a partir do vértice 0:");
        for (Aresta aresta : algoritmos.buscaEmLargura(grafo, new Vertice(0))) {
            System.out.println("Aresta: " + aresta.origem().id() + " -> " + aresta.destino().id());
        }

        System.out.println("Árvore geradora mínima:");
        for (Aresta aresta : algoritmos.arvoreGeradoraMinima(grafo)){
            System.out.println("Aresta: " + aresta.origem().id() + " -> " + aresta.destino().id());
        }

        try {
            System.out.println("Custo da árvore geradora mínima: " + algoritmos.custoDaArvoreGeradora(grafo, algoritmos.arvoreGeradoraMinima(grafo)));
        } catch (Exception e) {
            System.out.println("Erro ao calcular o custo da árvore geradora mínima: " + e.getMessage());    
        }

        System.out.println("Caminho minimo:");
        for (Aresta aresta : algoritmos.caminhoMinimo(grafo, new Vertice(0), new Vertice(2))) {
            System.out.println("Aresta: " + aresta.origem().id() + " -> " + aresta.destino().id());
        }

        System.out.println("Custo do caminho mínimo:");
        try {
            System.out.println(algoritmos.custoDoCaminhoMinimo(grafo, algoritmos.caminhoMinimo(grafo, new Vertice(0), new Vertice(4)), new Vertice(0), new Vertice(4)));
        } catch (Exception e) {
            System.out.println("Erro ao calcular o custo do caminho mínimo: " + e.getMessage());
        }
    }
}
