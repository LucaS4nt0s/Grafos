# 🕸️ Grafos - Biblioteca e Algoritmos Clássicos em Teoria dos Grafos

![Java](https://img.shields.io/badge/Language-Java-orange.svg)
![Data Structures](https://img.shields.io/badge/Data%20Structures-Graphs-blue.svg)
![Algorithms](https://img.shields.io/badge/Algorithms-BFS%20%7C%20DFS%20%7C%20Dijkstra%20%7C%20Prim-brightgreen.svg)
![Status](https://img.shields.io/badge/Status-Conclu%C3%ADdo-brightgreen.svg)

## 📌 Visão Geral
Este projeto é uma implementação robusta em **Java** de estruturas de dados e algoritmos fundamentais da **Teoria dos Grafos**, cobrindo tanto grafos direcionados quanto não-direcionados, ponderados e não-ponderados.

A biblioteca foi desenvolvida com forte orientação a objetos, abstraindo representações computacionais por **Matriz de Adjacência** e **Lista de Adjacência** através de contratos de interface, e implementando os algoritmos clássicos de percurso, cálculo de menores caminhos e geração de árvores geradoras mínimas.

---

## 🚀 Funcionalidades e Algoritmos Implementados
- 📐 **Múltiplas Representações em Memória**:
  - `Matriz de Adjacência`: Otimizada para consultas rápidas de existência de aresta em $O(1)$.
  - `Lista de Adjacência`: Otimizada para economia de memória em grafos esparsos ($O(V + E)$).
- 🚶‍♂️ **Algoritmos de Busca e Travessia**:
  - **BFS (Breadth-First Search / Busca em Largura)**: Travessia por níveis utilizando fila (`Queue`), ideal para menor distância em grafos sem pesos.
  - **DFS (Depth-First Search / Busca em Profundidade)**: Travessia profunda com pilha/recursão, permitindo detecção de ciclos e ordenação topológica.
- 🛣️ **Menor Caminho**:
  - **Algoritmo de Dijkstra**: Cálculo eficiente do caminho de custo mínimo a partir de um vértice fonte até todos os demais nós em grafos com pesos não-negativos.
- 🌲 **Árvore Geradora Mínima (AGM / MST)**:
  - **Algoritmo de Prim / Kruskal**: Determinação do subconjunto conexo de arestas que conecta todos os vértices com o menor custo acumulado possível.
- 📁 **Importação de Grafos**:
  - Carregamento automatizado de instâncias a partir de arquivos texto formatados (`FileManager.java`).

---

## 🛠️ Tecnologias e Ferramentas
- **Linguagem**: Java (JDK 8+)
- **Interface e Contratos**: `AlgoritmosEmGrafos.java`, `TipoDeRepresentacao.java`
- **Estruturas Auxiliares**: `LinkedList`, `PriorityQueue`, `HashMap`, `Queue`

---

## 📂 Estrutura do Repositório
```plaintext
Grafos/
├── CarregarGrafo/
│   └── Grafo.txt                       # Arquivo de exemplo contendo instâncias de grafos ponderados
└── src/
    ├── grafos/
    │   ├── AlgoritmosEmGrafos.java      # Interface com os métodos obrigatórios de algoritmos
    │   ├── Grafo.java                  # Classe abstrata/base para manipulação de grafos
    │   ├── Vertice.java                # Modelo de vértice com identificadores e rótulos
    │   ├── Aresta.java                 # Modelo de aresta com peso e sentido
    │   ├── TipoDeRepresentacao.java    # Enum para alternar entre Matriz e Lista
    │   └── FileManager.java            # Utilitário de I/O para leitura e parse de grafos em disco
    └── trabalho/
        ├── Algoritmos.java             # Implementação prática dos algoritmos de busca e otimização
        └── ListaDeAdjacencia.java      # Estrutura concreta de grafo baseada em listas
```

---

## ⚙️ Como Executar o Projeto Localmente

### Pré-requisitos
- **Java Development Kit (JDK 8+)** instalado.

### Compilação e Testes
1. Clone o repositório:
   ```bash
   git clone https://github.com/LucaS4nt0s/Grafos.git
   cd Grafos/src
   ```
2. Compile os pacotes:
   ```bash
   javac grafos/*.java trabalho/*.java
   ```
3. Para integrar a biblioteca ou executar testes, instancie a classe `trabalho.Algoritmos` informando o caminho para o arquivo `CarregarGrafo/Grafo.txt`.

---

## 👨‍💻 Autor
Desenvolvido por **Luca Samuel dos Santos** ([@LucaS4nt0s](https://github.com/LucaS4nt0s)).
