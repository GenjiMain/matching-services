package fr.jobslake.dto;

import fr.jobslake.document.Offers;
import fr.jobslake.repository.OfferRepository;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.Date;
@Getter
@Setter
public class OffersSearchDTO {

    private String organization;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Integer offset ;
    private Integer  limit ;
    public OffersSearchDTO(Offers offers)
    {
        this.description = offers.getDescription() ;
        this.title = offers.getTitle() ;
    }

}
