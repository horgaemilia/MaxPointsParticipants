package ssvv.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ssvv.example.domain.Nota;
import ssvv.example.domain.Pair;
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

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

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
            PrintWriter writer = new PrintWriter("studenti.xml");
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<Entitati>");
            writer.println("</Entitati>");
            writer.close();
        }
        catch (Exception ignored){}

        try {
            PrintWriter writer = new PrintWriter("teme.xml");
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<Entitati>");
            writer.println("</Entitati>");
            writer.close();
        }
        catch (Exception ignored){}


        try {
            PrintWriter writer = new PrintWriter("note.xml");
            writer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                    "<Entitati>");
            writer.println("</Entitati>");
            writer.close();
        }
        catch (Exception ignored){}
    }



    @Test
    public void TestSaveStudent_ValidInput_ShouldPass() {
        Student dummyStudent = new Student("123", "ana",123,"a@a.com");
        assertEquals(0,service.saveStudent(dummyStudent.getID(),dummyStudent.getNume(),dummyStudent.getGrupa(),dummyStudent.getEmail()));
    }

    @Test
    public void TestSaveHomework_InvalidInput_ShouldReturnError()
    {
        Tema dummyTema = new Tema("1","tema",0,1);
        assertEquals(1,service.saveTema(dummyTema.getID(), dummyTema.getDescriere(), dummyTema.getDeadline(), dummyTema.getStartline()));
    }

    @Test
    public void TestAddNota_ValidInput_ShouldPass()
    {
        Student dummyStudent = new Student("123", "ana",123,"a@a.com");
        service.saveStudent(dummyStudent.getID(), dummyStudent.getNume(),dummyStudent.getGrupa(), dummyStudent.getEmail());
        Tema dummyTema = new Tema("1","tema",12,1);
        service.saveTema(dummyTema.getID(), dummyTema.getDescriere(), dummyTema.getDeadline(), dummyTema.getStartline());

        Nota dummyNota = new Nota(new Pair<>(dummyStudent.getID(),dummyTema.getID()),4,4,"very good");
        assertEquals(1,service.saveNota(dummyStudent.getID(),dummyTema.getID(),dummyNota.getNota(),dummyNota.getSaptamanaPredare(),dummyNota.getFeedback()));
    }

    @Test
    public void BigBangTest_UndefinedBehaviour()
    {
        Student dummyStudent = new Student("123", "ana",123,"a@a.com");
        assertEquals(0,service.saveStudent(dummyStudent.getID(),dummyStudent.getNume(),dummyStudent.getGrupa(),dummyStudent.getEmail()));

        Tema dummyTema = new Tema("1","tema",5,1);
        assertEquals(0,service.saveTema(dummyTema.getID(), dummyTema.getDescriere(), dummyTema.getDeadline(), dummyTema.getStartline()));

        Nota dummyNota = new Nota(new Pair<>(dummyStudent.getID(),dummyTema.getID()),7,4,"very good");
        assertEquals(0,service.saveNota(dummyStudent.getID(),dummyTema.getID(),dummyNota.getNota(),dummyNota.getSaptamanaPredare(),dummyNota.getFeedback()));
    }

    @Test
    public void TestSaveStudent_InValidInput_ShouldThrowError() {
        Student dummyStudent = new Student("", "ana",123,"a@a.com");
        assertEquals(1,service.saveStudent(dummyStudent.getID(),dummyStudent.getNume(),dummyStudent.getGrupa(),dummyStudent.getEmail()));
    }

    @Test
    public void TestAddStudentAddHomework_InvalidStudentValidHomework_ShouldPass()
    {
        Student dummyStudent = new Student("", "ana",123,"a@a.com");
        service.saveStudent(dummyStudent.getID(),dummyStudent.getNume(),dummyStudent.getGrupa(),dummyStudent.getEmail());
        Tema dummyTema = new Tema("1","tema",7,1);
        assertEquals(0,service.saveTema(dummyTema.getID(), dummyTema.getDescriere(), dummyTema.getDeadline(), dummyTema.getStartline()));
    }

    @Test
    public void IntegrationAddGrade_InvalidStudent_AddGradeShouldThrowError()
    {
        Student dummyStudent = new Student("", "ana",123,"a@a.com");
        service.saveStudent(dummyStudent.getID(),dummyStudent.getNume(),dummyStudent.getGrupa(),dummyStudent.getEmail());
        Tema dummyTema = new Tema("1","tema",7,1);
        service.saveTema(dummyTema.getID(), dummyTema.getDescriere(), dummyTema.getDeadline(), dummyTema.getStartline());
        Nota dummyNota = new Nota(new Pair<>(dummyStudent.getID(),dummyTema.getID()),7,4,"very good");
        assertEquals(-1,service.saveNota(dummyStudent.getID(),dummyTema.getID(),dummyNota.getNota(),dummyNota.getSaptamanaPredare(),dummyNota.getFeedback()));
    }

}
