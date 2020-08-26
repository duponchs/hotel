package fr.projethotel.core.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Facture {

    public static void GenerationFacturePDF(){
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Rectangle pageSize = new Rectangle(216f, 720f);
        Chunk chunk = new Chunk("Hello World", font);


        try {
            document.add(chunk);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }
}
