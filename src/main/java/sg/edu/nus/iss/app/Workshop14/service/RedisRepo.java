package sg.edu.nus.iss.app.Workshop14.service;

import org.springframework.stereotype.Repository;
import sg.edu.nus.iss.app.Workshop14.model.Contact;

import java.util.List;

@Repository
public interface RedisRepo  {

    public void save(final Contact ctc);
    public Contact findById(final String contactId);
    public List<Contact> findAll (int startIndex);

}
