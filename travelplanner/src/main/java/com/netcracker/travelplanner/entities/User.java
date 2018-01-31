package com.netcracker.travelplanner.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name="users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
    @SequenceGenerator(name = "users_seq", sequenceName = "user_id_seq")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name="first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name="last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name="birth_date")
    @Temporal(TemporalType.DATE)
    private Date BirthDate;

    @Column(name="is_admin", nullable = false)
    private boolean isAdmin;

    @Column(name="registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(name="password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private Set<Route> routes;

    public User(String email,String firstName, String lastName, Date birthDate, boolean isAdmin, Date registrationDate, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        BirthDate = birthDate;
        this.isAdmin = isAdmin;
        this.registrationDate = registrationDate;
        this.password = password;
        routes = new HashSet<>();
    }
    private User(){}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", FirstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", BirthDate=" + BirthDate +
                ", IsAdmin=" + isAdmin +
                ", RegistrationDate=" + registrationDate +
                ", Password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date birthDate) {
        BirthDate = birthDate;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(Set<Route> routes) {
        this.routes = routes;
    }


}
