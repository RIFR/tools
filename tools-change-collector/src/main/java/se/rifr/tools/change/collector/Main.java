package se.rifr.tools.change.collector;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import se.rifr.tools.common.FileIOSupport;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Richard Freyschuss {@literal <mailto:richard.freyschuss@gmail.com/>}
 */
public class Main {

    public static void main(String[] args) throws IOException {

        MainParameters param = new MainParameters();

        Arrays.stream(args).forEach(item -> {

            if (item.contains("--help"))
                param.setHelpRequested(true);
            else if (item.contains("-d="))
                param.setDirectoryName(item.replace("-d=", "").trim());
            else if (item.contains("-s="))
                param.setFileInputName(item.replace("-s=", "").trim());
            else if (item.contains("-r="))
                param.setReleaseName(item.replace("-r=", "").trim());
            else {
                System.out.println("UNKNOWN PARAMETER: " + item);
                param.setHelpRequested(true);
            }
        });

        if (param.isHelpRequested()) {
            System.out.println("--help: Print this information");
            System.out.println("-d=   : Bootstrap directory name <default=current directory>");
            System.out.println("-s=   : Source file name, source for the extraction of data <default=application-releases>");
            System.out.println("-r=   : Release name <default=current date>");
        } else
            releaseNoteCollector(param.getDirectoryName(), param.getFileInputName(), param.getReleaseName());

    }

    public static void releaseNoteCollector(String dirName, String fileName, String releaseName) throws IOException {

        FileIOSupport applicationsFileIOSupport = new FileIOSupport(fileName);

        Stream<String> applications = applicationsFileIOSupport.readAllLines().stream().sorted()
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .filter(item -> !item.startsWith("#"));

        PdfReleaseDataWriter pdfReleaseDataWriter = new PdfReleaseDataWriterImpl();
        AdocReleaseDataWriter adocReleaseDataWriter = new AdocReleaseDataWriterImpl();

        PdfWriter pdfWriter = null;

        String outputFileName = "release-" + releaseName;

        try (OutputStream outputStream = new FileOutputStream(outputFileName+ ".pdf")) {
            Document pdfDocument = new Document();
            pdfWriter = PdfWriter.getInstance(pdfDocument, outputStream);
            pdfWriter.open();
            pdfDocument.open();
            pdfDocument.addHeader("RELEASE NOTES", releaseName);

            applications.forEach(application -> {

                DataHtmlCollector dataHtmlCollector = new DataHtmlCollector();

                String applicationName, version;
                if (application.contains(" ")) {
                    applicationName = application.substring(0, application.indexOf(' '));
                    version = application.substring(application.indexOf(' ')).trim();
                } else {
                    applicationName = application;
                    version = "";
                }

                dataHtmlCollector.interprete(dirName + "/" + applicationName + "/src/changes/changes.xml", version);

                System.out.println(FileIOSupport.consoleColors.RED + applicationName + FileIOSupport.consoleColors.RESET + dataHtmlCollector);

                pdfReleaseDataWriter.writeHeader(applicationName);
                dataHtmlCollector.getReleaseVersions().forEach(pdfReleaseDataWriter::writeReleaseData);

                adocReleaseDataWriter.writeHeader(applicationName);
                dataHtmlCollector.getReleaseVersions().forEach(adocReleaseDataWriter::writeReleaseData);

            });

            pdfReleaseDataWriter.write(pdfDocument);
            pdfDocument.close();

            adocReleaseDataWriter.write(outputFileName+ ".adoc");

        } finally {
            if (pdfWriter!=null)
                pdfWriter.close();
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("The composed release information is presented in: " + outputFileName+ ".pdf");
        System.out.println("  - " + outputFileName+ ".pdf");
        System.out.println("  - " + outputFileName+ ".adoc");
        System.out.println("-------------------------------------------------------------------------");

    }
}
