package fr.jobslake.utils.taxonomy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Node {

    private Integer id;
    public String name;
    protected ArrayList<Node> children = new ArrayList<Node>();
    protected Node parent;
    protected ArrayList<String> top_skills;

    public Node (){}

    public Node(String name, Node parent) {
        this.name = name;
        this.parent = parent;
    };

    public Node(Integer id, String name, Node parent) {
        this(name, parent);
        this.id = id;
    };

    public Node(String name, Node parent, ArrayList<Node> children) {
        this(name, parent);
        this.children = children;
    };

    public ArrayList<Node> getChildren() {
        return children;
    }

    public Node getParent(){
        return parent;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void appendChild(Node child) {
        this.children.add(child);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTop_skills() {
        return top_skills;
    }

    public void setTop_skills(ArrayList<String> top_skills) {
        this.top_skills = top_skills;
    }

    public void appendSkill(String skill){
        this.top_skills.add(skill);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }

    private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(name);
        buffer.append('\n');
        for (Iterator<Node> it = children.iterator(); it.hasNext();) {
            Node next = it.next();
            if (it.hasNext()) {
                next.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            } else {
                next.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
            }
        }
    }
}
