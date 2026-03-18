package akshat.e_commerce.Respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import akshat.e_commerce.Entity.UserModel;

public interface UserRepository extends MongoRepository<UserModel, String> {

    UserModel findByName(String name);

    UserModel findByEmail(String email);

    void deleteUserByName(String name);
}