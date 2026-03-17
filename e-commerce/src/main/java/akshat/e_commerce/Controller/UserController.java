package akshat.e_commerce.Controller;


import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Respository.UserRespository;
import akshat.e_commerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService UserService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserModel user) {
        UserModel temp = UserService.findByName(user.getEmail());
        if (temp != null) return new ResponseEntity<>("this is email is already register", HttpStatus.ALREADY_REPORTED);
        if (user.getPassword().length() < 6)
            return new ResponseEntity<>("password length should be greater then six", HttpStatus.NOT_ACCEPTABLE);
        boolean flag = UserService.saveUser(user);
        if (!flag) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

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

    @PatchMapping("/user-details/{name}")
    public ResponseEntity<?> updateUser(@PathVariable String name, @RequestBody UserModel user) {
        try {
            UserModel oldUser = UserService.findByName(name);
            if (oldUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (user.getName() != null) {
                oldUser.setName(user.getName());
            }
            if (user.getPassword() != null) {
                oldUser.setPassword(user.getPassword());
            }
            UserService.saveUser(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}