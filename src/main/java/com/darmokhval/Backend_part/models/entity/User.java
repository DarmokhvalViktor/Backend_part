package com.darmokhval.Backend_part.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "name")
//    @NotBlank(message = "field `name` should not be empty")
//    @Size(min = 1, max = 150, message = "field should contain minimum - 1, maximum - 150 characters")
//    private String name;

    @Column(name = "username")
    @NotBlank(message = "field `name` should not be empty")
    @Size(min = 1, max = 150, message = "field should contain minimum - 1, maximum - 150 characters")
    private String username;

//    @Column(name = "year_of_birth")
//    @NotNull(message = "field `age` should not be empty")
//    @Min(value = 1940, message = "Birthday must be after 1940")
//    private Integer yearOfBirth;

    @Column(name = "email")
    @Email(message = "Invalid format")
    @NotBlank(message = "field `email` should not be empty")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "field `password` should not be empty")
    @Size(min = 1, max = 150, message = "Field should contain minimum - 1, maximum - 150 characters")
    private String password;


    //    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
//    @Column(name = "role")
//    private Set<Role> roles = new HashSet<>();
//    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private ERole role;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
