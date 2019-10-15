package pl.slusarski.javaflashcardsappbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.slusarski.javaflashcardsappbackend.domain.User;
import pl.slusarski.javaflashcardsappbackend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
        // lub return new User(user.getUsername(), user.getPassword(), user.getRoles());    // User ze springframework.security
    }

    @Transactional
    public User loadUserById(Long id) {
        User user = userRepository.getById(id);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;

    }
}