package com.bridger.development.model;

import com.bridger.development.model.enums.Roles;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * <strong>UserRole</strong><br>
 * Defines a User Role from the enumerated user role values. The table is introduced to establish an one to more
 * relationship between <i>user accounts</i> and <i>user roles</i>.
 */
@Component
@Entity
@Table(name = "userrole")
public class UserAccountRole implements Serializable {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, unique = true)
    private Roles role;

    /* Getters and setters */
    public long getId() {
        return id;
    }

    public Roles getRole() {
        return role;
    }
    public void setRole(Roles role) {
        this.role = role;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "useraccountroles",
            joinColumns = @JoinColumn(name = "roleid"),
            inverseJoinColumns = @JoinColumn(name = "userid"))
    private Set<UserAccount> accounts;

    @Override
    public String toString() {
        return "User Account" + "\n\tId: " + this.id + "\n\tRole: "+this.role.name()+"\n";
    }
}
