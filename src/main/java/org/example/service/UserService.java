package org.example.service;

import org.example.api.UserLoginData;
import org.example.model.User;
import org.example.provider.FileUserProvider;
import org.example.provider.UserProvider;

public class UserService implements UserLoginChecker {

    private final UserProvider userProvider;

    public UserService() {
        userProvider = new FileUserProvider();
    }

    public UserService(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public boolean checkLogin(UserLoginData userLoginData) {
        return userProvider.getAllUsers().stream()
                .filter(user -> checkIfUserExist(userLoginData, user))
                .anyMatch(user -> checkIfUserPasswordIsCorrect(userLoginData, user));
    }

    private boolean checkIfUserPasswordIsCorrect(UserLoginData userLoginData, User user) {
        return userLoginData.getPassword().equals(user.getLogin());
    }

    private boolean checkIfUserExist(UserLoginData userLoginData, User user) {
        return user.getLogin().equals(userLoginData.getLogin());
    }
}
