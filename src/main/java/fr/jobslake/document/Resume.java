package fr.jobslake.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "Resume")
public class Resume {

    @Id
    private Long id;

    private String position;
    private List<Experience> experiences;
    private List<Education> educations;
    private List<Certification> certifications;
    private List<Skill> skills;

    public static class Education {
    }

    public static class Experience {

        private String title;
        private String description;
        private String organization;
        private Date start_date;
        private Date end_date;

    }
}
