package com.tpe.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="t_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    private String firstName;

    @Column(length = 25, nullable = false)
    private String lastName;


    @Column(length = 25, nullable = false, unique = true) //username should unique
    private String userName;

    @Column(length = 255, nullable = false) //password will be encoded so, the length may vay and long
    private String password;

    @ManyToMany(fetch = FetchType.EAGER) //when load user, we want to get his/her role at the same time
    @JoinTable(name="t_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )

    //to avoid repeated roles in list element we are using SET
    private Set<Role> roles = new HashSet<>(); //[Admin, Student]

}