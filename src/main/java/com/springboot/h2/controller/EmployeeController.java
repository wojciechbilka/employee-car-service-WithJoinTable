package com.springboot.h2.controller;

import com.springboot.h2.model.Car;
import com.springboot.h2.model.Employee;
import com.springboot.h2.service.CarService;
import com.springboot.h2.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final EmployeeService employeeService;

    @GetMapping("/")
    public String index() {
        return "employee/index";
    }

    @RequestMapping(value = "/employee_form", method = RequestMethod.GET)
    public String showform(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/employee_form";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute(value = "employee") @Valid Employee employee, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("employee", employee);
            return "employee/employee_form";
        }
        Employee empTemp;
        if (employee.getId() == 0) {
            empTemp = employee;
            empTemp.setCars(new HashSet<Car>()); // do zmiany?
        } else {
            System.out.println("Employee in save: " + employee);
            empTemp = employeeService.getEmployeeFromSet(employee.getId());
            empTemp.setFirstName(employee.getFirstName());
            empTemp.setSalary(employee.getSalary());
            empTemp.setAddress(employee.getAddress());
            empTemp.setBenefit(employee.getBenefit());
            empTemp.setAge(employee.getAge());
            empTemp.setCity(employee.getCity());
            empTemp.setLastName(employee.getLastName());
            empTemp.setStartJobDate(employee.getStartJobDate());
            empTemp.setEmail(employee.getEmail());
        }
        employeeService.save(empTemp);
        return "redirect:/employee_list";
    }

    @PostMapping(value = "/delete")
    public ModelAndView delete(@RequestParam(value = "emp_id") int emp_id) {
        Employee employee = employeeService.getEmployeeFromSet(emp_id);
        employeeService.delete(employee);
        return new ModelAndView("redirect:/employee_list");
    }

    @PostMapping(value = "/edit")
    public ModelAndView edit(@RequestParam(value = "emp_id") int emp_id) {
        Employee employee = employeeService.getEmployeeFromSet(emp_id);
        return new ModelAndView("employee/employee_form", "employee", employee);
    }

    @PostMapping(value = "/addCar")
    public String addCar(Model model, @RequestParam(value = "emp_id") int emp_id) {
        Employee employee = employeeService.getEmployeeFromSet(emp_id);
        Car car = new Car();
        car.setEmployee(employee); //zapis pracownika w samochodzie (w formularzu pracownika nie można edytować samochodu dlatego wystarczy to w metodzie addCar)
        model.addAttribute("car", car);
        model.addAttribute("empList", employeeService.getEmployeeSet());
        return "car/car_form";
    }

    @RequestMapping("/employee_list")
    public ModelAndView employee_list() {
        System.out.println("In employee list:");
        for(Employee e : employeeService.getEmployeeSet())
        {
            System.out.println(e);
        }
        return new ModelAndView("employee/employee_list", "list", employeeService.getAll());
    }

   /* private Employee getEmployeeById(int id) {
        for(Employee e : employeeList) {
            if(e.getId() == id) {
                return e;
            }
        }
        System.out.println("Employee Not Found");
        return null;
        //return employeeList.stream().filter(f -> f.getId() == id).findFirst().get();
    }*/

   /* public Set<Employee> getEmployeeList() {
        return employeeList == null ? employeeList = employeeService.getAll() : employeeList;
    }*/
}