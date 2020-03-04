package fr.jobslake.utils.taxonomy;

import java.util.HashMap;
import java.util.Map;

public class Taxonomy {


    private static Taxonomy taxonomy_instance = null;
    private Integer id = 1;
    public Node root;
    private HashMap<Integer, Node> nodes;
    private String name ;
    private Taxonomy() {
        root = new Node(0, "root_node", null);
        nodes = new HashMap<Integer, Node>();
        nodes.put(0, root) ;
    }

    public static Taxonomy getInstance(){
        if (taxonomy_instance == null){
            taxonomy_instance = new Taxonomy();
        }
        return taxonomy_instance;
    }
    //represents Nodes that rely on this taxo
    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public Integer addNode(Node node, Node parent) {
        node.setId(this.id);
        //check if parent is null rely to root node
        parent.appendChild(node);
        //Check if parent exist and is attached to same taxonomy
        node.setParent(parent);
        //adding sublist of Nodes
        this.nodes.put(this.id, node);
        this.id++;
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
    public String toString()
    {
        StringBuilder stb = new StringBuilder();
        System.out.println("Taxonomy ======================");
        System.out.println("Nodes size:"+this.getNodes().size());
        this.getNodes().forEach((k,v)->
                {
                   System.out.println("key:"+k+"value:"+v);
                     stb.append("List Of node in Taxonomy"+k+" value:"+v);
                }
                );

        return stb.toString();
        }

}
