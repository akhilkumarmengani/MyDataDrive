package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentialsOfUser(int userId){
        return credentialMapper.getAllCredentialsOfUser(userId);
    }

    public Credential getCredentialById(int credentialId){
        return credentialMapper.getCredentialById(credentialId);
    }

    public int insert(CredentialForm credentialForm, int userId){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(), encodedKey);

        if (credentialForm.getCredentialId()==null) {
             return credentialMapper.insert(new Credential(null,
                     credentialForm.getURL(),
                     credentialForm.getUsername(),
                     encodedKey,
                     encryptedPassword,
                     userId));
        } else {
            credentialForm.setKey(encodedKey);
            credentialMapper.updateCredential(credentialForm);
        }
        return 1;
    }

    public void deleteCredentialById(int credentialId){
        credentialMapper.deleteCredentialById(credentialId);
    }

    public void updateCredential(CredentialForm credentialForm){
        credentialMapper.updateCredential(credentialForm);
    }
}
