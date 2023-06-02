package com.bridger.development.repository;

import com.bridger.development.model.Participant;
import com.bridger.development.model.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UseraccountRepository extends CrudRepository<UserAccount, Long> {

    List<UserAccount> findAll();

    UserAccount findById(long id);
}
