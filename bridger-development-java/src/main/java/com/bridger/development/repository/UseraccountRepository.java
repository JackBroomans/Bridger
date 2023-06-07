package com.bridger.development.repository;

import com.bridger.development.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <strong>UseraccountRepository</strong><br>
 * JPA/Hibernate repository class which enables the communication between entity class <i>UserAccount</i> and database
 * table <i>useaccount</i>.
 */

@Repository
public interface UseraccountRepository extends CrudRepository<UserAccount, Long> {

    @Override
    List<UserAccount> findAll();

    UserAccount findById(long id);
}

