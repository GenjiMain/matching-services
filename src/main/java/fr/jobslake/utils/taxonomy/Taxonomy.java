package fr.jobslake.utils.taxonomy;


import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import fr.jobslake.utils.exceptions.NotSameLengthAsTaxonomyDepthException;

import java.util.*;
import java.util.function.BinaryOperator;

public class Taxonomy {

    private static Taxonomy taxonomy_instance = null;

    private Integer id = 1;
    public Node root;
    private HashMap<Integer, Node> nodes;

    private Taxonomy() {
        root = new Node(0, "root_node", null);
        nodes = new HashMap<Integer, Node>();
        this.nodes.put(root.getId(), root);

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

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public Integer  getIdFromNodeName(String nodeName){
        boolean found = false;
        Integer id = null;
        HashMap<Integer, Node> nodes  = this.nodes;
        Iterator nodesIter = nodes.entrySet().iterator();
        while (!found & nodesIter.hasNext()) {

                Map.Entry pair = (Map.Entry) nodesIter.next();
                if (((Node) pair.getValue()).name == nodeName) {
                    found = true;
                    id = (Integer) pair.getKey();
                    return id ;
                }
            }
        return null ;
    }

    public List<String> getAllAntecedent(String nodeName) throws NotInTaxonomyException {
        // get all node name antecedents without root_node
        List <String> antecedents = new ArrayList<String>();
        Node node = getInstanceFromNodeName(nodeName);
        antecedents.add(node.name);
        while(!(node.getParent().name == "root_node")){
        // while(!(node.getParent() == null)){
            antecedents.add(node.getParent().name);
            node = node.getParent();
        }
        return antecedents;
    }

    public List<String> getAllLeavesName(){
        // get all leaves node name in taxonomy
        List <String> leavesName = new ArrayList<String>();
        HashMap<Integer, Node> ids_nodes  = this.nodes;

        Iterator nodesIter = ids_nodes.entrySet().iterator();
        while (nodesIter.hasNext()) {

            Map.Entry pair = (Map.Entry) nodesIter.next();

            if (((Node) pair.getValue()).getChildren().isEmpty()) {
                    leavesName.add(((Node) pair.getValue()).name) ;
            }
        }
        return leavesName;

    }
    public int getDepthOfTaxonomy (){
        // get the depth of the taxonomy
        Node node = this.root;
        int depth = 0 ;
        boolean getToBottom = false ;
        while(!node.getChildren().isEmpty()){
            node = node.getChildren().get(0);
            depth+=1;
        }
        return depth;
    }
    public Node getInstanceFromId(Integer id ){
        boolean found = false ;
        HashMap<Integer, Node> ids_nodes  = this.nodes;

        Iterator iter = ids_nodes.entrySet().iterator();
        while (!found & iter.hasNext()) {

            Map.Entry pair = (Map.Entry) iter.next();
            if (((Node) pair.getValue()).getId() == id) {
                found = true;
                Node node = (Node) pair.getValue();
                return node;

            }
        }
        return null;
    }


    public Node getInstanceFromNodeName(String nodeName ) throws NotInTaxonomyException {
        boolean found = false;
        Node node = null;
        HashMap<Integer, Node> ids_nodes = this.nodes;
        Iterator nodesIter = ids_nodes.entrySet().iterator();
        if ( isInTaxonomy(nodeName)) {

            while (!found & nodesIter.hasNext()) {

                Map.Entry pair = (Map.Entry) nodesIter.next();
                if (((Node) pair.getValue()).name.equals(nodeName)) {
                    found = true;
                    node = (Node) pair.getValue();
                }
            }
        }
        return node;

    }

    public Boolean isInTaxonomy(String nodeName) throws NotInTaxonomyException {
        // check if a given node name exists in the taxonomy
        HashMap<Integer, Node> ids_nodes = this.nodes;
        Iterator iter = ids_nodes.entrySet().iterator();
        boolean found = false;
        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();
            if (((Node) pair.getValue()).name.equalsIgnoreCase(nodeName)) {
                found = true;
            }
        }
        if(!found){
            throw new NotInTaxonomyException(nodeName);
        }
        return found;
    }

    public Boolean isLeaf(String nodeName) throws NotLeafException, NotInTaxonomyException {
        // check if a given nameNode is a leaf node or not
        Node node = getInstanceFromNodeName(nodeName);
        boolean isLeaf = false ;
        if(node.getChildren().isEmpty()) {
            isLeaf = true;
        }
        if(!isLeaf){
            throw new NotLeafException(nodeName) ;
        }
        return isLeaf;
    }

    public Boolean hasSameParent(String nodeName1, String nodeName2) throws NotInTaxonomyException {
        Node node1 = getInstanceFromNodeName(nodeName1) ;
        Node node2 = getInstanceFromNodeName(nodeName2);

        // check if two nodes have the same parent or not
        if ((node1.getParent()) == (node2.getParent())){
            return true ;
        }else {return false ; }
    }

    public Double nodesSimilarity(String nodeName1 , String nodeName2, List<Double> weights ) throws NotSameLengthAsTaxonomyDepthException, NotInTaxonomyException ,NotLeafException {

        String name1, name2;
        int level = 0;
        name1 = nodeName1;
        name2 = nodeName2;
        boolean gotToRoot = getInstanceFromNodeName(name1).name.equals("root_node") || getInstanceFromNodeName(name2).name.equals("root_node");;
        double score = 0.0;

        // check if the taxonomy depth is equal to the length of the level weights
        /*
        if (weights.size() != this.getDepthOfTaxonomy()) {
            throw new NotSameLengthAsTaxonomyDepthException(this.getDepthOfTaxonomy());
        }
        */
        // check if the two nodes are leaves or not


        if (isLeaf(name1) & isLeaf(name2)) {
            if (name1.equals(name2)) {
                score = Math.pow(weights.get(level), 2) + Math.pow(weights.get(level + 1), 2) + Math.pow(weights.get(level + 2), 2);

            } else {
                score += 0.0;
                while (!gotToRoot) {
                    if (hasSameParent(name1, name2)) {
                        score += Math.pow(weights.get(level + 1), 2);
                    } else {
                        score += 0.0;
                    }
                    level += 1;
                    name1 = getInstanceFromNodeName(name1).getParent().name;
                    name2 = getInstanceFromNodeName(name2).getParent().name;

                    if (getInstanceFromNodeName(name1).getParent().name.equals("root_node") || getInstanceFromNodeName(name2).getParent().name.equals("root_node")) {
                        gotToRoot = true;
                    } else {
                        gotToRoot = false;
                    }
                }

            }
        }
        return score;

    }
    public void removeNode(Integer nodeID){
        this.nodes.remove(nodeID);
    }


}

