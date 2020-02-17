package fr.jobslake.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Offers")
public class Offers {

    @Id
    private UUID id;
    private String organization;
    private String title;
    private String description;

    private Date startDate;
    private Date endDate;

}
