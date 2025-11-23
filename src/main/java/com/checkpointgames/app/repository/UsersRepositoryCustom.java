package com.checkpointgames.app.repository;

public interface UsersRepositoryCustom {
    void updatePassword(String email, String newPassword);
    void updateUser(String email, String name, Integer age, Integer function, Integer status, String password, String number, Integer id);
}