package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.models.ContactInformation;

public interface ContactInformationRepository extends JpaRepository<ContactInformation, String> {

}
