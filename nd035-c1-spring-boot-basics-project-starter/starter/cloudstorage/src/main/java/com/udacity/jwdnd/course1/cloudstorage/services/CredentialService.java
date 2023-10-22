package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credentials;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

@Service
public class CredentialService {
    private final CredentialsMapper credentialsMapper;
//    private final HashService  hashService;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public void addCredentials(Credentials credentials){

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentials.getPassword(), encodedKey);
        credentials.setPassword(encryptedPassword);
        credentials.setKey(encodedKey);

        if(credentials.getCredentialid() == null){
              credentialsMapper.insertCredentials(credentials);
          }
          else {
              credentialsMapper.editCredentials(credentials);
          }
    }

public void deleteCredentials(Integer credId){
        credentialsMapper.deleteCredential(credId);
}

public ArrayList<Credentials> decryptAll(ArrayList<Credentials> credentials){
        for(Credentials cred : credentials){
         cred.setPassword(encryptionService.decryptValue(cred.getPassword(),cred.getKey()));
        }
        return credentials;
}

    public ArrayList<Credentials> getCredentials(Integer userId){
   return credentialsMapper.getCredentials(userId);
    }

    public void editCredentials(Credentials credentials) {
        encryptionService.encryptValue(credentials.getPassword(),credentials.getKey());
        credentialsMapper.editCredentials(credentials);
    }
}


