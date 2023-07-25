package com.tpe.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tbl_student")
@Getter // add getter method for all fields
@Setter // add setter method for all fields
@AllArgsConstructor // creates constructor with all fields
//@RequiredArgsConstructor
@NoArgsConstructor //creates empty constructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) //this field cannot be set
    private Long id;

    @NotNull(message = "first cannot be null")
    @NotBlank (message= "name cannot be white space")
    @Size(min=2, max = 25, message = "Fist name '${validatedValue}' must be between {min} and {max}" )
    @Column(nullable = false, length = 25)
    private /*final*/ String name;

    @Column(nullable = false, length = 25)
    //@Setter //it will set only this field
    private  /*final*/ String lastName;
    //@Setter
    private Integer grade;

    @Column(nullable = false, length = 50, unique = true)
    @Email(message="please provide valid email") //it will check "@" and "."
    private /*final*/ String email;

    private /*final*/ String phoneNumber;

    @Setter(AccessLevel.NONE) //this field cannot be set
    private LocalDateTime createDate =  LocalDateTime.now();

    @OneToMany (mappedBy = "student")
    private List<Book> books = new ArrayList<>();


}




























