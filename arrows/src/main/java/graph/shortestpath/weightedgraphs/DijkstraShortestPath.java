//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package graph.shortestpath.weightedgraphs;

import graph.Edge;
import graph.MinimumSpanningTreePrimShortestPathDijkstra;
import graph.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * when the weight of edge is negative number. Dijkstra algorithm does not work.
 *
 * Scenario:
 * provide: start node and end node;
 *          It maybe a directed graph or undirected graph.
 * confirm if it is not a spanning tree: if there is circle or the end node is a single vertex
 *          (A spanning tree is a subset of Graph G, which has all the vertices covered with
 *          minimum possible number of edges.
 *          Hence, a spanning tree does not have cycles and it cannot be disconnected.)
 *
 * Find shortest path or check if there is path between the start node and the end node.
 *
 * Data structure
 * Node:
 *    map: neighbor:distance.
 *    int: current shortest path distance from start node
 *    node: predecessor node on the current shortest path.
 *
 *    Initial:
 *     -- map: If node has not path to his neighbor node, set the distance:  Integer.MAX_VALUE;
 *     -- shortest distance and predecessor node:
 *         for start node:  shortest distance  is 0;
 *                          predecessor node  is null.
 *         for other node:  shortest distance is Integer.MAX_VALUE;
 *                          predecessor node is null.
 *
 * Set<Node>:  evaluated nodes, have found the shortest paths from start node .
 * Heap<Node>: under evaluating. a border between settled node and left nodes.
 *
 * use a binary heap which keep the node position in the binary heap array to get O(logV)
 * for decrease operation
 * Stack<Node> or StringBuilder: used to calculate the shortest path.
 *
 * Algorithm:
 *
 * From start node recursively:
 *  1 Calculate current node neighbor nodes' tentative shortest distance and predecessor node, put them in bounders
 *  2 put current node into settled nodes
 *  3 if 'current node' is end node. stop
 *  4 get the top of head as the next current node
 *      if its tentative shortest path' distance is still Integer.MAX_VALUE',
 *      that means here is no connection between the start node and the remaining unvisited nodes.
 *      return "there is no path to end node"
 *      else continue loop
 *
 * @see <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Algorithm"> wiki</a>
 *
 * adjacency lists and binary heap. it is necessary to use an auxiliary
 * data structure that maps each vertex to its position in the heap, and to keep this structure up
 * to date as the priority queue Q changes. With a self-balancing binary search tree or binary heap,
 * the algorithm requires.
 *
 * `ø((|E|+|V|)log|V|)`
 *
 * time in the worst case for connected graphs this time bound
 * can be simplified to
 *
 * `ø(|E|log|V|)` "
 *
 */
public class DijkstraShortestPath {
    public static void main(String[] args) {
        // todo test dircted graph;

        /**
         * <pre>
         *          f    -- 9 --  e
         *        /   \             \
         *       /     \             \
         *     14       2            6
         *     /         \            \
         *  a   - 9 -     c   - 11 -   d
         *   \           /           /
         *    7        10          15
         *      \      /        /
         *       \    /      /
         *            b
         */
        Map<Node, Integer> startNodeDistanceTo = new HashMap();
        Map<Node, Integer> bNodeDistanceTo = new HashMap();
        Map<Node, Integer> cNodeDistanceTo = new HashMap();
        Map<Node, Integer> dNodeDistanceTo = new HashMap();
        Map<Node, Integer> eNodeDistanceTo = new HashMap();
        Map<Node, Integer> fNodeDistanceTo = new HashMap();

        Node start = new Node("a", startNodeDistanceTo);

        Node b = new Node("b", bNodeDistanceTo);
        Node c = new Node("c", cNodeDistanceTo);
        Node e = new Node("e", eNodeDistanceTo);
        Node f = new Node("f", fNodeDistanceTo);
        Node end = new Node("d", dNodeDistanceTo);

        startNodeDistanceTo.put(b, 7);
        startNodeDistanceTo.put(c, 9);
        startNodeDistanceTo.put(f, 14);

        bNodeDistanceTo.put(start, 7);
        bNodeDistanceTo.put(c, 10);
        // bNodeDistanceTo.put(c, Integer.MAX_VALUE); TEST 1
        bNodeDistanceTo.put(end, 15);
        // bNodeDistanceTo.put(end, Integer.MAX_VALUE); // TEST 2   this not path between start a
        // and end d

        cNodeDistanceTo.put(start, 9);
        cNodeDistanceTo.put(b, 10);
        cNodeDistanceTo.put(end, 11);
        // cNodeDistanceTo.put(end, Integer.MAX_VALUE); // TEST 2
        cNodeDistanceTo.put(f, 2);

        fNodeDistanceTo.put(start, 14);
        fNodeDistanceTo.put(c, 2);
        fNodeDistanceTo.put(e, 9);

        eNodeDistanceTo.put(f, 9);
        eNodeDistanceTo.put(end, 6);
        // eNodeDistanceTo.put(end, Integer.MAX_VALUE); // TEST 2

        dNodeDistanceTo.put(e, 6);
        // dNodeDistanceTo.put(e, Integer.MAX_VALUE); // TEST 2
        dNodeDistanceTo.put(c, 11);
        // dNodeDistanceTo.put(c, Integer.MAX_VALUE); // TEST 2
        dNodeDistanceTo.put(b, 15);
        // dNodeDistanceTo.put(b, Integer.MAX_VALUE); // TEST 2

        Collection<Edge> sp =
                MinimumSpanningTreePrimShortestPathDijkstra.mstOrSp(
                        new IGraphWithAdjacentNodesDijkstraImp(
                                new ArrayList<Node>() {
                                    {
                                        add(start);
                                        add(b);
                                        add(c);
                                        add(e);
                                        add(f);
                                        add(end);
                                    }
                                },
                                start,
                                end));
        sp.stream()
                .forEach(
                        edge -> {
                            System.out.println(edge.toString());
                        });
        // a-c-d
    }
}
