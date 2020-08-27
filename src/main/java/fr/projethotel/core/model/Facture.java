package fr.projethotel.core.model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import fr.projethotel.core.entity.Client;
import fr.projethotel.core.entity.Reservation;
import fr.projethotel.core.service.ServiceClient;
import fr.projethotel.core.service.ServiceReservation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;


public class Facture {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    private static Client clientStatic;

    public static void GenerationFacturePDF(){
        Document document = new Document();
        ServiceClient serviceClient = new ServiceClient();
        ServiceReservation serviceReservation = new ServiceReservation();
        serviceClient.rechercheDesReservations();
        //Reservation reservation = serviceReservation.recherchePar();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("Facture"+clientStatic.getId()+" "+".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        try {
           // addMetaData(document);
            addTitlePage(document);
            //addContent(document);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Facture");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Stéphane, Mehdi, Adrien");
        document.addCreator("stéphane");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();




        // numero client
        Paragraph paragraphNumeroClient = new Paragraph("Numéro client :"+" "+clientStatic.getId(), smallBold);
        paragraphNumeroClient.setAlignment(Element.ALIGN_RIGHT);
        paragraphNumeroClient.setIndentationRight(100);
        preface.add(paragraphNumeroClient);

        // We add one empty line
        addEmptyLine(preface, 2);

        // titre nom client smallBold
        Paragraph paragraphNomClient = new Paragraph("Nom client :"+" "+clientStatic.getNom(), smallBold);
        paragraphNomClient.setAlignment(Element.ALIGN_RIGHT);
        paragraphNomClient.setIndentationRight(100);
        preface.add(paragraphNomClient);

        // insert info nom client



        // tittre prenom
        Paragraph paragraphPrenomClient = new Paragraph("Prenom client :"+" "+clientStatic.getPrenom(), smallBold);
        paragraphPrenomClient.setAlignment(Element.ALIGN_RIGHT);
        paragraphPrenomClient.setIndentationRight(100);
        preface.add(paragraphPrenomClient);

        // Will create: Report generated by: date
        Paragraph paragraphDateClient = new Paragraph("Date de facturation : " + LocalDate.now(), smallBold);
        paragraphDateClient.setAlignment(Element.ALIGN_RIGHT);
        paragraphDateClient.setIndentationRight(100);
        preface.add(paragraphDateClient);

        String numeroFacture = ""+Math.random();


        // Lets write a big header
        Paragraph paragraphTitre = new Paragraph("Facture N° : "+ numeroFacture.substring(2,10), catFont);
        paragraphTitre.setAlignment(Element.ALIGN_CENTER);
        preface.add(paragraphTitre);

        // We add one empty line
        addEmptyLine(preface, 3);

       // preface.add(new Paragraph(
        //        "This document describes something which is very important ",
         //       smallBold));

        //addEmptyLine(preface, 8);

        //preface.add(new Paragraph("Ce document est a conserver comme preuve.", redFont));

        PdfPTable table = new PdfPTable(2);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);


        PdfPCell c1 = new PdfPCell(new Phrase("Numero De Réservation"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        PdfPCell c2 = new PdfPCell(new Phrase("Prix"));
        c2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c2);

        table.setHeaderRows(1);

        table.addCell(new PdfPCell(new Phrase("")));
       // table.addCell(new PdfPCell(new Phrase(""+client.getPrenom())));
        table.addCell(new PdfPCell(new Phrase("")));

        table.addCell("");
        table.addCell("");



        preface.add(table);


        document.add(preface);
        // Start a new page
        //document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
