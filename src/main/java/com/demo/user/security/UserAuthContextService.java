package com.demo.user.security;

import com.demo.user.dao.entity.UserEntity;
import com.demo.user.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Bean to retrieve user data from database for authentication.
 */
@Component
public class UserAuthContextService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param userId {@link Long}
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException {@link UsernameNotFoundException}
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity userEntity=userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new UsernameNotFoundException("Authentication Failed : User not found"));
        return new UserContext(userEntity);
    }
}
