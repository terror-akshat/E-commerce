package akshat.e_commerce.Controller;

import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService UserService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll() {
        try {
            List<UserModel> user = UserService.getAll();
            if (!user.isEmpty()) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<List<UserModel>>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<UserModel> getById(@PathVariable String myId) {
        try {
            Optional<UserModel> user = UserService.getUserById(myId);
            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user.get(), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping
    public ResponseEntity<?> updateUser(@RequestBody UserModel user) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            assert authentication != null;
            String email = authentication.getName();
            UserModel oldUser = UserService.findByEmail(email);
            if (oldUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (user.getName() != null) {
                oldUser.setName(user.getName());
            }
            if (user.getEmail() != null) {
                UserModel existingUser = UserService.findByEmail(user.getEmail());
                if (existingUser != null && !existingUser.getId().equals(oldUser.getId())) {
                    return new ResponseEntity<>("this email is already registered", HttpStatus.CONFLICT);
                }
                oldUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                if (user.getPassword().length() < 6) {
                    return new ResponseEntity<>("password length should be greater than six", HttpStatus.BAD_REQUEST);
                }
                oldUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }
            boolean updated = UserService.updateUser(oldUser);
            if (!updated) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
