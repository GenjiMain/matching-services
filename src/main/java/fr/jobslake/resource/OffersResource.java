package fr.jobslake.resource;


import fr.jobslake.document.Offers;
import fr.jobslake.dto.OffersDTO;
import fr.jobslake.service.OfferService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/offers")
@RestController
public class OffersResource {
    private OfferService offerService ;

    public OffersResource(OfferService offerService)
    {
        this.offerService = offerService ;
    }

    @ApiOperation(value = "List offers", tags = { "offers" })
    public List<Offers> listAllOffers() {
        return offerService.listAllOffers() ;
    }

    @ApiOperation(value = "Update an existing offers", tags = { "offers" })
    @PutMapping(value = "/{offerId}")
    public Offers updateOffers(
            @ApiParam("Id of the offers to be update. Cannot be empty.")
            @PathVariable UUID offerId,
            @ApiParam("Contact to update. Cannot null or empty.")
            @Valid @RequestBody OffersDTO offersDTO) {
         return offerService.updateOffer(offersDTO) ;
    }
    @ApiOperation(value = "create Offers", notes = "create offers", tags = { "offers" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response=Offers.class )  })
    @PostMapping(path = "/offer")
    public Offers addOffer(@ApiParam("Offers  to add. Cannot be null or empty.") @RequestBody OffersDTO offersDTO)
    {
        return offerService.createOffer(offersDTO) ;
    }
    @ApiOperation(value = "Deletes offers", tags = { "offers" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation"),
            @ApiResponse(code = 404, message = "Contact not found") })
    @DeleteMapping(path="/{offerId}")
    public void deleteOfferById(
            @ApiParam("Id of the offers to be delete. Cannot be empty.")
            @PathVariable UUID offerId) {
       offerService.deleteOfferById(offerId);
    }
}
