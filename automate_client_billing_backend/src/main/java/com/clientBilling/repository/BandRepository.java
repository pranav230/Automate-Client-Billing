package com.clientBilling.repository;

import com.clientBilling.entity.Band;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("BandRepo")
public interface BandRepository extends CrudRepository<Band, String> {

    @Query("select band.ratePerHour from Band band where band.bandLevel = :level")
    Integer findRateByBandLevel(@Param("level") String level);
}
