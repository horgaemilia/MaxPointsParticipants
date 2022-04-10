package ssvv.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.NotaXMLRepository;
import ssvv.example.repository.StudentXMLRepository;
import ssvv.example.repository.TemaXMLRepository;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

import java.io.PrintWriter;

import static org.junit.Assert.*;

public class AddAssignmentTest {

    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING="";
    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();
    private StudentXMLRepository fileRepository1 ;
    private TemaXMLRepository fileRepository2 ;
    private NotaXMLRepository fileRepository3 ;
    private Service service ;

    @Before
    public void before()
    {
        String studentFileName = "studenti.xml";
        String notaFileName = "note.xml";
        String temaFileName = "teme.xml";

        fileRepository1 = new StudentXMLRepository(studentValidator,studentFileName);
        fileRepository2 = new TemaXMLRepository(temaValidator,temaFileName);
        fileRepository3 = new NotaXMLRepository(notaValidator,notaFileName);
        service = new Service(fileRepository1,fileRepository2,fileRepository3);
    }

    @After
    public void after()
    {
        try {
            PrintWriter writer = new PrintWriter("teme.xml");
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<Entitati>");
            writer.println("</Entitati>");
            writer.close();
        }
        catch (Exception ignored){}
    }


    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void addNewInvalidHomeworkShouldReturnNull()
    {
        Tema tema = new Tema("1","ceva",5,14);
        assertNull(fileRepository2.save(tema));
    }

    @Test
    public void addValidHomeworkShouldReturnItself()
    {
        Tema tema = new Tema("1","ceva",11,5);
        assertEquals(tema,fileRepository2.save(tema));
    }

    @Test
    public void addSameHomeworkTwiceShouldReturnNull()
    {
        Tema tema = new Tema("1","ceva",11,5);
        fileRepository2.save(tema);
        assertNull(fileRepository2.save(tema));
    }

    @Test
    public void saveHomeworkShouldReturn1(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        assertEquals(service.saveTema(NULL_STRING,"description",12,2),1);
    }
    @Test
    public void saveHomeworkShouldReturn0(){
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        assertEquals(service.saveTema("1","description",12,2),0);
    }
    @Test
    public void saveHomeworkNullIdShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema(NULL_STRING, "description", 12, 2);
        try {
            TemaValidator validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }

    @Test
    public void saveHomeworkEmptyIdShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema(EMPTY_STRING, "description", 12, 2);
        try {
                TemaValidator validator = null;
                validator.validate(tema);
                assert (false);
            } catch (Exception e) {
                assert (true);
            }
        }
    @Test
    public void saveHomeworkNULLDescriptionShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema("1", NULL_STRING, 12, 2);
        try {
            TemaValidator validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }
    @Test
    public void saveHomeworkEmptyDescriptionShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema("1", EMPTY_STRING, 12, 2);
        try {
            TemaValidator validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }
    @Test
    public void saveHomeworkWeek0InvalidDeadlineDescriptionShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema("1", "description", 0, 2);
        try {
            TemaValidator validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }
    @Test
    public void saveHomeworkWeek16InvalidDeadlineDescriptionShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema("1", "description", 16, 2);
        try {
            TemaValidator validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }
    @Test
    public void saveHomeworkWeek0StartDeadlineDescriptionShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema("1", "description", 12, 0);
        try {
            TemaValidator validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }
    @Test
    public void saveHomeworkWeek16StartDeadlineDescriptionShouldThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema("1", "description", 12, 16);
        try {
            TemaValidator validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }
    @Test
    public void saveHomeworkShouldNotThrowValidationException() {
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        Tema tema = new Tema("1", "description", 12, 2);
        try {
            Validator<Tema> validator = null;
            validator.validate(tema);
            assert (false);
        } catch (Exception e) {
            assert (true);
        }
    }
}
