package com.pk.ms.entities.user;

import com.pk.ms.entities.schedule.Schedule;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class MyScheduleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_schedule_user_seq")
    @SequenceGenerator(name = "my_schedule_user_seq", sequenceName = "my_schedule_user_seq", allocationSize = 1)
    private Long userId;

    @Size(min = 2, max = 30)
    private String firstName;

    @Size(min = 2, max = 30)
    private String lastName;

    @Size(min = 2, max = 30)
    private String nick;

    @Email
    @Size(max = 50)
    private String email;

    @Size(min = 4, max = 50)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "my_schedule_user_roles",
            joinColumns = @JoinColumn(name = "my_schedule_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Schedule schedule;

    public MyScheduleUser() {

    }

    public MyScheduleUser(String firstName, String lastName, String nick, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
