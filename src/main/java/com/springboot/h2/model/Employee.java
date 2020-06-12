package com.springboot.h2.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

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

    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String firstName;

    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String lastName;

    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String address;

    @EqualsAndHashCode.Exclude
    @NotEmpty
    private String city;

    @EqualsAndHashCode.Exclude
    @NotNull
    @Min(value = 1800)
    private double salary;

    @EqualsAndHashCode.Exclude
    @Min(value = 18)
    @Max(value = 80)
    private double age;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @EqualsAndHashCode.Exclude
    @NotNull
    private LocalDate startJobDate = LocalDate.now();

    @EqualsAndHashCode.Exclude
    @Min(0)
    @Max(5)
    private int benefit;

    @EqualsAndHashCode.Exclude
    @Email
    @NotEmpty
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employeecar",
            joinColumns = {@JoinColumn(name = "empid", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "carid", referencedColumnName = "id")}
    )
    @EqualsAndHashCode.Exclude
    private Set<Car> cars;

}
