package com.api.tenejob.repository;

import com.api.tenejob.model.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by p4pupro on 20/12/2018.
 */

@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

}
