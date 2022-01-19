
import java.util.LinkedList;
import java.util.HashMap;

public class IntuitiveTopological implements TopologicalSort {

    BetterDiGraph G;

    IntuitiveTopological(BetterDiGraph graph) {
        G = graph;
    }

    private class DepthFirstOrder {

        private boolean[] marked;
        private LinkedList<Integer> pre;
        private LinkedList<Integer> post;
        private LinkedList<Integer> reversePost;

        private DepthFirstOrder(BetterDiGraph G) {
            pre = new LinkedList<Integer>();
            post = new LinkedList<Integer>();
            reversePost = new LinkedList<Integer>();
            marked = new boolean[G.getVertexCount()];
            for (int vert = 0; vert < G.getVertexCount(); vert++) {
                if (!marked[vert]) {
                    dfs(G, vert);
                }
            }
        }

        private void dfs(BetterDiGraph G, int vert) {
            pre.add(vert);
            marked[vert] = true;
            for (int idx = 0; idx < G.getVertexCount(); idx++) {
                Object w = G.getAdj(idx);
                if (w != null && !marked[idx]) {
                    dfs(G, idx);
                }
            }
            post.add(vert);
            reversePost.push(vert);
        }

        private Iterable reversePostorder() {
            return reversePost;
        }
    }

    @Override
    public Iterable order() {
        LinkedList<Integer> sorted = new LinkedList();
        if (isDAG()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            sorted = (LinkedList<Integer>) dfs.reversePostorder();
        }
        return sorted;
    }

    @Override
    public boolean isDAG() {
        return !hasCycle();
    }

    private boolean hasCycleUtil(int idx, HashMap<Integer, Boolean> visited,
            HashMap<Integer, Boolean> recStack) {
        if (recStack.get(idx)) {
            return true;
        }
        if (visited.get(idx)) {
            return false;
        }
        visited.put(idx, true);
        recStack.put(idx, true);
        Iterable<Integer> children = G.getAdj(idx);
        for (Integer c : children) {
            if (hasCycleUtil(c, visited, recStack)) {
                return true;
            }
        }
        recStack.put(idx, false);
        return false;
    }

    private boolean hasCycle() {
        HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
        HashMap<Integer, Boolean> recStack = new HashMap<Integer, Boolean>();
        for (Object v : G.getA().keySet()) {
            visited.put((Integer) v, false);
            recStack.put((Integer) v, false);
        }
        for (Object v : G.getA().keySet()) {
            if (hasCycleUtil((Integer) v, visited, recStack)) {
                return true;
            }
        }
        return false;
    }
}
