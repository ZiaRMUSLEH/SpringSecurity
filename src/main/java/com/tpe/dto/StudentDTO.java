package com.tpe.dto;


import com.tpe.domain.Student;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class StudentDTO {


    private Long id;

    //    @Getter
//    @Setter // this will only generate for this filed
    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be white space ..")//
    @Size(min = 3,max = 25 , message = "First name '${validateValue}' must be between {min} and {max}")
    private String firstName;

    private String lastName;

    private Integer grade;

    @Email
    private  String email;

    private  String phoneNumber;

    private LocalDateTime createDate= LocalDateTime.now();


    public  StudentDTO(Student student){
        this.id= student.getId();
        this.firstName=student.getName();
        this.lastName=student.getLastName();
        this.grade=student.getGrade();
        this.createDate=student.getCreateDate();
        this.email=student.getEmail();
        this.phoneNumber=student.getPhoneNumber();

    }



}
