package akshat.e_commerce.Controller;

import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

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
}