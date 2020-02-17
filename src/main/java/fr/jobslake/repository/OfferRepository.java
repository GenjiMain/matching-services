package fr.jobslake.repository;

import fr.jobslake.document.Offers;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;



@Repository
public interface OfferRepository extends  PagingAndSortingRepository<Offers, UUID>  {

}
