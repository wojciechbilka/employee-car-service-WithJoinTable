package com.springboot.h2.service;

import com.springboot.h2.model.Car;
import com.springboot.h2.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository repository;
    private Set<Car> carSet = new HashSet<>();

    public void save(final Car car) {
        repository.save(car);
        carSet.add(car);
    }

    public void delete(final Car car) {
        repository.delete(car);
        carSet.remove(car);
    }

    public Set<Car> getAll() {
        return repository.findAll().stream().collect(Collectors.toSet());
    }

    public void removeFromSet(Car car) {
        carSet.remove(car);
    }

    public void addToSet(Car car) {
        carSet.add(car);
    }

    public Car getCarFromSet(int id) {
        carSet = getAll();
       return carSet.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    public Set<Car> getCarSet() {
        carSet = getAll();
        return carSet;
    }

}
