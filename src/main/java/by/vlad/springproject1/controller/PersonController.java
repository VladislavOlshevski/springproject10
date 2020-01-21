package by.vlad.springproject1.controller;

import by.vlad.springproject1.dto.NewPersonDto;
import by.vlad.springproject1.dto.PersonDto;
import by.vlad.springproject1.entity.enums.Person;
import by.vlad.springproject1.exceptions.NoSuchEntityException;
import by.vlad.springproject1.exceptions.ResourceNotFoundException;
import by.vlad.springproject1.service.PersonService;
import by.vlad.springproject1.util.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//@Slf4j
//@RestController
//@RequestMapping
//public class PersonController {
//    private final PersonService personService;
//    // private static final org.slf4j.Logger log =
//// org.slf4j.LoggerFactory.getLogger(MainController.class);
//// Вводится (inject) из application.properties.@Value("${welcome.message}")
//    private String message;
//    @Value("${error.message}")
//    private String errorMessage;
//    @Autowired
//    public PersonController(PersonService personService) {
//        this.personService = personService;
//    }
//    @GetMapping(value = {"/", "/index"})
//    public ModelAndView index(Model model) {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
//        model.addAttribute("message", message);
//        log.info("index was called");
//        return modelAndView;
//    }
//    @GetMapping(value = {"/personList"})
//    public ModelAndView personList(Model model) {
//        List<Person> persons = personService.getAllPerson();
//        log.info("person List" + persons);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("personList");
//        model.addAttribute("persons", persons);
//        log.info("/personList was called");
//        return modelAndView;
//    }
//    @GetMapping(value = {"/addPerson"})
//    public ModelAndView showAddPersonPage(Model model) {
//        ModelAndView modelAndView = new ModelAndView("addPerson");
//        NewPersonDto personForm = new NewPersonDto();
//        model.addAttribute("personForm", personForm);
//        log.info("/addPerson - GET was called" + personForm);
//        return modelAndView;
//    }
//    // @PostMapping("/addPerson")
////GetMapping("/")
//    @PostMapping(value = {"/addPerson"})
//    public ModelAndView savePerson(Model model, //
//                                   @Valid @ModelAttribute("personForm")
//                                           NewPersonDto personDto, Errors errors) {
//        ModelAndView modelAndView = new ModelAndView();
//      //  log.info("/addPerson - POST was called" + personDto);
//        if (errors.hasErrors()) {
//            modelAndView.setViewName("addPerson");
//        }
//        else {
//            modelAndView.setViewName("personList");
//            Long id = personDto.getPersonId();
//            String firstName = personDto.getFirstName();
//            String lastName = personDto.getLastName();
//            String street = personDto.getStreet();
//            String city = personDto.getCity();
//            String zip = personDto.getZip();
//            String email = personDto.getEmail();
//            Date birthday = (Date)personDto.getBirthday();
//            String phone = personDto.getPhone();
//            Person newPerson = new Person(id, firstName, lastName, street,city, zip, email, birthday, phone);
//            personService.addNewPerson(newPerson);
//            model.addAttribute("persons", personService.getAllPerson());
//            log.info("/addPerson - POST was called");
//            return modelAndView;
//        }
//        return modelAndView;
//    }
//    @RequestMapping(value = "/editPerson/{id}", method = RequestMethod.GET)
//    public ModelAndView editPage(@PathVariable("id") int id) throws
//            NoSuchEntityException {
//        Person person = personService.getById(id).orElseThrow(()-> new
//                NoSuchEntityException("Person not found") );
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("editPerson");
//        modelAndView.addObject("person", person);
//        return modelAndView;
//    }
//    @RequestMapping(value = "/editPerson", method = RequestMethod.POST)
//    public ModelAndView editPerson( @Valid @ModelAttribute("person") Person
//                                            person,
//                                    Errors errors) {
//        log.info("/editPerson - POST was called"+ person);
//        personService.addNewPerson(person);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/personList");
//        return modelAndView;
//    }
//    @RequestMapping(value = "/deletePerson/{id}", method = RequestMethod.GET)
//    public ModelAndView deletePerson(@PathVariable("id") Long id) throws NoSuchEntityException {
//        Person person = personService.getById(id).orElseThrow(()-> new
//                NoSuchEntityException("Person not found") );
//        personService.deletePerson(person);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("redirect:/personList");
//        return modelAndView;
//    }
//}


@RestController
@RequestMapping
class PersonController {
    private final PersonService personService;
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
// this.personRepository = personRepository;
    }

    //, produces = { "application/json" , "application/xml"}
    @GetMapping(value = {"/personList"})
    public CollectionModel<EntityModel<PersonDto>> personList() {
// return Mapper.mapAll(personService.getAllPerson(), PersonDto.class);
        CollectionModel<EntityModel<PersonDto>> resource = CollectionModel.wrap(
                Mapper.mapAll(personService.getAllPerson(), PersonDto.class));
        for (final EntityModel<PersonDto> res : resource) {
            Link selfLink = linkTo(PersonController.class)
                    .slash(res.getContent().getPersonId()).withSelfRel();
            res.add(selfLink);
        }
        resource.add(linkTo(methodOn(PersonController.class)
                .personList()).withRel("all"));
        return resource;
    }

    @GetMapping(value = {"/personList/{id}"})
    public EntityModel<PersonDto> findById(@PathVariable("id") Long id) throws
            ResourceNotFoundException {
        PersonDto personid = Mapper.map(personService.getById(id),
                PersonDto.class);
        Link link = new Link("http://localhost:8080/personList/").withSelfRel();
        EntityModel<PersonDto> rezult = new EntityModel<PersonDto>(personid,
                link);
        return rezult;
    }

    @PutMapping(value = "/editPerson/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editPerson(@PathVariable("id") Long id, @Valid @RequestBody
            PersonDto persondto) throws ResourceNotFoundException {
        personService.getById(id);
        personService.editPerson(Mapper.map(persondto, Person.class), id);
    }

    @PostMapping("/addPerson")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@Valid @RequestBody NewPersonDto personDto) {
        personService.addNewPerson(Mapper.map(personDto, Person.class));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePerson(@PathVariable("id") Long id) throws
            ResourceNotFoundException {
        personService.deletePerson(personService.getById(id));
    }
}



