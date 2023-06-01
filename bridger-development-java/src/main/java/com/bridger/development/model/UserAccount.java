package com.bridger.development.model;

import com.bridger.development.model.entity_utility_classes.UtilityUserAccount;
import com.bridger.development.util.TextFunctions;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

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

    // Getters and setters
    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
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
        StringBuilder text = new StringBuilder().append("User account");

        text.append("\n\tId: ").append(this.id);
        text.append("\n\tUser name: ").append(TextFunctions.presentation(this.userName));
        text.append("\n\tDate registered: ").append(TextFunctions.presentation(this.dateRegistered.toString()));
        text.append("\n\tDate last password reset: ")
                .append(TextFunctions.presentation(this.dateLastChanged.toString()));
        text.append("\n\tLogin attempts: ").append(TextFunctions.presentation(String.valueOf(this.loginAttempts)));
        text.append("\n\tActive: ").append(TextFunctions.presentation(this.isActive ? "yes" : "no"));
        text.append("\n\tLocked: ").append(TextFunctions.presentation(this.isLocked ? "yes" : "no"));

        return text.toString();
    }

}
