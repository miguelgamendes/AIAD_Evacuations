package movement;

import map.EvacScene;
import sun.security.ssl.Debug;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jo�o on 28/10/2015.
 */
public class AStar {

    private int[][] map;

    private EvacScene scene;

    public static class Node {
        public int x,y;
        public Node parent;

        // Heuristics
        public double g = 0.0;
        public double h = 0.0;
        public double f = 0.0;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Node n = (Node) obj;
            return (this.x == n.x && this.y == n.y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public AStar() {
        this.scene = Utils.scene;
    }

    protected List<Node> GenerateSuccessors(Node node) {
        List<Node> ret = new LinkedList<Node>();

        int x = node.x;
        int y = node.y;

        if (y < scene.getHeight() - 1 && scene.getCell(x,y+1) != EvacScene.CellType.Wall)
            ret.add(new Node(x,y+1));

        if (x < scene.getWidth() - 1 && scene.getCell(x+1,y) != EvacScene.CellType.Wall)
            ret.add(new Node(x+1,y));

        if (y > 0 && scene.getCell(x,y-1) != EvacScene.CellType.Wall)
            ret.add(new Node(x,y-1));

        if (x > 0 && scene.getCell(x-1,y) != EvacScene.CellType.Wall)
            ret.add(new Node(x-1,y));

        return ret;
    }

    public List<Node> Compute(Node start, Node end) {

        if (start.equals(end)) {
            return null;
        }

        List<Node> closedSet = new ArrayList<Node>();
        List<Node> openSet = new ArrayList<Node>();


        openSet.add(start);

        start.g = 0;
        start.h = h(start,end);
        start.f = start.g + start.h;

        while (!openSet.isEmpty()) {
            Node currentNode = bestNode(openSet, end);
            if (currentNode.equals(end)) {
                List<Node> list = new ArrayList<Node>();
                list.add(currentNode);

                do {
                    currentNode = currentNode.parent;
                    list.add(currentNode);
                } while (currentNode.parent !=null);

                Collections.reverse(list);
                return list;
            }

            openSet.remove(currentNode);
            closedSet.add(currentNode);

            for(Node successorNode : GenerateSuccessors(currentNode)) {
                if (closedSet.contains(successorNode))
                    continue;

                Double attemptGScore = g(currentNode) + h(currentNode, successorNode);
                boolean betterAttempt;

                if (!openSet.contains(successorNode)) {
                    openSet.add(successorNode);
                    betterAttempt = true;
                } else if (attemptGScore < g(successorNode)) {
                    betterAttempt = true;
                } else {
                    betterAttempt = false;
                }

                if (betterAttempt) {
                    successorNode.parent = currentNode;
                    successorNode.g = attemptGScore;
                    successorNode.h = h(successorNode,end);
                    successorNode.f = f(successorNode,end);
                }
            }
        }

        return null;

    }

    private Node bestNode(List<Node> openSet, Node end) {
        Node ret = openSet.get(0);
        Double bestF = f(ret,end);
        for (Node n : openSet) {
            if (f(n,end) < bestF) {
                ret = n;
                bestF = f(n,end);
            }
        }
        return ret;
    }

    // --- Heuristics ---
    protected Double g(Node node) {
        Double g = 0.0;
        while (node != null) {
            g += node.g;
            node = node.parent;
        }
        return g;
    }

    protected Double h(Node from, Node to) {
        return (double) (Math.abs(from.x-to.x) + Math.abs(from.y - to.y));
    }

    protected Double f(Node from, Node to) {
        return g(from) + h(from,to);
    }

    // --- End Heuristics


}
