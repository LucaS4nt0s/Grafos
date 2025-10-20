package trabalho;

// import grafos.Aresta;
// import grafos.Grafo;
// import grafos.TipoDeRepresentacao;
// import grafos.Vertice;
// import grafos.AlgoritmosEmGrafos;
import grafos.FileManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
// import java.io.File;
// import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager();

        System.out.println("Arquivo lido com sucesso!");
        System.out.println(fileManager.stringReader("test/teste.txt"));

        ArrayList<String> conteudo = fileManager.stringReader("test/teste.txt");

        int numeroDeVertices = Integer.parseInt(conteudo.get(0));
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

        System.out.println("Lista de Adjacência:");
        for (int i = 0; i < listaDeAdj.size(); i++) {
            System.out.print(i + ": ");
            for (int[] aresta : listaDeAdj.get(i)) {
                System.out.print("-> (" + aresta[0] + ", peso: " + aresta[1] + ") ");
            }
            System.out.println();
        }
    }
}
