public class HeuristicJustification_TakeHome1 {


    public HeuristicJustification_TakeHome1(){
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



    } // END ASTARALG




    public class Node {
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