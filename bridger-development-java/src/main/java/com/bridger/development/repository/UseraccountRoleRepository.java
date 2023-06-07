package com.bridger.development.repository;
//
//import org.springframework.data.jpa.repository.query.Procedure;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
///**
// * <strong>UseraccountRoleRepository</strong><br>
// * JPA/Hibernate repository class which enables the communication between entity class <i>UserAccountRole</i> and
// * database <i>useaccountrole</i>.
// */
//@Repository
//public interface UseraccountRoleRepository extends CrudRepository<UserAccountRole, Long> {
//
//    @Procedure(procedureName = "sp_RoleOfParticipantsById")
//    UserAccountRole findByIds(long userid, long roleid);
//
//    @Procedure(procedureName = "sp_RolesOfParticipantById")
//    List<UserAccountRole> findRolesOfParticipantsById(long userid);
//}
