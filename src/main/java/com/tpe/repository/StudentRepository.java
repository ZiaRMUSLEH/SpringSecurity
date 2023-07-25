package com.tpe.repository;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository//optional ,since we are extending from JpaRepository // spring will understand this class is repo
public interface StudentRepository extends JpaRepository<Student,Long> {


    boolean existsByEmail(String email);

    List<Student> findByLastName(String lastName);

    //================ JPQL======================
    ////The @Query annotation is used to specify the JPQL (Java Persistence Query Language) query that should be executed
    //The @Param annotation is used to bind the input parameter pGrade to the named parameter :pGrade in the JPQL query.

    @Query("SELECT s FROM Student s WHERE s.grade=:pGrade")
    List<Student> findStudentByGradeWithJPQL(@Param("pGrade") Integer grade);

    //================ SQL======================

    @Query(value ="SELECT * FROM tbl_student s WHERE s.grade=:pGrade" ,nativeQuery = true)
    List<Student> findStudentByGradeWithSQL(@Param("pGrade") Integer grade);

    //================ JPQL======================

    @Query("SELECT new com.tpe.dto.StudentDTO(std) FROM Student std WHERE std.id=:id")
    Optional<StudentDTO> findStudentDTOById(@Param("id") Long id);
}
