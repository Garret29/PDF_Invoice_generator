package pl.dtree.pdf_invoice_generator.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class GeneratorModel {

    private File logoFile;
    private JSONObject invoiceData;
    private JSONObject senderData;
    private JSONObject receiverData;

    public void generatePDF(File file) throws IOException, DocumentException {
        Image image = Image.getInstance(getClass().getClassLoader().getResource("blank.png"));
        image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setAbsolutePosition(0, 0);
        Image logoImage = Image.getInstance(logoFile.toURI().toURL());
        //logoImage.scaleAbsolute((float) (logoImage.getWidth() * 0.35), (float) (logoImage.getHeight() * 0.35));

        float logoWidth = logoImage.getWidth();
        float logoHeight = logoImage.getHeight();
        float ratioH = logoHeight / logoWidth;
        float ratioW = logoWidth / logoHeight;


        if (logoHeight >= logoWidth) {
            logoHeight = 150f;
            logoWidth = 150.0f * ratioW;
            logoImage.scaleAbsolute(logoWidth, logoHeight);

        } else {
            logoHeight = 150f * ratioH;
            logoWidth = 150.0f;
            logoImage.scaleAbsolute(logoWidth, logoHeight);
        }


        logoImage.setAbsolutePosition(130f - (0.5f * logoWidth), 725f - (0.5f * logoHeight));
        Document document = new Document(PageSize.A4, 0, 0, 0, 0);
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        BaseFont baseBold = BaseFont.createFont(getClass().getClassLoader().getResource("font/AbhayaLibre-Bold.ttf").toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont baseRegular = BaseFont.createFont(getClass().getClassLoader().getResource("font/AbhayaLibre-Regular.ttf").toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont baseExtraBold = BaseFont.createFont(getClass().getClassLoader().getResource("font/AbhayaLibre-ExtraBold.ttf").toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font fontBold = new Font(baseBold, 22);
        Font fontExtraBold = new Font(baseExtraBold, 40);
        Font fontRegular = new Font(baseRegular, 12);

        document.open();
        PdfContentByte pdfContentByte = writer.getDirectContent();

        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("PODPIS WYSTAWCY", fontRegular), 100, 100, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("PODPIS ODBIORCY ", fontRegular), 390, 100, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Termin płatności: " + invoiceData.getString("paymentDate"), fontRegular), 35, 170, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Słownie: " + invoiceData.getString("inWordsValue"), fontRegular), 35, 195, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("RAZEM", fontBold), 35, 245, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase(invoiceData.getString("value") + " PLN", fontBold), 565, 245, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(invoiceData.getString("service"), fontBold), 35, 380, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase(invoiceData.getString("value") + " PLN", fontBold), 565, 380, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Nazwa usługi", fontBold), 35, 400, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase("Wartość", fontBold), 565, 400, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(senderData.getString("accountNumber"), fontRegular), 35, 435, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("Numer konta bankowego:", fontRegular), 35, 450, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("NIP: " + senderData.getString("NIP"), fontRegular), 35, 485, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase("NIP: " + receiverData.getString("NIP"), fontRegular), 565, 485, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(senderData.getString("postalCode") + " " + senderData.getString("street"), fontRegular), 35, 510, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase(receiverData.getString("postalCode") + " " + receiverData.getString("street"), fontRegular), 565, 510, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("ul. " + senderData.getString("street") + " " + senderData.getString("building") + (senderData.getString("apartment").isEmpty() ? "" : "/") + senderData.getString("apartment"), fontRegular), 35, 535, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase("ul. " + receiverData.getString("street") + " " + receiverData.getString("building") + (receiverData.getString("apartment").isEmpty() ? "" : "/") + receiverData.getString("apartment"), fontRegular), 565, 535, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(senderData.getString("nameMore"), fontRegular), 35, 560, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase(receiverData.getString("nameMore"), fontRegular), 565, 560, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase(senderData.getString("name"), fontRegular), 35, 585, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase(receiverData.getString("name"), fontRegular), 565, 585, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("WYSTAWCA", fontRegular), 35, 605, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase("ODBIORCA", fontRegular), 565, 605, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("NR: " + invoiceData.getString("ID"), new Font(baseBold, 36)), 265, 690, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_LEFT, new Phrase("RACHUNEK", fontExtraBold), 265, 733, 0);
        ColumnText.showTextAligned(pdfContentByte, Element.ALIGN_RIGHT, new Phrase(invoiceData.getString("city") + ", " + invoiceData.getString("date"), fontRegular), 565, 795, 0);

        //pdfContentByte = writer.getDirectContent();
        pdfContentByte.addImage(logoImage);
        pdfContentByte = writer.getDirectContentUnder();
        pdfContentByte.addImage(image);

        document.close();
        outputStream.close();
    }

    public void setInvoiceData(JSONObject jsonObject) {
        this.invoiceData = jsonObject;
    }

    public void setSenderData(JSONObject senderData) {
        this.senderData = senderData;
    }

    public void setReceiverData(JSONObject receiverData) {
        this.receiverData = receiverData;
    }

    public void setLogoFile(File logoFile) {
        this.logoFile = logoFile;
    }
}
