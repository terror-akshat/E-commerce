package akshat.e_commerce.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import akshat.e_commerce.Entity.UserModel;
import akshat.e_commerce.Respository.UserRepository;
import akshat.e_commerce.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService {


    @Value("${spring.security.oauth.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${app.google.redirect-uri:http://localhost:8081/auth/callback}")
    private String redirectUri;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public String authenticate(String code) {
        try {
            String tokenEndPoint = "https://oauth2.googleapis.com/token";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId.trim());
            params.add("client_secret", clientSecret.trim());
            params.add("redirect_uri", redirectUri.trim());
            params.add("grant_type", "authorization_code");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenEndPoint, request, Map.class);
            String idToken = (String) tokenResponse.getBody().get("id_token");
            String userInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
            ResponseEntity<Map> userinfoResponse = restTemplate.getForEntity(userInfoUrl, Map.class);
            if (userinfoResponse.getStatusCode() == HttpStatus.OK) {
                Map userInfo = userinfoResponse.getBody();
                String email = (String) userInfo.get("email");
                UserDetails userDetails = null;
                try {
                    userDetails = userDetailsService.loadUserByUsername(email);
                } catch (UsernameNotFoundException e) {
                    UserModel user = new UserModel();
                    user.setEmail(email);
                    user.setName(email);
                    user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    user.setRoles(Arrays.asList("USER"));
                    userRepository.save(user);
                    userDetails = userDetailsService.loadUserByUsername(email);
                }
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String s = jwtUtil.generateToken(userDetails.getUsername());
                return s;
            }
            return null;
        } catch (Exception e) {
            log.error("Exceptioon", e);
            throw new RuntimeException(e);
        }
    }
}