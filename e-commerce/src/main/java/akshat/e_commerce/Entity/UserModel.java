package akshat.e_commerce.Entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Document(collection = "users")
public class UserModel {

    @Id
    private String id;

    @NonNull
    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private List<String> roles;
}