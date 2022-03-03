package sg.edu.nus.iss.app.Workshop14.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sg.edu.nus.iss.app.Workshop14.model.Contact;
import sg.edu.nus.iss.app.Workshop14.service.ContactsRepo;

import java.util.List;


@Controller
public class AddressBookController {

    private static final Logger logger = LoggerFactory.getLogger(AddressBookController.class);

    @Autowired
    ContactsRepo service;

    @GetMapping("/")
    public String contactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @GetMapping("/getContact/{contactId}")
    public String getContact(Model model, @PathVariable(value="contactId") String contactId) {
        logger.info("contactId > " + contactId);
        Contact ctc = service.findById(contactId);
        logger.info("Name " + ctc.getName());
        logger.info("Email " + ctc.getEmail());
        logger.info("Email " + ctc.getPhoneNumber());
        model.addAttribute("contact",ctc);
        return "showContact";
    }

    @GetMapping("/contact")
    public String getAllContact(Model model, @RequestParam(name="startIndex") String startIndex) {
        List<Contact> result = service.findAll(Integer.parseInt(startIndex));
        model.addAttribute("contacts",result);
        return "listContact";
    }


    @PostMapping("/contact")
    public String contactSubmit(@ModelAttribute Contact contact, Model model) {
        logger.info("Name > " + contact.getName());
        logger.info("Email > " + contact.getEmail());
        logger.info("Phone Number > " + contact.getPhoneNumber());

        Contact persistToRedisCtc = new Contact(
                contact.getName(),
                contact.getEmail(),
                contact.getPhoneNumber()
        );

        service.save(persistToRedisCtc);
        model.addAttribute("contact",persistToRedisCtc);
        return "showContact";
    }
}
