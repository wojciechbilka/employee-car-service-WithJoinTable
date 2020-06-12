package com.springboot.h2.model;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    @GeneratedValue(
            strategy= GenerationType.AUTO,
            generator="native"
    )
    @GenericGenerator(
            name = "native",
            strategy = "native"
    )
    private int id;

    @ManyToOne
    @JoinTable(name = "employeecar",
            joinColumns = {@JoinColumn(name = "carid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "empid", referencedColumnName = "id")}
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NonNull
    public Employee employee;

    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String name;

    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String model;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @NotNull
    private LocalDate registrationDate;

}
