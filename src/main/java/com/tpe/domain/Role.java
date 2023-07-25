package com.tpe.domain;

import com.tpe.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="t_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING) //to bring string names we need to use EnumType.STRING(ROLE_ADMIN, ROLE_STUDENT)
    private UserRole name;


    //toString to get only name
    @Override
    public String toString() {
        return "Role{" +
                "name=" + name +
                '}';
    }
}