package akshat.e_commerce.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import akshat.e_commerce.Service.AuthService;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/auth")
public class GoogleAuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/callback")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code
    ) {
        try {
            String token = authService.authenticate(code);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            String redirectUrl = "http://localhost:5173/oauth-success?token=" + token;
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(java.net.URI.create(redirectUrl));
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            log.error("exceptioon", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
