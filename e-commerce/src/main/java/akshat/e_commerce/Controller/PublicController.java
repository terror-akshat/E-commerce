package akshat.e_commerce.Controller;

import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Service.UserDetailsServiceImpl;
import akshat.e_commerce.Service.UserService;
import akshat.e_commerce.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService UserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create-user")
    public ResponseEntity<?> signup(@RequestBody UserModel user) {
        if (user.getName() == null || user.getName().isBlank()) {
            return new ResponseEntity<>("name is required", HttpStatus.BAD_REQUEST);
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            return new ResponseEntity<>("email is required", HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return new ResponseEntity<>("password length should be greater than six", HttpStatus.BAD_REQUEST);
        }

        UserModel temp = UserService.findByEmail(user.getEmail());
        if (temp != null) {
            return new ResponseEntity<>("this email is already registered", HttpStatus.CONFLICT);
        }

        try {
            UserModel savedUser = UserService.saveUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            return new ResponseEntity<>("this email is already registered", HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("failed to save user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{user}")
    public UserModel getUser(@PathVariable String user) {
        return UserService.findByName(user);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        try {
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                return new ResponseEntity<>("email is required", HttpStatus.BAD_REQUEST);
            }
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                return new ResponseEntity<>("password is required", HttpStatus.BAD_REQUEST);
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String s = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(s, HttpStatus.OK);
//            boolean flag = UserService.login(user.getEmail(), user.getPassword());
//            if (!flag) {
//                return new ResponseEntity<>("invalid email or password", HttpStatus.UNAUTHORIZED);
//            }
//            return new ResponseEntity<>("login successful", HttpStatus.OK);
        } catch (Exception e) {
            log.error("exception occuer while createAuthentication", e);
            return new ResponseEntity<>("Please put create username and passeword: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
