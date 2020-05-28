package se.rifr.tools.change.collector;

import com.google.common.collect.Lists;
import se.rifr.tools.common.FileIOSupport;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Richard Freyschuss {@literal <mailto:richard.freyschuss@gmail.com/>}
 */
public class DataHtmlCollector {

    private String title;
    private String auther;

    private List<ReleaseData> releaseVersions = Lists.newArrayList();

    private boolean tempActionStarted;
    private String tempAction;
    private ReleaseData tempReleaseData;
    private ActionData tempActionData;
    private boolean filterThisVersion;

    public String getTitle() {
        return title;
    }

    public String getAuther() {
        return auther;
    }

    public List<ReleaseData> getReleaseVersions() {
        return releaseVersions;
    }

    public void interprete (String fileName, String version) {

        FileIOSupport changesFileIOSupport = new FileIOSupport(fileName);

        tempActionStarted = false;
        tempAction = "";
        tempReleaseData = new ReleaseData();
        tempActionData = new ActionData();
        filterThisVersion = false;

        final boolean allVersions = version.isEmpty();

        try {
            Stream<String> changes = changesFileIOSupport.readAllLines().stream()
                    .map(String::trim)
                    .filter(str -> !str.isEmpty())
                    .filter(str -> !str.startsWith("<document"))
                    .filter(str -> !str.startsWith("xsi:schemaLocation="))
                    .filter(str -> !str.startsWith("xmlns:xsi="))
                    .filter(str -> !str.startsWith("<body>"))
                    .filter(str -> !str.startsWith("</body>"));

            changes.forEach(line -> {

                if (line.startsWith("<title>"))
                    title=line.substring(line.indexOf('>')+1,line.indexOf("</title>"));

                if (line.startsWith("<author"))
                    auther=line.substring(line.indexOf('>')+1,line.indexOf("</author>"));

                if (line.startsWith("<release")) {
                    tempReleaseData.setReleaseInformation(line.substring(line.indexOf(' ')+1,line.indexOf('>'))
                    .replace("version=","")
                            .replace("date=","")
                            .replace("description=","")
                            .replace("\"","")
                            .trim());
                    if (!allVersions) {
                        filterThisVersion = !tempReleaseData.getReleaseInformation().contains(version);
                    }
                }


                if (line.startsWith("<action")) {
                    tempActionData.setActionNote(line.substring(8, line.indexOf('>'))
                            //.replace("type=","")
                            //.replace("dev=","")
                            .replace("\"",""));
                    tempActionStarted = true;
                    tempAction = "";
                } else if (line.startsWith("</action>")) {
                    tempActionStarted = false;
                    tempActionData.setAction(tempAction);

                    tempReleaseData.appendActionData(tempActionData);
                    tempActionData = new ActionData();

                } else if (tempActionStarted)
                    tempAction = tempAction + line + " ";

                if (line.startsWith("</release>")) {
                    if (!filterThisVersion)
                        releaseVersions.add(tempReleaseData);

                    tempReleaseData = new ReleaseData();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return " {" + title +
//                "," + auther +
                "," + releaseVersions + '}';
    }
}
