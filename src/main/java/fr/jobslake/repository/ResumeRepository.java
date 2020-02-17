package fr.jobslake.repository;

import fr.jobslake.document.Resume;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResumeRepository extends PagingAndSortingRepository<Resume, UUID> {


}
