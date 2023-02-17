package com.progml.imageszsone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.progml.imageszsone.model.MarkedZone;

import java.util.List;

@Repository
public interface MarkedZoneRepository extends JpaRepository<MarkedZone, Long> {
    List<MarkedZone> getAllByImageId(long imageId);
    List<MarkedZone> findAllByTags(String tag);
}
