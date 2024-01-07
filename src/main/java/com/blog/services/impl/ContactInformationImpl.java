package com.blog.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.models.ContactInformation;
import com.blog.repositories.ContactInformationRepository;
import com.blog.services.ContactInformationService;

@Service
public class ContactInformationImpl implements ContactInformationService {

    @Autowired
    private ContactInformationRepository contactInformationRepository;

    @Override
    public List<ContactInformation> findAll() {
        return contactInformationRepository.findAll();
    }

    @Override
    public ContactInformation create(ContactInformation contactInformation) {
        try {
            return contactInformationRepository.save(contactInformation);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ContactInformation update(ContactInformation contactInformation) {
        try {
            return contactInformationRepository.save(contactInformation);
        } catch (Exception e) {
            return null;
        }
    }

}
