package com.checkpointgames.app.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UsersRepositoryImpl implements UsersRepositoryCustom {    
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void updatePassword(String email, String newPassword) {
        entityManager.createQuery("UPDATE Users u SET u.password = :password WHERE u.email = :email")
                     .setParameter("password", newPassword)
                     .setParameter("email", email)
                     .executeUpdate();
    } 
    
    @Override
    public void updateUser(String email, String name, Integer age, Integer function, Integer status, String password, String number, Integer id){
        entityManager.createQuery("UPDATE Users u set u.email = :email, u.name = :name, u.age = :age, u.function = :function, u.status = :status, u.password = :password, u.number = :number where u.id = :id")
                     .setParameter("email", email)
                     .setParameter("name", name)
                     .setParameter("age", age)
                     .setParameter("function", function)
                     .setParameter("status", status)
                     .setParameter("password", password)
                     .setParameter("number", number)
                     .setParameter("id", id)               
                     .executeUpdate();
    }
}

