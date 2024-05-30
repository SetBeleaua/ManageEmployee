package setprimaru.manageemployee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(EmployeeDataLoader.class);
    private final EmployeeRepository employeeRepository;
    private final ObjectMapper objectMapper;

    public EmployeeDataLoader(EmployeeRepository employeeRepository, ObjectMapper objectMapper){
        this.employeeRepository = employeeRepository;
        this.objectMapper=objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.findAll().isEmpty()) {
            employee();
        }
    }

    public void employee() throws Exception {
        log.info("Loading data...");

        InputStream inputStream = getClass().getResourceAsStream("/data/data.json");
        List<Employee> allEmployees = objectMapper.readValue(inputStream, new TypeReference<List<Employee>>(){});
        log.info("Loaded data: {}", allEmployees);
        employeeRepository.saveAll(allEmployees);
    }
}