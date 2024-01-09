package com.darmokhval.Backend_part.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")

    @NotEmpty(message = "field `name` should not be empty")
    @Size(min = 1, max = 150, message = "field should contain minimum - 1, maximum - 150 characters")
    private String name;

    @Column(name = "username")
    @NotEmpty(message = "field `name` should not be empty")
    @Size(min = 1, max = 150, message = "field should contain minimum - 1, maximum - 150 characters")
    private String username;

    @Column(name = "year_of_birth")
    @NotEmpty(message = "field `age` should not be empty")
    @Min(value = 1940, message = "Birthday must be after 1940")
    private int yearOfBirth;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "field `email` should not be empty")
    @Size(min = 1, max = 150, message = "field should contain minimum - 1, maximum - 150 characters")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "field `password` should not be empty")
    @Size(min = 1, max = 150)
    private String password;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;
}
