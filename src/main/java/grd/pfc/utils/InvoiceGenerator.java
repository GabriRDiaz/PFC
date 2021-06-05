/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.utils;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import grd.pfc.pojo.Pedido;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Gabriel
 */
public class InvoiceGenerator {
    private File saveLocation;
    private Pedido pedido;
    public InvoiceGenerator(File saveLocation, Pedido pedido){
        this.saveLocation=saveLocation;
        this.pedido=pedido;
    }
    
    public void generateInvoice() throws FileNotFoundException{
    PdfDocument pdfDocument = new PdfDocument(new PdfWriter(saveLocation));
    Document layoutDocument = new Document(pdfDocument);
    
    // title
    addTitle(layoutDocument);

    // customer reference information
    addCustomerInfo(layoutDocument);
    addTable(layoutDocument);

    // articles
    layoutDocument.close();
    }
    private void addTitle(Document layoutDocument){
        Style title = new Style();
        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            title.setFont(font).setFontSize(22);
            layoutDocument.add(new Paragraph(new Text("Factura\nProforma").addStyle(title)).setBold().setUnderline().setTextAlignment(TextAlignment.LEFT));
            
        } catch (IOException ex) {
            Logger.getLogger(InvoiceGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void addCustomerInfo(Document layoutDocument){
        Table table = new Table(2);
        Cell cliente = new Cell().add(new Paragraph("Cliente: "+pedido.getCliente()+"\n"
                + "Dirección: "+pedido.getDireccion()+"\n"
                + "Teléfono: "+pedido.getTelefono()+"\n"
                + "Fecha Exp.: "+pedido.getFechaExp()));
        cliente.setPadding(0);
        cliente.setTextAlignment(TextAlignment.RIGHT);
        cliente.setBorder(Border.NO_BORDER);
        Cell empresa = new Cell().add(new Paragraph(
                "PFC S.L.\n"
            + "Avd PFC Nº5 5ºD: \n"
            + "27850 Viveiro,Lugo \n"
            + "España"));
        empresa.setPadding(0);
        empresa.setTextAlignment(TextAlignment.LEFT);
        empresa.setBorder(Border.NO_BORDER);
        table.addCell(empresa);
        table.addCell(cliente);
//        Paragraph p = new Paragraph("Cliente: "+pedido.getCliente()+"\n"
//                + "Dirección: "+pedido.getDireccion()+"\n"
//                + "Teléfono: "+pedido.getTelefono()+"\n"
//                + "Fecha Exp.: "+pedido.getFechaExp());
//        p.add(new Tab());
//        p.addTabStops(new TabStop(1000, TabAlignment.RIGHT));
//        p.add("PFC S.L.\n"
//            + "Avd PFC Nº5 5ºD: \n"
//            + "27850 Viveiro,Lugo \n"
//            + "España");
        layoutDocument.add(table);
//    layoutDocument.add(new Paragraph("Cliente: "+pedido.getCliente()).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(0.2f));
//    layoutDocument.add(new Paragraph("Dirección: "+pedido.getDireccion()).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(.2f));
//    layoutDocument.add(new Paragraph("Teléfono: "+pedido.getTelefono()).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(.2f));
//    layoutDocument.add(new Paragraph("Fecha Exp.: "+pedido.getFechaExp()).setTextAlignment(TextAlignment.RIGHT).setMultipliedLeading(.2f));
//    
//    layoutDocument.add(new Paragraph("PFC S.L.").setTextAlignment(TextAlignment.LEFT).setMultipliedLeading(0.2f));
//    layoutDocument.add(new Paragraph("Avd PFC Nº5 5ºD").setMultipliedLeading(.2f));
//    layoutDocument.add(new Paragraph("27850 Viveiro,Lugo").setMultipliedLeading(.2f));
//    layoutDocument.add(new Paragraph("España").setMultipliedLeading(.2f));
}
    
    private void addTable(Document layoutDocument){
    Table table = new Table(UnitValue.createPointArray(new float[]{60f, 180f, 50f, 80f, 110f}));

    // headers
    table.addCell(new Paragraph("Producto").setBold());
    table.addCell(new Paragraph("Cantidad").setBold());
    table.addCell(new Paragraph("Precio").setBold());
    table.addCell(new Paragraph("Tipo IVA").setBold());
    table.addCell(new Paragraph("Total").setBold());

    table.addCell(new Paragraph("Motosierra"));
        table.addCell(new Paragraph(""+1));
        table.addCell(new Paragraph(""+99));
        table.addCell(new Paragraph(""+21+"%"));
        table.addCell(new Paragraph(""+190));

    layoutDocument.add(table);
}
}
