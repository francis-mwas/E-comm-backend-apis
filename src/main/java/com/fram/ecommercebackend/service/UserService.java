package com.fram.ecommercebackend.service;
import com.fram.ecommercebackend.api.model.LoginBody;
import com.fram.ecommercebackend.api.model.RegistrationBody;
import com.fram.ecommercebackend.exception.UserAlreadyExistsException;
import com.fram.ecommercebackend.model.LocalUser;
import com.fram.ecommercebackend.model.dao.LocalUserDao;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Log4j2
public class UserService {

    private LocalUserDao localUserDao;
    private EncryptionService encryptionService;
    private JwtService jwtService;


    public UserService(LocalUserDao localUserDao, EncryptionService encryptionService, JwtService jwtService){
        this.localUserDao = localUserDao;
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
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

    public String loginUser(LoginBody loginBody){
        Optional<LocalUser> localUser = localUserDao.findByUsernameIgnoreCase(loginBody.getUsername());
        if(localUser.isPresent()){
            LocalUser user = localUser.get();
            log.info("The hashed user password is here: {}", user.getPassword());
            if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())){
                return jwtService.generateJWT(user);
            }
        }
        return  null;
    }
}
