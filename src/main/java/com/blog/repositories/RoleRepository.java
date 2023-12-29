package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.Role;

public interface RoleRepository extends JpaRepository<Role, String> {

}
