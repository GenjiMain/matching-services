package fr.jobslake.utils;

import fr.jobslake.utils.taxonomy.Node;

import java.util.HashMap;

public class Taxonomy {

    private static Taxonomy taxonomy_instance = null;

    private Integer id = 1;
    public Node root;
    private HashMap<Integer, Node> nodes;

    private Taxonomy() {
        root = new Node(0, "root_node", null);
        nodes = new HashMap<Integer, Node>();
    }

    public static Taxonomy getInstance(){
        if (taxonomy_instance == null){
            taxonomy_instance = new Taxonomy();
        }
        return taxonomy_instance;
    }

    public Integer addNode(Node node, Node parent) {
        node.setId(this.id);
        parent.appendChild(node);
        node.setParent(parent);
        this.nodes.put(this.id, node);
        this.id ++;
        return this.id;
    }

    public Integer addNode(Node node, Integer parentId) {
        node.setId(this.id);
        Node parent = this.nodes.get(parentId);
        return this.addNode(node, parent);
    }

    public void removeNode(Integer nodeID){
        this.nodes.remove(nodeID);
    }

}
