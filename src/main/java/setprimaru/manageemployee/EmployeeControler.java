package setprimaru.manageemployee;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@RequestMapping("/api/employee")
public class EmployeeControler {

    private final EmployeeRepository employeeRepository;


    public EmployeeControler(EmployeeRepository employeeRepository){

        this.employeeRepository=employeeRepository;
    }


    @GetMapping("")
    public List<Employee> employees(){

        return employeeRepository.findAll();
    }

    @PostMapping("")
    public void create(@RequestBody Employee employee){

        employeeRepository.create(employee);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Employee employee, @PathVariable Integer id){

        employeeRepository.update(employee,id);
    }




}
