package akshat.e_commerce.Service;


import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Respository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository UserRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel saveUser(UserModel user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of("USER"));
        }

        UserModel savedUser = UserRespository.save(user);
        logger.info("User saved successfully with id: {} and email: {}", savedUser.getId(), savedUser.getEmail());
        return savedUser;
    }

    public List<UserModel> getAll() {
        return UserRespository.findAll();
    }

    public Optional<UserModel> getUserById(String id) {
        return UserRespository.findById(id);
    }

    public UserModel findByName(String name) {
        return UserRespository.findByName(name);
    }

    public UserModel findByEmail(String email) {
        return UserRespository.findByEmail(email);
    }

    public UserModel updateUser(UserModel user) {
        UserModel updatedUser = UserRespository.save(user);
        logger.info("User updated successfully with id: {}", updatedUser.getId());
        return updatedUser;
    }

    public boolean login(String email, String password) {
        UserModel user = UserRespository.findByEmail(email);
        if (user == null || user.getPassword() == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }
}
