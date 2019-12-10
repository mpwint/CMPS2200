import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CMPS-2200 Project2 Applied Dijkstra
 * @author Mary Pwint
 */

public class ShortestPath {

    /** Assumptions:
     *  1) Vertices are integer values starting from 0
     */

    private class Graph {
        int numVertices;
        int numEdges;
        ArrayList[] adjVertexLists; // adjacency list
        int[][] weights;    // table of weights

        /**
         * Construct and empty graph
         */
        Graph() {
            this.numVertices = 0;
            this.numEdges = 0;
        }

        Graph(int numVertices, int numEdges) {
            this.numVertices = numVertices;
            this.numEdges = numEdges;
            this.adjVertexLists = new ArrayList[numVertices+1] ;
            this.weights = new int[numVertices+1][numVertices+1];

            // Initialize each list in adjListArray, 0th list will always be empty
            for (int i = 0; i <= numVertices; i++) {
                adjVertexLists[i] = new ArrayList();
            }
        }

        public void addEgde(int source, int destination, int weight) {
            adjVertexLists[source].add(destination);
            weights[source][destination] = weight;
        }

        public int getWeight(int source, int neighbor) {
            return weights[source][neighbor];
        }
    }

    public void dijkstra(Graph graph, int source, int target) throws IOException {
        PriorityQueue queue = new PriorityQueue(graph.numVertices+1);
        int[] dist = new int[graph.numVertices+1];     // array if distances from source
        int[] prev = new int[graph.numVertices+1];     // array of predecessors

        dist[source] = 0;

        for (int i = 1; i <= graph.numVertices; i++) {
            // Initialize dist of each vertex v to infinity and prev to 0
            if (i != source) {
                dist[i] = Integer.MAX_VALUE;
                prev[i] = 0;
            }
            queue.enqueue(i, dist[i]);
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            System.out.println(u);
            // for each neighbor n of u
            for (int j = 0; j < graph.adjVertexLists[u].size(); j++) {
                int n = (int) graph.adjVertexLists[u].get(j);
                int alt = dist[u] + graph.getWeight(u, n);
                if (alt < dist[n]) {
                    dist[n] = alt;
                    prev[n] = u;
                    queue.decreaseKey(n, alt);
                }
            }
        }
//        System.out.println(Arrays.toString(dist));
//        System.out.println(Arrays.toString(prev));
        ArrayList<Integer> s = new ArrayList<>();
        s.add(target);
        int vertex = target;
        while (vertex != source) {
            s.add(prev[vertex]);
            vertex = prev[vertex];
        }
        System.out.println(dist[target]);
        for (int v = s.size()-1; v >= 0; v--) {
            System.out.println(s.get(v));
        }
        FileWriter output = new FileWriter("DijkstraOutput.txt");
        PrintWriter printWriter = new PrintWriter(output);
        printWriter.println(dist[target]);
        for (int v = s.size()-1; v >= 0; v--) {
            printWriter.println(s.get(v));
        }
        printWriter.close();
    }

    class PriorityQueue {
        private int[] heap;
        private int[] dist;
        private int size;

        /**
         * Construct an empty MinHeap
         * @param capacity max size of the MinHeap
         */
        public PriorityQueue(int capacity) {
            this.size = 0;
            this.heap = new int[capacity];
            this.dist = new int[capacity];
            heap[0] = Integer.MIN_VALUE;    // placeholder(-inf) for unused index 0
        }

        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * @param index the index of the element
         * @return the index of the left child of the element
         */
        private int leftChild(int index) {
            return (2 * index);
        }

        /**
         * @param index the index of the element
         * @return the index of the right child of the element
         */
        private int rightChild(int index) {
            return (2 * index) + 1;
        }

        /**
         * @param index the index of the element
         * @return the index of the parent of the element
         */
        private int parent(int index) { return index/2; }

        /**
         * @param index the index of the element
         * @return true if the element is a leaf; false otherwise
         */
        private boolean isLeaf(int index) {
            return index >= (size / 2) && index <= size;
        }

        /**
         * Swaps elements at indices i1 and i2
         * @param i1 index of the first element to be swapped
         * @param i2 index of the second element to be swapped
         */
        private void swap(int i1, int i2) {
            int temp1, temp2;
            temp1 = heap[i1];
            heap[i1] = heap[i2];
            heap[i2] = temp1;
            temp2 = dist[heap[i1]];
            dist[heap[i1]] = dist[heap[i2]];
            dist[heap[i2]] = temp2;
        }

        /**
         * Floats the element at index until the MaxHeap property is satisfied
         * @param index the index of the element
         */
        private void minHeapify(int index) {
//            if (isLeaf(index))
//                return;
            int smallest = index;
            if (leftChild(index) <= size && dist[heap[leftChild(index)]] <= dist[heap[index]]) {
                smallest = leftChild(index);
            }
            if (rightChild(index) <= size && dist[heap[rightChild(index)]] <= dist[heap[smallest]]) {
                smallest = rightChild(index);
            }
            if (smallest != index) {
                swap(index, smallest);
                minHeapify(smallest);
            }
        }

        private void heapsort() {
            for (int i=size/2; i>=1; i--) {
                minHeapify(i);
            }
            for (int i=size; i>=2; i--) {
                swap(1,i);
                size--;
                minHeapify(1);
            }
        }

        public void enqueue(int vertex, int distance) {
            heap[vertex] = vertex;
            dist[vertex] = distance;
            size++;
            minHeapify(vertex);
        }

        public int poll() {
            int polled = heap[1];
            heap[1] = heap[size--];
            minHeapify(1);
            return polled;
        }

        public void decreaseKey(int neighbor, int distance) {
            dist[neighbor] = distance;
            minHeapify(neighbor);
        }
    }

    /**
     * Reads a graph file into adjacency list
     * @param grFile input graph file
     * @throws FileNotFoundException
     */
    public Graph readGraph(File grFile) throws FileNotFoundException {
        String problem = "^(p)(\\s)(sp)(\\s)(\\d+)(\\s)(\\d+)$";
        String arcDescriptor = "^(a)(\\s)(\\d+)(\\s)(\\d+)(\\s)(\\d+)$";
        Pattern a = Pattern.compile(arcDescriptor);
        Pattern p = Pattern.compile(problem);
        BufferedReader br = new BufferedReader(new FileReader(grFile));
        Graph graph = new Graph();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher pm = p.matcher(line);
                Matcher m = a.matcher(line);
                if (pm.find()) {
                    int numVertices = Integer.parseInt(pm.group(5));
                    int numEdges = Integer.parseInt(pm.group(7));
                    graph = new Graph(numVertices, numEdges);
                }
                while (m.find()) {
                    int src = Integer.parseInt(m.group(3));
                    int dest = Integer.parseInt(m.group(5));
                    int wt = Integer.parseInt(m.group(7));
                    graph.addEgde(src, dest, wt);
                }
            }
//            graph.printGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public static void main(String[] args) throws IOException {
        ShortestPath path = new ShortestPath();
        // TODO: TAKE FILENAME AND SOURCE VERTEX FROM COMMANDLINE
//        Graph graph = path.readGraph(new File("C:\\Users\\mayyo\\IdeaProjects\\CMPS2200\\Project2\\src\\testGrFile.gr"));
        Graph graph = path.readGraph(new File("C:\\Users\\mayyo\\IdeaProjects\\CMPS2200\\Project2\\src\\NY-road.gr"));
        path.dijkstra(graph, 1, 1276);
    }
}