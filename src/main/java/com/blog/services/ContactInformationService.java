package com.blog.services;

import java.util.List;

import com.blog.models.ContactInformation;

public interface ContactInformationService {

    List<ContactInformation> findAll();

    ContactInformation create(ContactInformation contactInformation);

    ContactInformation update(ContactInformation contactInformation);

}
