package com.johnson.cannabis.services;

import com.johnson.cannabis.models.Review;
import com.johnson.cannabis.models.User;
import com.johnson.cannabis.utils.CustomComparator;
import com.johnson.cannabis.models.Strains;
import com.johnson.cannabis.repositories.StrainRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
public class StrainServiceImpl implements StrainService {

    @Autowired
    private StrainRepository strainRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    public List<Strains> getAllStrains(){
        return strainRepository.findAll();
    }

    public Strains getStrainByName(String name){

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String emailAddr = loggedInUser.getName();
        User user = userService.findByEmail(emailAddr);

        try {
            Collection<Strains> strains = user.getFavorites();

            Strains strain = strainRepository.findByStrain(name);

            for (Strains s : strains){
                if (s.getStrain().equalsIgnoreCase(name)){
                    strain.setUsersFavorite(true);
                    break;
                }
            }

            if (strain.getUsersFavorite() == null) strain.setUsersFavorite(false);

            return strainRepository.findByStrain(name);

        } catch (Exception ex){

        }

        return strainRepository.findByStrain(name);

    }

    public Strains getStrain(Long id){
        return strainRepository.findById(id).get();
    }



    public List<Strains> searchStrainType(String type, String theStrain, Pageable pageable, String sortType) {

        List<Strains> typeFilteredStrains;

        if (type.length() == 0) {
            typeFilteredStrains = strainRepository.findAll();
        } else {
            typeFilteredStrains = strainRepository.findBySType(type);
        }

        List<Strains> strainFiltered = strainRepository.findByStrainContainingIgnoreCase(theStrain);

        Collection<Strains> fullyFiltered = CollectionUtils.intersection(typeFilteredStrains, strainFiltered);
        List theFinalList = new ArrayList(fullyFiltered);

        if (sortType.toLowerCase().equals("rating")) {
            theFinalList.sort(Comparator.comparing(Strains::getId));
            Collections.sort(theFinalList, new CustomComparator());
        } else {
            theFinalList.sort(Comparator.comparing(Strains::getStrain));
        }

        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > theFinalList.size() ? theFinalList.size() : (start + pageable.getPageSize());

        if (start < end) {
            Page<Strains> pages = new PageImpl<Strains>(theFinalList.subList((int) start, (int) end), pageable, theFinalList.size());
            return pages.getContent();
        }

        return new ArrayList<>();
    }
}
