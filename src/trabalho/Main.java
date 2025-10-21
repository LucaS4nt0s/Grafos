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

        ArrayList<String> conteudo = fileManager.stringReader("test/teste.txt");

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

        System.out.println("Vertices: " + numeroDeVertices);
        System.out.println("Arestas: " + numeroDeArestas);
        System.out.println("Destinos: " + destinosList);
        System.out.println("Origens: " + origensList);
        System.out.println("Pesos: " + pesosList);

    }
}
