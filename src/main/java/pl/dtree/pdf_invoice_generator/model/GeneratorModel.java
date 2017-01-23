package pl.dtree.pdf_invoice_generator.model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;
import java.util.Observable;


public class GeneratorModel extends Observable {

    private File logoImage;

    public void generatePDF(File file) throws IOException{

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(1));
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);

        contentStream.newLineAtOffset(15, 600);
        contentStream.showText("hello world");
        contentStream.endText();
        contentStream.close();

        document.addPage(page);
        document.save(file);
        document.close();
    }

    public void setLogoImage(File logoImage) {
        this.logoImage = logoImage;
    }
}
