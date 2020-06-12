package com.springboot.h2.service;

import com.springboot.h2.model.Car;
import com.springboot.h2.model.Employee;
import com.springboot.h2.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    private Set<Employee> employeeSet = new HashSet<>();
    private final CarService carService;

    public void save(final Employee employee) {
        repository.save(employee);
        employeeSet.add(employee); //ogromna zaleta setu tutaj jest, nie dodaje nowego pracownika w przypadku edycji -> save tylko zastepuje tego który mial takie samo id
    }

    public void delete(final Employee employee) {
        repository.delete(employee);
        employeeSet.remove(employee);
        for(Car car : employee.getCars()) {
            carService.getCarSet().remove(car);
        }
    }

    public Employee getEmployeeFromSet(int id) {
        employeeSet = getAll();
        return employeeSet.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    public Set<Employee> getEmployeeSet() {
        //employeeSet = getAll();
        return employeeSet;
    }

    public Set<Employee> getAll() {
        return repository.findAll().stream().collect(Collectors.toSet());
    }

}
