package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController//used to create restful API
@RequestMapping("/students")//http://localhost:8080/students
public class StudentController {


    //Create logger Logger obj
    Logger logger = LoggerFactory.getLogger(StudentController.class);


    @Autowired
    private StudentService studentService;

    //method to bring all students

    @GetMapping///http://localhost:8080/students+get
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Student>> getALL(){
      List<Student> student=  studentService.getAllStudents();
      return ResponseEntity.ok(student);// ok is for 200 status and objet of students
        ////creates an HTTP response with a status code of 200 OK and a body that contains the students list
    }//[]

    //method to save students
    //valid validates the filed of students object
    //RequestBody== maps the json to students object
    //ResponseEntity --> for return type and http status code

    @PostMapping()////http://localhost:8080/students+post
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){
        studentService.saveStudent(student);
        //status:true
        //messga:students successfully created
        Map<String,String> map= new HashMap<>();
        map.put("message","student is created successfully ..");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.CREATED);//201
    }

    //method to get student by Id using RequestParam
    @GetMapping("/query")//http://localhost:8080/students/query +get()

    public  ResponseEntity<Student> getStudentByIdUsingRequestParam(@RequestParam("id") Long id){
       Student student= studentService.getStudentById(id);
       return  new ResponseEntity<>(student,HttpStatus.OK);//200

    }

    //method to get student by Id using PathVariable

    @GetMapping("/{id}")////http://localhost:8080/students/2 +Get

    public ResponseEntity<Student> getStudentByIdUsingPathVariable(@PathVariable("id") Long id ){

       Student student= studentService.getStudentById(id);
       return ResponseEntity.ok(student);// ok is for 200 status and objet of students
    }

    //Both ways will do the same thing(get values from path)
    //if there are multiple parameters, then RequestParam is suggested because we can
    // write variable names, so it will be understandable

    //method to delete student object

    @DeleteMapping("/{id}")//http://localhost:8080/students/1 + DELETE
    public  ResponseEntity<Map<String,String>> deleteStudentById(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        Map<String,String> map= new HashMap<>();
        map.put("message","student is deleted successfully ..");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.OK);// return ResponseEntity.ok(map)
    }


    /*
    {
        "firstName": "ali",
        "lastName": "Can",
        "grade": 90,
        "email": "Ali@gmail.com",
        "phoneNumber": "9898976"

    }
     */
    //update exist student DTO

    @PutMapping("{id}")//http://localhost:8080/students/2  // endpoint + id+ put+ JSON

    public ResponseEntity<Map<String ,String>> updateStudent(@Valid @PathVariable("id") Long id ,
                                                              @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id,studentDTO);
        Map<String,String> map= new HashMap<>();
        map.put("message","student is updated successfully ..");
        map.put("status","true");
        return new ResponseEntity<>(map, HttpStatus.OK);// return ResponseEntity.ok(map)
    }

    //if in  our DB there are 1000 s of student and if we try to fetch theme at the same time  it will take too much time my create issue

    //getAll student by page

    @GetMapping("/page")//http://localhost:8080/students/page?page=2&size=3&sort=name&direction=ASC +get

    public ResponseEntity<Page<Student>> getAllStudentWithPage(@RequestParam("page") int page,//page number starting from 0
                                                               @RequestParam("size") int size,//how many student we want in a page
                                                               @RequestParam("sort") String prop,//sorting filed (optional)
                                                               @RequestParam("direction") Sort.Direction direction){// sorting type (optional)
       Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));

       Page<Student> studentsBypage= studentService.getAllStudentWithPage(pageable);
       return ResponseEntity.ok(studentsBypage);

    }

    //method to get Student By LastName

    @GetMapping("/queryLastName")///http://localhost:8080/students/queryLastName +get

    public ResponseEntity<List<Student>> getStudentByLastName(@RequestParam("lastName") String LastName){
      List<Student>  students=  studentService.getStudentByLastName(LastName);
      return ResponseEntity.ok(students);

    }

    //method to get student by Grade (JPQL) java Persistence Query Language

    @GetMapping("/grade/{grade}")//http://localhost:8080/students/grade/90

    public ResponseEntity<List<Student>> getStudentByGrade(@PathVariable("grade") Integer grade){
       List<Student>  students=  studentService.findStudentByGrade(grade);
       return ResponseEntity.ok(students);

    }

    //Can we get DATA As DTO From DB?
    //using JPQL we can map entity class to DTO using the Constructor we have set IN DTO

    @GetMapping("/query/dto")//http://localhost:8080/students/query/10
    public ResponseEntity<StudentDTO> getStudentDTO(@RequestParam("id") Long id){
       StudentDTO studentDTO= studentService.findStudentDTOByID(id);
       return ResponseEntity.ok(studentDTO);

    }
    @GetMapping("/welcome")  //http://localhost:8080/students/welcome

    public String welcome(HttpServletRequest request){

        logger.warn("----------------Welcome{}", request.getServletPath());
        return "Welcome to Student Controller";
    }






























}
