package com.bridger.development.model.entity_utility_classes;

import com.bridger.development.model.UserAccountRole;
import com.bridger.development.model.enums.Roles;
import org.springframework.stereotype.Component;

@Component
public class UtilityUserRole {

    public UserAccountRole userRole()  {
        UserAccountRole role = new UserAccountRole();
        role.setRole(Roles.getDefaultSetting());
        return role;
    }
}
