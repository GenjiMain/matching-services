package fr.jobslake.dto;

import fr.jobslake.document.Offers;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Builder
@ApiModel(description = "offers for organization")
public class OffersDTO  implements Serializable  {
    private ModelMapper modelMapper ;

    @Getter
    @Setter
    private UUID id ;

    @ApiModelProperty(notes = "organization concerned by offer", example = "organization", required = true, position = 0)
    private String organization;
    @ApiModelProperty(notes = "title of offers ", example = "title", required = true, position = 1)
    private String title;
    @ApiModelProperty(notes = "description of offers ", example = "description", required = true, position = 2)
    private String description;
    @ApiModelProperty(notes = "starting date of offers ", example = "start date", required = false, position = 3)
    private Date startDate;
    @ApiModelProperty(notes = "end date of offers ", example = "end date", required = false, position = 4)
    private Date endDate;

    public OffersDTO(String organization, String title, String description, Date startDate, Date endDate) {
        this.organization = organization;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static OffersDTOBuilder builder() {
        return new OffersDTOBuilder();
    }



    public   Offers buildOffersByOffersDTO(OffersDTO offersDTO)
    {
        return  modelMapper.map(offersDTO , Offers.class);
    }

    public String getOrganization() {
        return this.organization;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static class OffersDTOBuilder {
        private String organization;
        private String title;
        private String description;
        private Date startDate;
        private Date endDate;

        OffersDTOBuilder() {
        }

        public OffersDTO.OffersDTOBuilder organization(String organization) {
            this.organization = organization;
            return this;
        }

        public OffersDTO.OffersDTOBuilder title(String title) {
            this.title = title;
            return this;
        }

        public OffersDTO.OffersDTOBuilder description(String description) {
            this.description = description;
            return this;
        }

        public OffersDTO.OffersDTOBuilder startDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }

        public OffersDTO.OffersDTOBuilder endDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }

        public OffersDTO build() {
            return new OffersDTO(organization, title, description, startDate, endDate);
        }

        public String toString() {
            return "OffersDTO.OffersDTOBuilder(organization=" + this.organization + ", title=" + this.title + ", description=" + this.description + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ")";
        }
    }
}
