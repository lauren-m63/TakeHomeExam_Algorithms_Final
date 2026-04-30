import java.util.*;

public class TerrainTilesAStar {


    public TerrainTilesAStar(){
      //
    }

    /*

    heuristic is usually the Euclidean distance which is the squared and sqaure rooted x y
    or the manhattan distance which is xs and ys difference absolute value added
     */


    /*

    contents
    the algorithm
    node class
    where i do heuristic math
    somewhere terrain costs
    method to do my printing or i cna combined top two
    paht reconstruction

     */

    public static void main(String[] args){

        char[][]input1Matrix = {
                {'H', 'P' , 'P', 'F', 'G'},
                {'F', 'M' , 'F', 'F', 'F'},
                {'P', 'P' , 'S', 'P', 'P'},
        };

        AStarAlg(input1Matrix);

    }// END MAIN

    public static void AStarAlg(char[][] gx){

        long startTime = System.currentTimeMillis();

        int[] start = null;
        int[] end = null;

        // looking for the start and end tiles
        for(int i = 0; i < gx.length; i++){
            for(int j = 0; j < gx[0].length; j++){
                if(gx[i][j] == 'H'){
                    start = new int[]{i,j};
                }
                if (gx[i][j] == 'G'){
                    end = new int[]{i,j};
                }
            } // end j loop
        } // end i loop


        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(Node::f));
                // priority queue of node objects that sorts them by node.f() so whichever has smallest f gets out first

        boolean[][] visited = new  boolean[gx.length][gx[0].length];

        Node startNode = new Node(start[0], start[1], 0, heutristic(start[0],start[1], end [0], end[1]), null);

        pq.add(startNode);

        int nodesVisited = 0;


       int [][] directions = new int[][]{ // for which direction igo in
               {-1, 0}, // up
               {1, 0}, // down
               {0, -1}, // left
               {0, 1},// right
        };

        while(!pq.isEmpty()){

            Node current = pq.poll();

            if (visited[current.row][current.col]){
                continue; // if ive alreayd been there
            }

            visited[current.row][current.col] = true;
            nodesVisited++;

            // if i find the goal tile i am gonna print my table about it
            if (current.row == end[0] && current.col == end[1]){
                // CALL PRINTING METHOD
                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;

                List<String> path = new ArrayList<>();

                printTable(nodesVisited, path, current.g, totalTime);

                return; // end since found the goal

            }// end if statement for goal found


            for (int[] direction : directions) {
                int nextRow = current.row + direction[0];
                int nextCol = current.col + direction[1];

                if (nextRow >= 0
                        && nextRow < gx.length
                        && nextCol >= 0
                        && nextCol < gx[0].length
                        && !visited[nextRow][nextCol]
                        && gx[nextRow][nextCol] != 'M'){

                    int newG = current.g +terrainCompute(gx[nextRow][nextCol]);

                    int newH = heutristic(nextRow, nextCol, end[0], end[1]);

                    Node neighbor = new Node(
                            nextRow, nextCol, newG, newH, current);

                    pq.add(neighbor);
                }// end if statement

            }// end for loop


        } // end while loop

        System.out.println(" No path was found ");





    } // END ASTARALG




    public static class Node {
        int row;
        int col;
        int g;
        int h;
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



    public static void printTable(int nodesVisited, List<String> path, int cost, long time){
        System.out.println("===== PERFORMANCE TABLE =====");
        System.out.println("Nodes Visited: " + nodesVisited);
        System.out.println("Shortest Path Length: " + (path.size() - 1));
        System.out.println("Shortest Path: " + path);
        System.out.println("Cost of Shortest Path: " + cost);
        System.out.println("Time Taken: " + time + " ms");
    }// end print table

    public static List<String> reconstructPath(Node node){
        List<String> path = new ArrayList<>();

        while(node!= null){
            path.add(node.row + "," + node.col);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    } // END RECONSTRUCT PATH



} // LAST BRACKET END CLASS






//public char[] convertMatrixToChar(char[][] charMatrix) {
//    int[][] intMatrix = new int[charMatrix.length][charMatrix[0].length];
//    for (int i = 0; i < charMatrix.length; i++) {
//        for (int j = 0; j < charMatrix[i].length; j++) {
//            if(charMatrix[i][j] == 'H'){intMatrix[i][j] = 2; };
//            if(charMatrix[i][j] == 'G'){intMatrix[i][j] = 2; };
//            if(charMatrix[i][j] == 'P'){intMatrix[i][j] = 2; };
//            if(charMatrix[i][j] == 'S'){intMatrix[i][j] = 2; };
//            if(charMatrix[i][j] == 'M'){intMatrix[i][j] = ; };
//
//        }
//    }
//
//} //END CONVERTMATRIXTOCHAR