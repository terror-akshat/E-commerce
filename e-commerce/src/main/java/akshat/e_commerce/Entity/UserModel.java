package akshat.e_commerce.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String name;

    @Indexed(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private List<String> roles;
}