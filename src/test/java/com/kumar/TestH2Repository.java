package com.kumar;

import com.kumar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author RakeshKumar created on 07/09/24
 */
public interface TestH2Repository extends JpaRepository<User,Integer> {
}
