package com.bridger.development.model.entity_utility_classes;

import com.bridger.development.model.UserRole;
import com.bridger.development.model.enums.Roles;
import org.springframework.stereotype.Component;

@Component
public class UtilityUserRole {

    public UserRole userRole()  {
        UserRole role = new UserRole();
        role.setRole(Roles.getDefaultSetting());
        return role;
    }
}
