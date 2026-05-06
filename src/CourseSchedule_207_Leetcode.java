import java.util.ArrayList;
import java.util.List;

public class CourseSchedule_207_Leetcode {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //Doing DFS

        List<List<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }

        for(int[] pre : prerequisites){
            graph.get(pre[1]).add(pre[0]);

        }

        int[] visited = new int[numCourses];

        for(int i = 0; i < numCourses; i++){
            if(visited[i] == 0){
                if(!dfs(i, graph, visited)){
                    return false;
                }
            }
        }


        return true;
    }// end method


    public boolean dfs(int node, List<List<Integer>> graph, int[] visited){
        if(visited[node] == 1) return false;
        if(visited[node] == 2) return true;

        visited[node] = 1;

        for (int neighbor : graph.get(node)) {
            if (!dfs(neighbor, graph, visited)) {
                return false;
            }
        }

        visited[node] = 2;

        return true;
    }


}// END CLASS
