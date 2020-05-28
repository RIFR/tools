package se.rifr.tools.change.collector;

import java.time.Instant;

/**
 * @author Richard Freyschuss {@literal <mailto:richard.freyschuss@gmail.com/>}
 */
public class MainParameters {

    private boolean helpRequested = false;
    private String directoryName = ".";
    private String fileInputName = "application-releases";
    private String releaseName = Instant.now().toString().substring(0,10);

    public boolean isHelpRequested() {
        return helpRequested;
    }

    public void setHelpRequested(boolean helpRequested) {
        this.helpRequested = helpRequested;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getFileInputName() {
        return fileInputName;
    }

    public void setFileInputName(String fileInputName) {
        this.fileInputName = fileInputName;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }
}
