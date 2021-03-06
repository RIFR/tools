package se.rifr.tools.change.collector;

import com.lowagie.text.Document;

/**
 * @author Richard Freyschuss {@literal <mailto:richard.freyschuss@gmail.com/>}
 */
public interface PdfReleaseDataWriter {

    void writeHeader(String header);

    void writeReleaseData(ReleaseData releaseData);

    void write(Document document);

}
