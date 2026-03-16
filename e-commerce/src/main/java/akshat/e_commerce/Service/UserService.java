package akshat.e_commerce.Service;


import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Respository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRespository UserRespository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean saveUser(UserModel user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("Admin"));
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
}