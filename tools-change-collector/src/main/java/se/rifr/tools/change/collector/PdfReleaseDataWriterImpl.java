package se.rifr.tools.change.collector;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

import java.awt.*;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public class PdfReleaseDataWriterImpl implements PdfReleaseDataWriter {

    private final Font font = new Font(Font.HELVETICA, 10, Font.BOLDITALIC);
    private final PdfPTable table = new PdfPTable(2);

    public PdfReleaseDataWriterImpl() {
        table.setWidthPercentage(100);
        table.setWidths(new float[]{6.0f, 6.0f});
    }

    @Override
    public void writeHeader(String header, Document document) {

        PdfPCell cell = new PdfPCell();

        //Table headers
        cell.setPhrase(new Phrase(header, font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("", font));
        table.addCell(cell);
    }

    public void writeReleaseData(ReleaseData releaseData, Document document) {

        try {
            table.addCell(releaseData.getReleaseInformation());
            table.addCell("");

            releaseData.getActionData().forEach(item -> {
                table.addCell(item.getAction());
                table.addCell(item.getActionNote()
                        .replace("type=","")
                        .replace("dev=",""));
            });

            table.addCell("");
            table.addCell("");

            //System.out.println("PDF using OpenPDF created successfully");
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void write(Document document) {
        try {
            document.add(table);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
     }

}
