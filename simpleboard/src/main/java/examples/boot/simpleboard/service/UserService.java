package examples.boot.simpleboard.service;

import examples.boot.simpleboard.domain.User;
import examples.boot.simpleboard.domain.UserConnection;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User addUser(User user);
    User getUserByEmail(String email);

    public User getSocialUser(String type, String providerUserId);
    public UserConnection addUserConnection(UserConnection userConnection);
}
