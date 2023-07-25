package com.tpe.service;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();

    }

    public void saveStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())){
            throw new ConflictException("student whose email is "+ student.getEmail()+ " already exist");
        }
        studentRepository.save(student);
    }


    // get student by id .
    // //findById(id) method will return Optional class (if there is no student with
    // // provided id, it will return empty obj ), so we need to customize exception
    public Student getStudentById(Long id) {
       Student student= studentRepository.findById(id).orElseThrow(
               ()-> new ResourceNotFoundException("Students whose Id is "+id+ "  "+"not found ")
       );
       return student;

    }


    // delete student//2
    public void deleteStudent(Long id) {
        //find student by id
        Student student =  getStudentById(id);
        studentRepository.delete(student);
    }


    public void updateStudent(Long id, StudentDTO studentDTO) {

        //find the student
       Student existingStudent=  getStudentById(id);

       boolean emilExists = studentRepository.existsByEmail(studentDTO.getEmail());//aa@gmail.com


        if (emilExists && !studentDTO.getEmail().equals(existingStudent.getEmail()) ){
            throw new ConflictException("student whose   "+ studentDTO.getEmail()+" already exist"  );
        }

            existingStudent.setName(studentDTO.getFirstName());
            existingStudent.setLastName(studentDTO.getLastName());
            existingStudent.setGrade(studentDTO.getGrade());
            existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
            existingStudent.setEmail(studentDTO.getEmail());
            studentRepository.save(existingStudent);

    }

    //method pageable
    public Page<Student> getAllStudentWithPage(Pageable pageable) {

        return studentRepository.findAll(pageable);

    }

    public List<Student> getStudentByLastName(String lastName) {

        return studentRepository.findByLastName(lastName);

    }

    //get method student Grade ;
    public List<Student> findStudentByGrade(Integer grade) {
        return studentRepository.findStudentByGradeWithJPQL(grade);
    }

    public StudentDTO findStudentDTOByID(Long id) {
        return studentRepository.findStudentDTOById(id).orElseThrow(
                ()->new ResourceNotFoundException("Student with id "+id+" is not found"));
    }


    //1.check email exist or not
    //2. if true ,if the email in Db belongs to same student whose being updated

    //Bd email list[aa@gmail.com,bb@gmail.com,ccc@gmail.com)

    //1. scenario  StudentDTO.getEmail()-- > aa@gmail.com exist
    // if new email is :aa@gmail.com
    //result update the exist email

    //2.  scenario  StudentDTO.getEmail()-- > aa@gmail.com exist  student id =2
    // if new email: bb@gmail.com but there is
    //bb@gmail.com in DB(some else using this email)   student id =3
    // result exception

    //3 scenario  StudentDTO.getEmail()-- > aa@gmail.com exist

    // if new email :bb@gmail.com but there is
    //no email in Db
    //result : update the exist email



}
