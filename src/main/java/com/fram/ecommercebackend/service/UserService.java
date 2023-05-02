package com.fram.ecommercebackend.service;
import com.fram.ecommercebackend.api.model.RegistrationBody;
import com.fram.ecommercebackend.exception.UserAlreadyExistsException;
import com.fram.ecommercebackend.model.LocalUser;
import com.fram.ecommercebackend.model.dao.LocalUserDao;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private LocalUserDao localUserDao;
    private EncryptionService encryptionService;


    public UserService(LocalUserDao localUserDao, EncryptionService encryptionService){
        this.localUserDao = localUserDao;
        this.encryptionService = encryptionService;
    }

    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException {
        if(localUserDao.findByUsernameIgnoreCase(registrationBody.getUsername()).isPresent()
        || localUserDao.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        LocalUser user = new LocalUser();

        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUsername());
        user.setFirstName(registrationBody.getFirstname());
        user.setLastName(registrationBody.getLastname());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        return localUserDao.save(user);
    }
}
