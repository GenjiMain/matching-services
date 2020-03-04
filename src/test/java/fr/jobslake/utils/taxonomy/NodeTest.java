package fr.jobslake.utils.taxonomy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void setIdtest() {
        //Given
         Node node = new Node("name" , null ) ;
         Node node1 = new Node(1, "name" , null ) ;


        //When
         assertNull(node.getId());
         assertEquals(1 , node1.getId());
        // Then


    }



    @Test
    void getParentTest() {
        //Given
        Node nodeParent = new Node("name",null );
        //node1 rely on node
        Node node1 = new Node("name1",nodeParent );
        node1.setTop_skills(Arrays.asList("skill1","skill2","skill3"));
        Node node2 = new Node("name2" , nodeParent) ;

        //Then
        assertNotNull(node1.getParent()) ;
        assertEquals("name" , node1.getParent().getName());
        assertNull(node1.getParent().getParent());
        assertNotNull(node2.getParent()) ;
        assertEquals("name" , node2.getParent().getName());
        //assert They had same Parent
        assertEquals(node1.getParent() , node2.getParent());
        assertEquals(3, node1.getTop_skills().size());

    }

    @Test
    void setParent() {
    }

    @Test
    void appendChild() {
    }

    @Test
    void setName() {
    }

    @Test
    void getName() {
    }

    @Test
    void getTop_skills() {
    }

    @Test
    void setTop_skills() {
    }

    @Test
    void appendSkill() {
    }
}