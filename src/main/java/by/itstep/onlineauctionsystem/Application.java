package by.itstep.onlineauctionsystem;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static by.itstep.onlineauctionsystem.service.InvoiceGeneratingService.writeDataToDocument;


@SpringBootApplication()
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //create a document instance
        Document doc = new Document();

        //load the template file
        doc.loadFromFile("C:\\Users\\Lenovo\\Desktop\\TestInvoice.docx");

        //replace text in the document
        doc.replace("#InvoiceNumber", "17854", false, true);

        doc.saveToFile("NewInvoice.docx", FileFormat.Docx_2013);
    }
}