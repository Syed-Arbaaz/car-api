package com.example.demo.service.invoice;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.example.demo.model.Booking;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class InvoiceService {
    public byte[] generateInvoice(Booking booking){
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            //title
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Car Booking Invoice", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));
            //Booking Details

            document.add(new Paragraph("Booking ID: " + booking.getId()));
            document.add(new Paragraph("Customer Name: " + booking.getCustomerName()));
            document.add(new Paragraph("Customer Email: " + booking.getCustomerEmail()));
            document.add(new Paragraph("Car Name: " + booking.getCarName()));
            document.add(new Paragraph("Brand: " + booking.getBrand()));
            document.add(new Paragraph("Price: $" + booking.getPrice()));
            document.add(new Paragraph("Status: " + booking.getStatus()));

            document.add(new Paragraph(" "));

            Paragraph footer = new Paragraph("Thank you for choosing our service");
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();

            return out.toByteArray();
        }
        catch(Exception e){
            throw new RuntimeException("Error generating file:"+e);
        }
    }
}
