package ra.jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ra.jpa.entity.Person;
import ra.jpa.repository.PersonRepository;

import java.util.List;

@Controller
public class JpaController {
    @Autowired
    private PersonRepository personRepository;
    @GetMapping
    public String home(){
//        Person person = new Person(1,"Nguyễn Văn A",19,true);
//        personRepository.save(person);
//        List<Person> list = personRepository.findAllByNameContaining("hu");
long count = personRepository.countedByName("hu");
        return "home";
    }

    // cap nhật , lấy về tất cả, lấy theo id, xóa
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        personRepository.deleteById(id);
        return "home";
    }
    // tìm kiếm theo tên
}
