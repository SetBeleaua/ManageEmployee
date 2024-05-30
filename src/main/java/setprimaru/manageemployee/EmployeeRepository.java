package setprimaru.manageemployee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import  org.springframework.jdbc.core.simple.JdbcClient;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepository.class);

    private final JdbcClient jdbcClient;

    public EmployeeRepository(JdbcClient jdbcClient){
        this.jdbcClient=jdbcClient;
    }

    public List<Employee> findAll() {
        return jdbcClient.sql("select * from employee")
                .query(Employee.class)
                .list();
    }

    public Employee findById(Integer id) {
        List<Employee> employees = jdbcClient.sql("select * from employee where id = ?")
                .params(List.of(id))
                .query(Employee.class)
                .list();
        return employees.isEmpty() ? null : employees.get(0);
    }

    public void create(Employee employee) {
        // Verify if an employee exist with the exact same id
        Employee existingEmployee = findById(employee.id());
        if (existingEmployee != null) {
            // If exist it will throw an error to announce that the employee already exists;
            throw new IllegalArgumentException("Un angajat cu ID-ul " + employee.id() + " există deja.");
        } else {
            // If the verification is over, it will progress to the final step, that means the creation of the employee.

            var updated = jdbcClient.sql("INSERT INTO employee(id, name, email, phone, address, city, state, position) values(?,?,?,?,?,?,?,?)")
                    .params(List.of(employee.id(), employee.name(), employee.email(), employee.phone(), employee.address(), employee.city(), employee.state(), employee.position()))
                    .update();
        }
    }


    public void update(Employee employee, Integer id) {

        Employee existingEmployee = findById(id);
        if (existingEmployee == null) {
            // Dacă nu există, aruncă o excepție
            throw new IllegalArgumentException("Nu există niciun angajat cu ID-ul " + id);
        } else {
            // If exists, it will update the curent situation
            var updated = jdbcClient.sql("UPDATE employee SET name=?, email=?, phone=?, address=?, city=?, state=?, position=? WHERE id=?")
                    .params(List.of(employee.name(), employee.email(), employee.phone(), employee.address(), employee.city(), employee.state(), employee.position(), id))
                    .update();
        }
    }

    public void deleteEmployee(Integer id){

        var updated=jdbcClient.sql(("DELETE FROM employee WHERE id=?")).params(List.of(id)).update();

    }

   public void saveAll(List<Employee> employees){employees.forEach(this::create);}


}


//Done