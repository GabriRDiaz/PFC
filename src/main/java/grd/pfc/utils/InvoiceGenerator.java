/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grd.pfc.utils;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
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
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import grd.pfc.dao.ManagerDAO;
import grd.pfc.pojo.LineaPedido;
import grd.pfc.pojo.Pedido;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
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
    
    addTitle(layoutDocument);

    addCustomerInfo(layoutDocument);
    addTable(layoutDocument);
    
    layoutDocument.close();
    
    }
    private void addTitle(Document layoutDocument){
        Style title = new Style();
        PdfFont font;
        try {
            font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            title.setFont(font).setFontSize(26);
            title.setFontColor(Color.BLUE);
            layoutDocument.add(new Paragraph(new Text("Factura\nProforma").addStyle(title)).setBold().setTextAlignment(TextAlignment.CENTER));
            
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

        layoutDocument.add(table);

}
    
    private void addTable(Document layoutDocument){
    layoutDocument.add(new Paragraph("\n\n"));
    
    ArrayList<LineaPedido> lineas = new ManagerDAO().desglosarPedido(pedido.getId());
    Table table = new Table(UnitValue.createPointArray(new float[]{180f, 60f, 70f, 60f, 50f}));
    table.setHorizontalAlignment(HorizontalAlignment.CENTER);
    table.addCell(new Paragraph("Producto").setBold());
    table.addCell(new Paragraph("Cantidad").setBold());
    table.addCell(new Paragraph("Precio").setBold());
    table.addCell(new Paragraph("Tipo IVA").setBold());
    table.addCell(new Paragraph("Subtotal").setBold());
    
    DecimalFormat df = new DecimalFormat("#.##");
    double total=0;
    for(int i=0; i<lineas.size(); i++){
        table.addCell(new Paragraph(lineas.get(i).getNombre()));
        table.addCell(new Paragraph(""+lineas.get(i).getCantidad()));
        table.addCell(new Paragraph(""+df.format(lineas.get(i).getPrecioSinIva())+"€"));
        table.addCell(new Paragraph(""+df.format(lineas.get(i).getIva())+"%"));
        table.addCell(new Paragraph(""+df.format(lineas.get(i).getSubtotal())+"€"));
        total=total+lineas.get(i).getTotal();
    }
    
    layoutDocument.add(table);
    
    Table tbTotal = new Table(UnitValue.createPointArray(new float[]{370f, 50f}));
    tbTotal.setHorizontalAlignment(HorizontalAlignment.CENTER);
     Cell cellTotal = new Cell().add(new Paragraph("Total+IVA: ").setBold());
        cellTotal.setTextAlignment(TextAlignment.RIGHT);
    Cell cellTotalValue = new Cell().add(new Paragraph(df.format(total)+"€").setBold());
        cellTotalValue.setTextAlignment(TextAlignment.RIGHT);
    tbTotal.addCell(cellTotal);
    tbTotal.addCell(cellTotalValue);
    layoutDocument.add(tbTotal);
//    layoutDocument.add(new Paragraph(new Text("Importe total + IVA: "+df.format(total))).setBold().setTextAlignment(TextAlignment.RIGHT));
    
}
}
