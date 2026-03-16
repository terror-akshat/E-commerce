package akshat.e_commerce.Respository;

import akshat.e_commerce.Entity.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRespository extends MongoRepository<UserModel, String> {

    UserModel findByName(String name);

    void deleteUserByName(String name);
}