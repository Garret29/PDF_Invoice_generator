package pl.dtree.pdf_invoice_generator.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Observable;


public class GeneratorModel extends Observable {

    private File logoImage;
    private Hashtable<String, String> invoiceData;

    public void generatePDF(File file) throws IOException{

        PDDocument document = new PDDocument();
        document.addPage(new PDPage());
        PDPage page = document.getPage(0);

        PDImageXObject imageXObject = PDImageXObject.createFromFile(getClass().getClassLoader().getResource("blank1.png").getPath(), document);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(imageXObject, 0, 0);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 20);

        contentStream.newLineAtOffset(100, 0);
        contentStream.showText("hello world");
        contentStream.endText();
        contentStream.close();

        document.save(file);
        document.close();
    }

    public void setLogoImage(File logoImage) {
        this.logoImage = logoImage;
    }

    public void setInvoiceData(Hashtable<String, String> invoiceData) {
        this.invoiceData = invoiceData;
    }
}
