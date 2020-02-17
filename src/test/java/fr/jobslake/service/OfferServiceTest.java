package fr.jobslake.service;

import com.palantir.docker.compose.DockerComposeExtension;
import fr.jobslake.document.Offers;
import fr.jobslake.dto.OffersDTO;
import fr.jobslake.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OfferServiceTest  {
    @Autowired
    private OfferService offerService ;


    @RegisterExtension
    public static DockerComposeExtension docker = DockerComposeExtension.builder()
            .file("src/test/resources/test-compose.yml")
            .build();

    @Test
    void testListAllOffers() {
        offerService.deleteAll();
        OffersDTO offers1 = OffersDTO.builder().description("description1").endDate(new Date()).organization("organization1").startDate(new Date()).build() ;
        OffersDTO offers2 = OffersDTO.builder().description("description2").endDate(new Date()).organization("organization2").startDate(new Date()).build() ;
        offerService.createOffer(offers1) ;
        offerService.createOffer(offers2) ;
        assertEquals(2, offerService.listAllOffers().size());


    }

    @Test
    void filterData() {
        //Given

        //When

        //Then
    }

    @Test
    void createOfferTest() throws ParseException {
        //Given
        Date dateCreation = DateUtil.stringToDateFormat("14022002") ;
        Date dateFin = DateUtil.stringToDateFormat("14022004") ;
        offerService.deleteAll();
        OffersDTO offersDTO = OffersDTO.builder().description("description").organization("organization").startDate(dateCreation)
                .endDate(dateFin).build() ;
        //When
        Offers offers = offerService.createOffer(offersDTO) ;
        //Then
        assertEquals("description" , offers.getDescription());
        assertEquals("organization" , offers.getOrganization());
        assertEquals("14022002", DateUtil.dateToStringFormat(offers.getStartDate()));
    }

    @Test
    void updateOffer() {
    }

    @Test
    void deleteOfferById() {
    }
}