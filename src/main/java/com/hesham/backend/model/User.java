package com.hesham.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    //private String roles; // like : ROLE_USER, ROLE_ADMIN
    private String[] authorities; // like : read, create, update, delete
    private boolean isActive;
    private boolean isNotLocked;
    private Date lastLoginDate;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="user_role",
            joinColumns={@JoinColumn(referencedColumnName="id")}
            , inverseJoinColumns={@JoinColumn(referencedColumnName="id")})
    private Set<Role> roles = new HashSet<>();
}
