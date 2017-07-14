package com.sevenre.triastest.repository;

import com.sevenre.triastest.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikhilesh on 14/07/17.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUserEmail(String userEmail);
}
