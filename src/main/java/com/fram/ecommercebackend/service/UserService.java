package com.fram.ecommercebackend.service;
import com.fram.ecommercebackend.api.model.RegistrationBody;
import com.fram.ecommercebackend.exception.UserAlreadyExistsException;
import com.fram.ecommercebackend.model.LocalUser;
import com.fram.ecommercebackend.model.dao.LocalUserDao;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private LocalUserDao localUserDao;


    public UserService(LocalUserDao localUserDao){
        this.localUserDao = localUserDao;
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
        //TODO: Encrypt passwords!!
        user.setPassword(registrationBody.getPassword());
        return localUserDao.save(user);
    }
}
