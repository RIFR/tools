package se.rifr.tools.change.collector;

import se.rifr.tools.common.FileIOSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Richard Freyschuss {@literal <mailto:richard.freyschuss@gmail.com/>}
 */
public class AdocReleaseDataWriterImpl implements AdocReleaseDataWriter {

    private List<String> strings = new ArrayList<>();

    private boolean headerAdded = false;

    //-----------------------------------------------
    private final String headerSymbol = ".";
    private final String tableDescriminator = "|";

    // Start AND end with
    private final String tableSymbol = "\n|===\n";
    private final String bold = "*";
    private final String italic = "_";
    //-----------------------------------------------

    @Override
    public void writeHeader(String header) {

        strings.add("\n" + tableDescriminator + bold + header + bold + " \n\n");
        headerAdded = true;
    }

    @Override
    public void writeReleaseData(ReleaseData releaseData) {

        if (headerAdded) {
            headerAdded = false;
            strings.add(releaseData.getReleaseInformation() + tableDescriminator);
        }

        releaseData.getActionData().forEach(item -> {
            strings.add(item.getAction()+ "\n\n");
        });

    }

    @Override
    public void write(String fileName) {

        FileIOSupport fileIOSupport = new FileIOSupport(fileName);

        try {
            fileIOSupport.create();

            // Document Header
            fileIOSupport.write(headerSymbol + fileName.replace(".adoc","")+ "\n");
            //fileIOSupport.write("Doc Writer <richard.freyschuss@so4it.com>"+ "\n");
            //fileIOSupport.write(":toc:"+ "\n");
            fileIOSupport.write(tableSymbol);

            strings.forEach(str -> {
                try {
                    fileIOSupport.write(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            fileIOSupport.write(tableSymbol);

            fileIOSupport.close();

        } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static class symbols {

    }
}
