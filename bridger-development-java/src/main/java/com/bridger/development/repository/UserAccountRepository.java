package com.bridger.development.repository;

import com.bridger.development.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <strong>UseraccountRepository</strong><br>
 * JPA/Hibernate repository class which enables the communication between entity class <i>UserAccount</i> and database
 * table <i>useaccount</i>.
 */

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

    Iterable<UserAccount> findAll();

    UserAccount findById(long id);

    Optional<UserAccount> findByUserName(String userName);

    Boolean existsByUserName(String userName);

}

