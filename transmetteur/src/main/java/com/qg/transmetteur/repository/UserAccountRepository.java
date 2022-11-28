package com.qg.transmetteur.repository;

import com.qg.transmetteur.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount,Integer>
{
    void deleteById(int id);
    Optional<UserAccount> findById(int id);
    Optional<UserAccount> findByEmail(String email);
    Optional<UserAccount> findByUserName(String userName);

}
