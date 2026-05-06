import java.util.*;

public class TerrainTilesAStarFULL {


 /*

    TIME AND SPACE COMPLEXITY

    the time complexity is O(n*m log n*m) where n is the number of rows and m is the number of columsn
    because each tile is visited at most once and from there can be inserted and popped and each priority queue operation takes log(n*m)
    so you are doing log(n*m) work n*m times which is n*m * log(n*m)


    the space complexity is O(n*m) where n is the number of rows and m is the number of columsn in the given char input matrix
    because each time the algorithm runs different variables are created which can store up to n*m such as
    the visited array, priority queue, parent pointers, and the path reconstruction
     */



    public TerrainTilesAStarFULL(){
      // constructor
    }

    public static void main(String[] args){

        char[][]input1Matrix = {
                {'H', 'P' , 'P', 'F', 'G'},
                {'F', 'M' , 'F', 'F', 'F'},
                {'P', 'P' , 'S', 'P', 'P'},
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
        System.out.println(" ");


        System.out.println("input2: mountain block with path");
        AStarAlg(grid2);
        System.out.println(" ");

        System.out.println("input3: mountain block no path");
        AStarAlg(grid3);
        System.out.println(" ");

        System.out.println("input3: super expensive but swamp should be avoided");
        AStarAlg(grid4);
        System.out.println(" ");



    }// END MAIN




    /* ********************* A Star Algorithm (find goal and traverse neighbors) ********************* */
    public static void AStarAlg(char[][] gx){

        long startTime = System.currentTimeMillis();

        int[] start = null; // arrays bc row, columns
        int[] end = null;

        // 1. looking for start(H) and end (G) tiles
        for(int i = 0; i < gx.length; i++){
            for(int j = 0; j < gx[0].length; j++){
                if(gx[i][j] == 'H'){
                    start = new int[]{i,j}; // add row/column of start tile
                }
                if (gx[i][j] == 'G'){
                    end = new int[]{i,j};
                }
            }
        }

        //2. setting up structures (visited, directions, priorityQueue)
        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(Node::f));
                // priority queue of node objects that sorts them by node.f() so whichever has smallest f gets out first

        boolean[][] visited = new  boolean[gx.length][gx[0].length]; // prevents infinite loop so you don't revisit nodes
        Node startNode = new Node(start[0], start[1], 0, heutristic(start[0],start[1], end [0], end[1]), null);
        pq.add(startNode); // add the first node to the queue to poll it later

        int nodesVisited = 0;

       int [][] directions = new int[][]{ // for which direction i go in
               {-1, 0}, // up
               {1, 0}, // down
               {0, -1}, // left
               {0, 1},// right
        };

       //3. main traversal loop until queue is empty
        while(!pq.isEmpty()){

            Node current = pq.poll();

            if (visited[current.row][current.col] == true){
                continue; // if i pop a node ive been to then i skip logic go to the next one in the queue, continue to pq.poll above
            }

            visited[current.row][current.col] = true; // mark as visited
            nodesVisited++;

            // check is goal is found
            if (current.row == end[0] && current.col == end[1]){
                // reconstruct path and print results
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;

                List<String> path = reconstructPath(current, gx); // make sure that i am printing the actual path

                printTable(nodesVisited, path, current.g, totalTime);

                return; // end since found the goal

            }// end if statement for goal found

            // if it was not the goal, explore the neighbors if they are in bounds, not visited,and not moutnains
            for (int[] direction : directions) {
                int nextRow = current.row + direction[0];
                int nextCol = current.col + direction[1];

                if (nextRow >= 0
                        && nextRow < gx.length
                        && nextCol >= 0
                        && nextCol < gx[0].length
                        && !visited[nextRow][nextCol]
                        && gx[nextRow][nextCol] != 'M'){

                    int newG = current.g +terrainCompute(gx[nextRow][nextCol]); //compute H and G for neighbors (if pass through parameters) and then add to queue
                    int newH = heutristic(nextRow, nextCol, end[0], end[1]);

                    Node neighbor = new Node(
                            nextRow, nextCol, newG, newH, current);

                    pq.add(neighbor);
                }// end if statement

            }// end for loop
        } // end while loop
        System.out.println(" No path was found ");

    } // END ASTARALG





    /* ********************* Node Class ********************* */
    public static class Node {
        int row;
        int col;
        int g; // cost so far
        int h; // heuristic to goal
        Node parent;

        Node(int row, int col, int g, int h, Node parent) {
            this.row = row;
            this.col = col;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        int f(){ // creating my f and heutristic with each node, row column
            return g + h;
        }

    } // END NODE CLASS

    public static int heutristic (int row, int col, int goalRow, int goalCol){

        return 2 * (Math.abs(goalRow - row) + Math.abs(goalCol - col));

    }// END HEURISTIC





    /* ********************* Compute Cost of Terrain ********************* */
    public static int terrainCompute(char tile){
        switch (tile){
            case 'H':
            case'G':
                case 'P':
                return 2;
            case 'F':
                return 3;
            case 'S':
                return 5;
            default:
                return Integer.MAX_VALUE; // return inifity for my mountains
        }
    } // END TERRAIN COMPUTE






                    /* ********************* Path Reconstruction ********************* */
    public static List<String> reconstructPath(Node node, char[][] grid){
        List<String> path = new ArrayList<>();

        while(node!= null){
            //path.add(node.row + "," + node.col); adds them as numbers nr change to it as letters
            path.add(String.valueOf(grid[node.row][node.col])); // each node stores parent, go backwards through to get path
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    } // END RECONSTRUCT PATH






    /* ********************* Print Table ********************* */
    public static void printTable(int nodesVisited, List<String> path, int cost, long time){
        System.out.println("===== PERFORMANCE TABLE =====");
        System.out.println("Nodes Visited: " + nodesVisited);
        System.out.println("Shortest Path Length: " + (path.size() - 1));
        System.out.println("Shortest Path: " + path);
        System.out.println("Cost of Shortest Path: " + cost);
        System.out.println("Time Taken: " + time + " ms");
    }// end print table




} // LAST BRACKET END CLASS
