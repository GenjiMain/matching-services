package fr.jobslake.service;

import fr.jobslake.document.Offers;
import fr.jobslake.dto.OffersDTO;
import fr.jobslake.dto.OffersSearchDTO;
import fr.jobslake.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OfferService {

    private MongoTemplate mongoTemplate;
    private OfferRepository offerRepository;
    private ModelMapper modelMapper ;
    public OfferService(OfferRepository offerRepository ,MongoTemplate mongoTemplate ,ModelMapper modelMapper)
    {
        this.offerRepository = offerRepository ;
        this.modelMapper = modelMapper ;
        this.mongoTemplate = mongoTemplate ;
    }

    public List<Offers> listAllOffers() {
        return   StreamSupport.stream(offerRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }
    public Page<Offers> filterData(OffersSearchDTO searchDTO)
    {
        List<Offers> list = new ArrayList<>();
        Integer offset = Optional.ofNullable(searchDTO.getOffset()).orElse(0);
        Integer limit = Optional.ofNullable(searchDTO.getLimit()).orElse(10);
        int page = offset / limit;
        Pageable pageable = PageRequest.of(page, limit);
        Query query = new Query();

        if (!StringUtils.isEmpty(searchDTO.getDescription())) {
            query.addCriteria(Criteria.where("description").is(searchDTO.getDescription()));
        }
        query.with(pageable);
        list = mongoTemplate.find(query, Offers.class);
        return PageableExecutionUtils.getPage(list, pageable,
                ()-> mongoTemplate.count(query, Offers.class));
    }


    public Offers createOffer(OffersDTO offersDTO) {
        Offers offers = modelMapper.map(offersDTO, Offers.class);
        offers.setId( UUID.randomUUID());
        return offerRepository.save(offers);
    }


    public Offers updateOffer(OffersDTO offersDTO) {
        Offers offers = modelMapper.map(offersDTO , Offers.class);
        return  offerRepository.save(offers );
    }
    public void deleteOfferById(UUID offerId)
    {
         offerRepository.deleteById(offerId);

    }
    public void deleteAll()
    {
        offerRepository.deleteAll();

    }

}
