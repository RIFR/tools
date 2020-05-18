package se.rifr.tools.change.collector;

import com.lowagie.text.Document;

/**
 * @author Magnus Poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface PdfReleaseDataWriter {

    void writeHeader(String header, Document document);

    void writeReleaseData(ReleaseData releaseData, Document document);

    void write(Document document);

}
