package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.models.ContactInformation;
import com.blog.services.ContactInformationService;

@Controller
public class ContactInformationController {

    @Autowired
    private ContactInformationService contactInformationService;

    @GetMapping("/contact")
    public String index(Model model) {
        model.addAttribute("title", "Thông tin liên hệ");
        return "contact/index";
    }

    @GetMapping("/admin/contact-information")
    public String admin(Model model) {
        model.addAttribute("title", "Chỉnh sửa thông tin liên hệ");
        List<ContactInformation> contactInformationList = contactInformationService.findAll();
        ContactInformation contactInformation = new ContactInformation();
        if (contactInformationList.size() > 0) {
            contactInformation = contactInformationList.get(0);
        } else {
            contactInformation = contactInformationService.create(contactInformation);
        }

        model.addAttribute("contactInformation", contactInformation);

        return "admin/contact/index";
    }

    @GetMapping("/admin/contact-information/edit")
    public String getEditPage(Model model) {
        List<ContactInformation> contactInformationList = contactInformationService.findAll();
        ContactInformation contactInformation = new ContactInformation();
        if (contactInformationList.size() > 0) {
            contactInformation = contactInformationList.get(0);
        } else {
            contactInformation = contactInformationService.create(contactInformation);
        }

        return "redirect:/admin/contact";
    }

    @PostMapping("/admin/contact-information")
    public String save(@ModelAttribute("contactInformation") ContactInformation contactInformation, Model model,
            RedirectAttributes redirectAttrs) {

        ContactInformation newContactInformation = this.contactInformationService.update(contactInformation);
        if (newContactInformation == null) {
            model.addAttribute("contactInformation", contactInformation);
            redirectAttrs.addFlashAttribute("toastMsg", "Chỉnh sửa thất bại");
            redirectAttrs.addFlashAttribute("toastType", "error");
        } else {
            model.addAttribute("contactInformation", newContactInformation);
            redirectAttrs.addFlashAttribute("toastMsg", "Chỉnh sửa thành công");
            redirectAttrs.addFlashAttribute("toastType", "success");
        }
        model.addAttribute("title", "Chỉnh sửa thông tin liên hệ");
        return "admin/contact/index";

    }
}
