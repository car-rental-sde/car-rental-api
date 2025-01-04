package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.entity.UserEntity;
import it.unitn.carrentalapi.openapi.model.NewUserModel;
import it.unitn.carrentalapi.openapi.model.UserModel;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> addUser(NewUserModel request);
    Optional<UserEntity> editUser(UserModel userModel);
    void deleteUser(Long id);
}
