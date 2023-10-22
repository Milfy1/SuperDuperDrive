package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final EncryptionService encryptionService;

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(EncryptionService encryptionService, UserMapper userMapper, HashService hashService) {
        this.encryptionService = encryptionService;
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public Integer getUserId(String username){
        return userMapper.getUser(username).getUserid();
    }

    public boolean isPasswordCorrect(User user){

        String password = encryptionService.decryptValue(user.getPassword(), user.getSalt());
        if(userMapper.getUser(user.getUsername()).getPassword() == password){
            return true;
        }
      return false;
    }

    public boolean isUserExist(String username){
        if (userMapper.getUser(username) != null){
            return true;
        }
        return false;
    }

    public Integer createUser(User user){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedKey);
        user.setPassword(hashedPassword);
        user.setSalt(encodedKey);
       return userMapper.insertUser(user);
    }
}
