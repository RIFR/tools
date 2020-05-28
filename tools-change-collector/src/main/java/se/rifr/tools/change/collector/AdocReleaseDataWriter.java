package se.rifr.tools.change.collector;

/**
 * @author Richard Freyschuss {@literal <mailto:richard.freyschuss@gmail.com/>}
 */
public interface AdocReleaseDataWriter {

    void writeHeader(String header);

    void writeReleaseData(ReleaseData releaseData);

    void write(String fileName);

}
