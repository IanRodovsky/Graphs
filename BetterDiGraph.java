
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;

public class BetterDiGraph implements EditableDiGraph {

    private int V;
    private int E;
    private HashMap<Integer, String> v;
    private HashMap<Integer, HashSet<Integer>> adj;

    public BetterDiGraph() {
        this.v = new HashMap<>();
        this.adj = new HashMap<>();
    }

    @Override
    public void addEdge(int v, int w) {
        addVertex(v);
        addVertex(w);
        if (this.adj.containsKey(v)) {
            if (!this.adj.get(v).contains(w)) {
                this.adj.get(v).add(w);
                this.E++;
            }
        }
    }

    public void addEdge(int v, int w, String valOne, String valTwo) {
        addVertex(v, valOne);
        addVertex(w, valTwo);
        if (this.adj.containsKey(v)) {
            if (!this.adj.get(v).contains(w)) {
                this.adj.get(v).add(w);
                this.E++;
            }
        }
    }

    public void addVertex(int v, String val) {
        if (!this.adj.containsKey(v)) {
            this.v.put(v, val);
            addVertex(v);
        }
    }

    @Override
    public void addVertex(int v) {
        if (!this.adj.containsKey(v)) {
            this.adj.put(v, new HashSet<>());
            this.V++;
        }
    }

    @Override
    public Iterable getAdj(int v) {
        return this.adj.get(v);
    }

    @Override
    public int getEdgeCount() {
        return this.E;
    }

    @Override
    public int getIndegree(int v) throws NoSuchElementException {
        if (!containsVertex(v)) {
            throw new NoSuchElementException();
        }
        LinkedList<Integer> inList = new LinkedList<Integer>();
        for (Integer to : this.adj.keySet()) {
            for (Integer e : this.adj.get(to)) {
                if (e.equals(v)) {
                    inList.add(to);
                }
            }
        }
        return inList.size();
    }

    @Override
    public int getVertexCount() {
        return this.V;
    }

    @Override
    public void removeEdge(int v, int w) {
        if (this.adj.containsKey(v) && this.adj.containsKey(w)) {
            if (this.adj.get(v).contains(w)) {
                this.adj.get(v).remove(w);
                this.E--;
            }
        }

    }

    public HashMap getV() {
        return this.v;
    }

    public HashMap getA() {
        return this.adj;
    }

    @Override
    public void removeVertex(int v) {
        if (this.adj.containsKey(v)) {
            this.adj.remove(v);
            for (Map.Entry<Integer, HashSet<Integer>> entry : this.adj.entrySet()) {
                if (entry.getValue().contains(v)) {
                    this.adj.get(entry.getKey()).remove(v);
                }
            }
            this.V--;
        }
    }

    @Override
    public Iterable vertices() {
        LinkedList<String> inList = new LinkedList<String>();
        for (String to : this.v.values()) {
            if (!inList.contains(to)) {
                inList.add(to);
            }
        }
        return inList;
    }

    public boolean containsVertex(int v) {
        return this.adj.containsKey(v);
    }
}
