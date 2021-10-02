package com.johnson.cannabis.services;

import com.johnson.cannabis.models.Review;
import com.johnson.cannabis.models.Strains;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StrainService {

    List<Strains> getAllStrains();

    Strains getStrainByName(String name);

    Strains getStrain(Long id);

    List<Strains> searchStrainType(String type, String theStrain, Pageable pageable, String sortType);
}
