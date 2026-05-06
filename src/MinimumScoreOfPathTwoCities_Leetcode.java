import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MinimumScoreOfPathTwoCities_Leetcode {

    /*
    You are given a positive integer n representing n cities numbered from 1 to n. You are also given a 2D array roads where roads[i] = [ai, bi, distancei] indicates that there is a bidirectional road between cities ai and bi with a distance equal to distancei. The cities graph is not necessarily connected.

The score of a path between two cities is defined as the minimum distance of a road in this path.

Return the minimum possible score of a path between cities 1 and n.

Note:

A path is a sequence of roads between two cities.
It is allowed for a path to contain the same road multiple times, and you can visit cities 1 and n multiple times along the path.
The test cases are generated such that there is at least one path between 1 and n.
     */


    /*


    so there is a path and it has a score which is just the edge weight along the path
    i can revisit nodes(cities) reuse paths (roads) and take detours idk what that one means basically i can just move however i want

    so when i am finding a path i should just go anywehre around the whole thing looking for the smallest so basically dijkstras?
    or can i use bfs dfs since i am just traversing around but i do need the shortest paht so i feel like dijkstras is better
no idjkstra because im trying to find the path where the smallest edge is super small so like the path i take one of the edges is really small
where dijkstras is finding path with smallest total distance which isnt what im doing


so my thing doesnt need to be efficent at all i jsut have to take the path from start to goal
that includes the smallest edge in teh whole graph if its all connected or the smallest one that still would allow me to get from start to goal

so bfs dfs then track the minimum one and return or do just keep track of the path that has the smallest one


     // doing BFS like in the redoassignment weighted/unweighted
     */

    // im given roads matrix and for each node a i have a list [a, to b, cost of road]

    public int minScore(int n, int[][] roads) {

        List<List<int[]>> graph = new ArrayList<>(); // im given

        for (int i = 0; i <= n; i++) { // creating adjacency list
            graph.add(new ArrayList<>());

        }

        for (int[] road : roads) { // adding neighbors to the graph so i can do bfs
            int a= road[0], b =  road[1], d = road[2];
            graph.get(a).add(new int[]{b,d});
            graph.get(b).add(new int[]{a,d});
        }

        boolean[] visited = new boolean[n+1];
        Queue<Integer> q = new LinkedList<>();

        q.add(1);
        visited[1] = true;
        int min = Integer.MAX_VALUE;

        while (!q.isEmpty()) { // going through the graph
            int node= q.poll();
            for(int[] neighbor : graph.get(node)) {
                int nextNode = neighbor[0];
                int nextNextNode = neighbor[1];

                min = Math.min(min, nextNextNode);

                if(!visited[nextNode]){
                    visited[nextNode] = true;
                    q.add(nextNode);
                }
            }
        }

        // i need a queue so i dont go deep its so i can take them out like fifo because i want hte closest ones first


        return min;
    } // end min score


} // END CLASS LAST BRACKET



/*

TIME AND SPACE COMPLEXITY


time complexity is O(V+E) where v is the number of cities/vertices and e is the number of roads/edges because
everytime you run the algorithm you run the for loop to build the adjaency list which looks at every single road in the input matrix
then you do the actual bfs traversal where at most each city/vertex is added to the queue once because if they are all connected then you go to every city and visit and look at the neighbors


space complexity is also O(V+E) where v is the number of cities/vertices and e is the number of roads/edges because
everytime you run the algorthm you add all roads/edges and vertices to the adjacency list which stores E amount
and the visited list stores V nodes in it, same with the queue





 */
