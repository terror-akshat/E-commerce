package akshat.e_commerce.Service;


import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRespository UserRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean saveUser(UserModel user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                user.setRoles(Arrays.asList("USER"));
            }
            UserRespository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
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

    public boolean updateUser(UserModel user) {
        try {
            UserRespository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean login(String email, String password) {
        UserModel user = UserRespository.findByEmail(email);
        if (user == null || user.getPassword() == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }
}