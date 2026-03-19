package akshat.e_commerce.Service;

import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository UserRespository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = UserRespository.findByEmail(email);
        if (user != null) {
            List<String> authorities = user.getRoles() == null
                    ? List.of("ROLE_USER")
                    : user.getRoles().stream()
                    .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                    .toList();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .authorities(authorities.toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + email);
    }
}