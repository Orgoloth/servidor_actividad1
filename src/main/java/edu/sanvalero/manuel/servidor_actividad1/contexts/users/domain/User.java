package edu.sanvalero.manuel.servidor_actividad1.contexts.users.domain;

import edu.sanvalero.manuel.servidor_actividad1.contexts.reviews.domain.Review;
import edu.sanvalero.manuel.servidor_actividad1.contexts.roles.domain.Role;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Usuario de la aplicación
 */
@Data
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true, updatable = false)
    private String email;
    private String nif;
    private String name;
    private String surname;
    private String address;
    private String city;
    @Column(name = "postal_code")
    private String postalCode;
    private String province;
    private String country;
    private String image;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    @Column(name = "active")
    private boolean active;
    @Transient
    private int age;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "author")
    Set<Review> reviews = new java.util.LinkedHashSet<>();
}
