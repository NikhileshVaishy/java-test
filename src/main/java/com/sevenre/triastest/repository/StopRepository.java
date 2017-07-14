package com.sevenre.triastest.repository;

import com.sevenre.triastest.Stop;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikhilesh on 13/07/17.
 */
@Repository
public interface StopRepository extends CrudRepository<Stop, Long> {
    List<Stop> findByStopName(String stopName);
    List<Stop> findByLocName(String locName);
    List<Stop> findByGlobalId(String stopId);
}
