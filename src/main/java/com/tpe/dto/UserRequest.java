package com.tpe.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {


    @NotBlank(message="enter first name")
    private String firstName;

    @NotBlank(message="enter last name")
    private String lastName;


    @NotBlank(message="enter user name")
    @Size( message = "please provide an username between 5 and 20 characters")
    private String userName;

    @NotBlank(message="enter password")
    private String password;


}