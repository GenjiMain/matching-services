package fr.jobslake.utils.taxonomy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyTest {

    @Test
    void getInstanceTest() {
        Taxonomy taxo = Taxonomy.getInstance();
        assertEquals(2, taxo.getNodes().size());
    }

    @Test
    void addNode() {

    }

    @Test
    void testAddNode() {
        Node nodeParent = new Node(0, "noeud0", null) ;
        Node nodeEnfant = new Node(1,"noeud1", nodeParent) ;
        Node nodeEnfant1 = new Node(2,"noeud1", nodeParent) ;
        Node nodeEnfant2 = new Node(1,"noeud1", nodeParent) ;

        Taxonomy taxo = Taxonomy.getInstance() ;
      //  nodeEnfant.appendChild(nodeEnfant1);
      //  nodeEnfant.appendChild(nodeEnfant2);
        taxo.addNode(nodeEnfant, taxo.root ) ;
        taxo.addNode(nodeEnfant1,nodeEnfant) ;
        System.out.println(taxo.toString()) ;

    }

    @Test
    void removeNode() {
    }
    @Test
    public void UnmarshallJsonTaxonomy() throws JsonProcessingException {
        //Given
        String taxoString = "{ \n" +
                "   \"name\":\"all skills\",\n" +
                "   \"top5_skills\":\"all_skills\",\n" +
                "   \"top_skills\":[ \n" +
                "\n" +
                "   ],\n" +
                "   \"prop_jobs\":100.0,\n" +
                "   \"mention_growth\":1.4207,\n" +
                "   \"avgsalary_range\":[ \n" +
                "\n" +
                "   ],\n" +
                "   \"topN_titles\":[ \n" +
                "      \"na\",\n" +
                "      0\n" +
                "   ],\n" +
                "   \"id\":0,\n" +
                "   \"children\":[ \n" +
                "      { \n" +
                "         \"name\":\"education, sales and marketing\",\n" +
                "         \"top5_skills\":\"product sale and delivery, sales recruiting, sales management, account management, marketing\",\n" +
                "         \"prop_jobs\":19.243,\n" +
                "         \"mention_growth\":1.3419452516996129,\n" +
                "         \"avgsalary_range\":[ \n" +
                "            24500.0,\n" +
                "            31200.0,\n" +
                "            40000.0\n" +
                "         ],\n" +
                "         \"topN_titles\":[ \n" +
                "            [ \n" +
                "               \"marketing manager\",\n" +
                "               571655\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"sales executive\",\n" +
                "               481904\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"marketing executive\",\n" +
                "               475349\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"business development manager\",\n" +
                "               376038\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"account manager\",\n" +
                "               320249\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"sales manager\",\n" +
                "               288417\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"digital marketing executive\",\n" +
                "               180516\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"marketing assistant\",\n" +
                "               178430\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"digital marketing manager\",\n" +
                "               150456\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"graphic designer\",\n" +
                "               148657\n" +
                "            ]\n" +
                "         ]\n" +
                "      },\n" +
                "      { \n" +
                "         \"name\":\" XXXX education, sales and marketing\",\n" +
                "         \"top5_skills\":\"product sale and delivery, sales recruiting, sales management, account management, marketing\",\n" +
                "         \"prop_jobs\":19.243,\n" +
                "         \"mention_growth\":1.3419452516996129,\n" +
                "         \"avgsalary_range\":[ \n" +
                "            24500.0,\n" +
                "            31200.0,\n" +
                "            40000.0\n" +
                "         ],\n" +
                "         \"topN_titles\":[ \n" +
                "            [ \n" +
                "               \"marketing manager\",\n" +
                "               571655\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"sales executive\",\n" +
                "               481904\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"marketing executive\",\n" +
                "               475349\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"business development manager\",\n" +
                "               376038\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"account manager\",\n" +
                "               320249\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"sales manager\",\n" +
                "               288417\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"digital marketing executive\",\n" +
                "               180516\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"marketing assistant\",\n" +
                "               178430\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"digital marketing manager\",\n" +
                "               150456\n" +
                "            ],\n" +
                "            [ \n" +
                "               \"graphic designer\",\n" +
                "               148657\n" +
                "            ]\n" +
                "         ]\n" +
                "      }\n" +
                "   ]\n" +
                "}" ;

        //When
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
       Node taxonomy =  mapper.readValue(taxoString , Node.class) ;
        //Then
        assertNotNull(taxonomy);
        assertEquals("all skills", taxonomy.getName());
        System.out.println(taxonomy);
        assertEquals(2, taxonomy.getChildren().size()) ;
    }


}