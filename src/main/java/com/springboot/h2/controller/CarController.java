package com.springboot.h2.controller;

import com.springboot.h2.model.Car;
import com.springboot.h2.service.CarService;
import com.springboot.h2.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final EmployeeService employeeService;

    @RequestMapping(value = "/car_form", method = RequestMethod.GET)
    public String showform(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("empList", employeeService.getAll());
        return "car/car_form";
    }

    @PostMapping(value = "/save_car")
    public String save(@ModelAttribute(value = "car") @Valid Car car, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("empList", employeeService.getEmployeeSet());
            model.addAttribute("car", car);
            return "car/car_form";
        }
        Car carTemp;
        if (car.getId() == 0) {
            carTemp = car;
        } else {
            carTemp = carService.getCarFromSet(car.getId());
            carTemp.setEmployee(car.getEmployee());
            carTemp.setModel(car.getModel());
            carTemp.setName(car.getName());
            carTemp.setRegistrationDate(car.getRegistrationDate());
        }
        //carTemp.getEmployee().getCars().add(carTemp); // zapis samochodu w pracowniku
        System.out.println("In save_car method:");
        System.out.println("Employees: " + employeeService.getEmployeeSet());
        System.out.println("Cars: " + carService.getCarSet());
        carService.save(carTemp);
        return "redirect:/car_list";
    }

    @PostMapping(value = "/delete_car")
    public ModelAndView delete(@RequestParam(value = "car_id") int car_id) {
        Car car = carService.getCarFromSet(car_id);
        carService.delete(car);
        return new ModelAndView("redirect:/car_list");
    }

    @PostMapping(value = "/edit_car")
    public String edit(Model model, @RequestParam(value = "car_id") int car_id) {
        Car car = carService.getCarFromSet(car_id);
        model.addAttribute("car", car);
        model.addAttribute("empList", employeeService.getEmployeeSet());
        return "car/car_form";
    }

    @RequestMapping("/car_list")
    public ModelAndView carList() {
        return new ModelAndView("car/car_list", "list", carService.getAll());
    }

    /*private Car getCarById(int id) {
        return carList.stream().filter(f -> f.getId() == id).findFirst().get();
    }*/

    /*public Set<Car> getCarList() {
        return carList == null ? carList = carService.getAll() : carList;
    }*/
}