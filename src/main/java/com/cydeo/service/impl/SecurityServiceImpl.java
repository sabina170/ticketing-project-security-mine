package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get the user from the DB, we will get User entity:
        User user = userRepository.findByUserNameAndIsDeleted(username, false);

        if(user==null){
            throw  new UsernameNotFoundException(username);
        }
        //convert that user entity to user, that Spring understands by using UserPrincipal class:
        return new UserPrincipal(user);
    }
}
