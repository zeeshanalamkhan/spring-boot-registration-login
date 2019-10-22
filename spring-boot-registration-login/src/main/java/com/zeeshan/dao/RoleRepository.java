package com.zeeshan.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zeeshan.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
