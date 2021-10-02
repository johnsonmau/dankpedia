package com.johnson.cannabis.repositories;

import com.johnson.cannabis.models.Strains;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StrainRepository extends JpaRepository<Strains, Long> {
    List<Strains> findByStrainContainingIgnoreCase(String strain);
    Strains findByStrain(String strain);
    List<Strains> findBySType(String type);
}
