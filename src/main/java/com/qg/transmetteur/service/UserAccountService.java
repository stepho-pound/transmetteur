package com.qg.transmetteur.service;

import com.qg.transmetteur.exception.UserNotFoundException;
import com.qg.transmetteur.model.UserAccount;
import com.qg.transmetteur.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService
{
    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccount saveUserAccount(UserAccount UserAccount){
        return userAccountRepository.save(UserAccount);
    }

    public UserAccount getUserAccountById(int id){
        return userAccountRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User by id " + id + " was not found"));
    }

    public UserAccount getUserAccountByUserName(String userName){
        return userAccountRepository.findByUserName(userName).orElseThrow(()-> new UserNotFoundException("User by name " + userName + " was not found"));
    }

    public UserAccount getUserAccountByEmail(String email){
        return userAccountRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User by email " + email + " was not found"));
    }

    public String deleteUserAccount(int id){
        userAccountRepository.deleteById(id);
        return "userAccount removed !! " + id;
    }
}
