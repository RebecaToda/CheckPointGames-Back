package com.checkpointgames.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "USERS",
        uniqueConstraints  = {
                @UniqueConstraint(columnNames = {"email"})
        })
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;

    @Email
    @NotBlank
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;

    @NotBlank
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "AGE")
    private Integer age;

    @NotBlank
    @Size(max = 15)
    @Column(name = "NUMBER")
    private String number;

    @Column(name = "PROFILE_IMAGE", columnDefinition = "TEXT")
    private String profileImage;

    @Column(name = "FUNCTION", columnDefinition = "integer default 0")
    private Integer function;

    @Min(0)
    @Max(2)
    @Column(name = "STATUS", columnDefinition = "integer default 0")
    private Integer status;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public Integer getFunction() { return function; }
    public void setFunction(Integer function) { this.function = function; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }

    public boolean getIsAdmin() {
        return this.function != null && this.function == 1;
    }
}