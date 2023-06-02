package com.bridger.development.repository;

import com.bridger.development.model.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UseraccountRepository extends CrudRepository<UserAccount, Long> {

    List<UserAccount> findAll();

    UserAccount findById(long id);
}
