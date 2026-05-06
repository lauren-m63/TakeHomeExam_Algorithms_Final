import java.util.*;

public class TerrainTilesAStarBASIC {


    /*
    TIME AND SPACE COMPLEXITY

    time: O(n*m log(n*m))
    because each tile is visited at most once and each PQ operation is log(n*m)

    space: O(n*m)
    because of visited array, priority queue, parent pointers, and path storage
    */


    public static void main(String[] args){

        char[][] input1Matrix = {
                {'H', 'P', 'P', 'F', 'G'},
                {'F', 'M', 'F', 'F', 'F'},
                {'P', 'P', 'S', 'P', 'P'},
        };

        char[][] grid2 = {
                {'H','P','M','M','M'},
                {'P','P','M','P','G'},
                {'P','P','P','P','P'}
        };

        char[][] grid3 = {
                {'H','M','M','M','G'},
                {'M','M','M','M','M'},
                {'P','P','P','P','P'}
        };

        char[][] grid4 = {
                {'H','P','P','P','G'},
                {'S','S','S','S','S'},
                {'P','P','P','P','P'}
        };

        System.out.println("input1: path found");
        AStarAlg(input1Matrix);
        System.out.println();

        System.out.println("input2: mountain block with path");
        AStarAlg(grid2);
        System.out.println();

        System.out.println("input3: mountain block no path");
        AStarAlg(grid3);
        System.out.println();

        System.out.println("input4: super expensive but swamp should be avoided");
        AStarAlg(grid4);
        System.out.println();
    }


    /* ********************* A Star Algorithm (find goal and traverse neighbors) ********************* */
    public static void AStarAlg(char[][] gx){

        long startTime = System.currentTimeMillis();

        int[] start = null;
        int[] end = null;

        // find start (H) and goal (G)
        for(int i = 0; i < gx.length; i++){
            for(int j = 0; j < gx[0].length; j++){
                if(gx[i][j] == 'H'){
                    start = new int[]{i,j};
                }
                if(gx[i][j] == 'G'){
                    end = new int[]{i,j};
                }
            }
        }

        // priority queue sorted by f = g + h
        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(Node::f));

        boolean[][] visited = new boolean[gx.length][gx[0].length];

        Node startNode = new Node(
                start[0], start[1],
                0,
                heuristic(start[0], start[1], end[0], end[1]),
                null);

        pq.add(startNode);

        int nodesVisited = 0;

        int[][] directions = {
                {-1,0},{1,0},{0,-1},{0,1}
        };

        // main A* loop
        while(!pq.isEmpty()){

            Node current = pq.poll();

            if(visited[current.row][current.col]){
                continue; // skip duplicates
            }

            visited[current.row][current.col] = true;
            nodesVisited++;

            // goal check
            if(current.row == end[0] && current.col == end[1]){

                long endTime = System.currentTimeMillis();

                List<String> path = reconstructPath(current, gx);

                printTable(nodesVisited, path, current.g, endTime - startTime);

                return;
            }

            // explore neighbors
            for(int[] dir : directions){

                int nr = current.row + dir[0];
                int nc = current.col + dir[1];

                if(nr < 0 || nc < 0 || nr >= gx.length || nc >= gx[0].length)
                    continue;

                if(visited[nr][nc] || gx[nr][nc] == 'M')
                    continue;

                int newG = current.g + terrainCompute(gx[nr][nc]);
                int newH = heuristic(nr, nc, end[0], end[1]);

                pq.add(new Node(nr, nc, newG, newH, current));
            }
        }

        System.out.println("No path found");
    }


    /* ********************* Node Class ********************* */
    public static class Node {

        int row;
        int col;
        int g;
        int h;
        Node parent;

        Node(int row, int col, int g, int h, Node parent){
            this.row = row;
            this.col = col;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        int f(){
            return g + h;
        }
    }


    /* ********************* heuristic ********************* */
    public static int heuristic(int row, int col, int goalRow, int goalCol){

        return 2 * (Math.abs(goalRow - row) + Math.abs(goalCol - col));
    }


    /* ********************* terrainCompute ********************* */
    public static int terrainCompute(char tile){

        switch(tile){
            case 'H':
            case 'G':
            case 'P':
                return 2;
            case 'F':
                return 3;
            case 'S':
                return 5;
            default:
                return Integer.MAX_VALUE;
        }
    }


    /* ********************* reconstructPath ********************* */
    public static List<String> reconstructPath(Node node, char[][] grid){

        List<String> path = new ArrayList<>();

        while(node != null){
            path.add(String.valueOf(grid[node.row][node.col]));
            node = node.parent;
        }

        Collections.reverse(path);
        return path;
    }


    /* ********************* printTable ********************* */
    public static void printTable(int nodesVisited, List<String> path, int cost, long time){

        System.out.println("===== PERFORMANCE TABLE =====");
        System.out.println("Nodes Visited: " + nodesVisited);
        System.out.println("Shortest Path Length: " + (path.size() - 1));
        System.out.println("Shortest Path: " + path);
        System.out.println("Cost of Path: " + cost);
        System.out.println("Time Taken: " + time + " ms");
    }

} // END CLASS