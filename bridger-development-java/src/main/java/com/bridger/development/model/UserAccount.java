package com.bridger.development.model;

import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;
import com.bridger.development.util.StringFunctions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import static com.bridger.development.util.StringFunctions.validateString;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 * <strong></strong><br>
 *
 */
@Component
@Entity
@Table(name = "useraccount")
public class UserAccount implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Participant.class);
    private static final UtilityUserAccount appVar = new UtilityUserAccount();

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "dateregistered", nullable = false)
    private LocalDate dateRegistered = LocalDate.now();

    @Column(name = "datelastpasswordreset", nullable = false)
    private LocalDate dateLastChanged;

    @Column(name = "datalastused")
    private LocalDate dateLastused;

    @Column(name = "loginattempts")
    private int loginAttempts = 0;

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "locked")
    private boolean isLocked;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "useraccountid")
    Participant participant;

    @ManyToMany(mappedBy= "accounts", fetch = FetchType.LAZY)
    private Set<UserRole> roles;


    // Getters and setters
    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        if (!validateString(userName)) {
            logger.warn(appVar.MSG_INVALID_USERNAME_FORMAT);
            return;
        }
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }
    public void setDateRegistered(LocalDate dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public LocalDate getDateLastChanged() {
        return dateLastChanged;
    }
    public void setDateLastChanged(LocalDate dateLastChanged) {
        this.dateLastChanged = dateLastChanged;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }
    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLocked() {
        return isLocked;
    }
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {

        String text = "User account" +
                "\n\tId: " + this.id +
                "\n\tUser name: " + StringFunctions.presentation(this.userName) +
                "\n\tDate registered: " + StringFunctions.presentation(this.dateRegistered.toString()) +
                "\n\tDate last password reset: " +
                StringFunctions.presentation(this.dateLastChanged.toString()) +
                "\n\tLogin attempts: " + StringFunctions.presentation(String.valueOf(this.loginAttempts)) +
                "\n\tActive: " + StringFunctions.presentation(this.isActive ? "yes" : "no") +
                "\n\tLocked: " + StringFunctions.presentation(this.isLocked ? "yes" : "no");

        return text;
    }

}
