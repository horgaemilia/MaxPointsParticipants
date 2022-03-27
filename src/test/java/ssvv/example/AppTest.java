package ssvv.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Student;
import ssvv.example.domain.Tema;
import ssvv.example.repository.*;
import ssvv.example.service.Service;
import ssvv.example.validation.NotaValidator;
import ssvv.example.validation.StudentValidator;
import ssvv.example.validation.TemaValidator;
import ssvv.example.validation.Validator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.PrintWriter;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
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
            PrintWriter writer = new PrintWriter("studenti.xml");
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
    public void addStudentEmptyIdShouldReturnError()
    {
        Student dummyStudent = new Student(null,"gigel",129,"d@s.com");
        Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
        assertEquals(1,service.saveStudent(dummyStudent.getID(),dummyStudent.getNume(),dummyStudent.getGrupa(),"a@a.com"));
    }

    @Test
    public void addStudentValidFieldsShouldAddStudent()
    {
        Student dummyStudent = new Student("100","gigel",129,"d@s.com");
        int value = service.saveStudent(dummyStudent.getID(),dummyStudent.getNume(),dummyStudent.getGrupa(),"a@a.com");
        assertEquals(0,value);
    }
}
