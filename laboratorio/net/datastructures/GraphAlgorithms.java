package net.datastructures;
/**
 * A collection of graph algorithms that support Double edge weights.
 */
public class GraphAlgorithms {

  /**
   * Computes shortest-path distances from src vertex to all reachable vertices of g.
   *
   * This implementation uses Dijkstra's algorithm.
   *
   * The edge's element is assumed to be its weight as a Double.
   */
  public static <V> Map<Vertex<V>, Double>
  shortestPathLengths(Graph<V, Double> g, Vertex<V> src) {
      // d.get(v) is upper bound on distance from src to v
      Map<Vertex<V>, Double> d = new ProbeHashMap<>();
      // map reachable v to its d value
      Map<Vertex<V>, Double> cloud = new ProbeHashMap<>();
      // pq will have vertices as elements, with d.get(v) as key
      AdaptablePriorityQueue<Double, Vertex<V>> pq;
      pq = new HeapAdaptablePriorityQueue<>();
      // maps from vertex to its pq locator
      Map<Vertex<V>, Entry<Double, Vertex<V>>> pqTokens;
      pqTokens = new ProbeHashMap<>();

      // for each vertex v of the graph, add an entry to the priority queue, with
      // the source having distance 0 and all others having infinite distance
      for (Vertex<V> v : g.vertices()) {
          if (v == src)
              d.put(v, 0.0);
          else
              d.put(v, Double.MAX_VALUE);
          pqTokens.put(v, pq.insert(d.get(v), v)); // save entry for future updates
      }
      // now begin adding reachable vertices to the cloud
      while (!pq.isEmpty()) {
          Entry<Double, Vertex<V>> entry = pq.removeMin();
          double key = entry.getKey();
          Vertex<V> u = entry.getValue();
          cloud.put(u, key); // this is actual distance to u
          pqTokens.remove(u); // u is no longer in pq
          for (Edge<Double> e : g.outgoingEdges(u)) {
              Vertex<V> v = g.opposite(u, e);
              if (cloud.get(v) == null) {
                  // perform relaxation step on edge (u,v)
                  double wgt = e.getElement();
                  if (d.get(u) + wgt < d.get(v)) { // better path to v?
                      d.put(v, d.get(u) + wgt); // update the distance
                      pq.replaceKey(pqTokens.get(v), d.get(v)); // update the pq entry
                  }
              }
          }
      }
      return cloud; // this only includes reachable vertices
  }

  /**
   * Reconstructs a shortest-path tree rooted at vertex s, given distance map d.
   * The tree is represented as a map from each reachable vertex v (other than s)
   * to the edge e = (u,v) that is used to reach v from its parent u in the tree.
   */
  public static <V> Map<Vertex<V>, Edge<Double>>
  spTree(Graph<V, Double> g, Vertex<V> s, Map<Vertex<V>, Double> d) {
      Map<Vertex<V>, Edge<Double>> tree = new ProbeHashMap<>();
      for (Vertex<V> v : d.keySet())
          if (v != s)
              for (Edge<Double> e : g.incomingEdges(v)) { // consider INCOMING edges
                  Vertex<V> u = g.opposite(v, e);
                  double wgt = e.getElement();
                  if (d.get(v) == d.get(u) + wgt)
                      tree.put(v, e); // edge is used to reach v
              }
      return tree;
  }

  /**
   * Computes shortest-path distances from src vertex to target vertex of g.
   *
   * This implementation uses Dijkstra's algorithm and shortest-path tree return a
   * Positional List of vertices.
   *
   * The edge's element is assumed to be its weight as a Double.
   */
  public static <V> PositionalList<Vertex<V>> shortestPathList(Graph<V, Double> g, Vertex<V> src, Vertex<V> target) {
      PositionalList<Vertex<V>> path = new LinkedPositionalList<>();
      Map<Vertex<V>, Double> res = GraphAlgorithms.shortestPathLengths(g, src);
      Map<Vertex<V>, Edge<Double>> tree;

      tree = GraphAlgorithms.spTree(g, src, res);
 
  

      Edge<Double> arc;

      while (target != src) {
          path.addFirst(target);
          arc = tree.get(target);
          target = g.opposite(target, arc);
      }
      path.addFirst(src);

      return path;
  }


  /**
   * Computes a minimum spanning tree of connected, weighted graph g using Kruskal's algorithm.
   *
   * Result is returned as a list of edges that comprise the MST (in arbitrary order).
   */
  public static <V> PositionalList<Edge<Double>> MST(Graph<V, Double> g) {
    // tree is where we will store result as it is computed
    PositionalList<Edge<Double>> tree = new LinkedPositionalList<>();
    // pq entries are edges of graph, with weights as keys
    PriorityQueue<Double, Edge<Double>> pq = new HeapPriorityQueue<>();
    // union-find forest of components of the graph
    Partition<Vertex<V>> forest = new Partition<>();
    // map each vertex to the forest position
    Map<Vertex<V>, Position<Vertex<V>>> positions = new ProbeHashMap<>();

    // Initialize the union-find structure
    for (Vertex<V> v : g.vertices()) {
        positions.put(v, forest.makeCluster(v));
    }

    // Add all edges to the priority queue
    for (Edge<Double> e : g.edges()) {
        pq.insert(e.getElement(), e);
    }

    int size = g.numVertices();
    // while tree not spanning and unprocessed edges remain...
    while (tree.size() != size - 1 && !pq.isEmpty()) {
        Entry<Double, Edge<Double>> entry = pq.removeMin();
        Edge<Double> edge = entry.getValue();
        Vertex<V>[] endpoints = g.endVertices(edge);
        Position<Vertex<V>> a = forest.find(positions.get(endpoints[0]));
        Position<Vertex<V>> b = forest.find(positions.get(endpoints[1]));
        if (a != b) {
            tree.addLast(edge);
            forest.union(a, b);
        }
    }

    return tree;
  }
  

}