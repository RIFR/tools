package se.rifr.tools.change.collector;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author Richard Freyschuss {@literal <mailto:richard.freyschuss@gmail.com/>}
 */
public class ReleaseData {

    private String releaseInformation;
    private List<ActionData> actionData = Lists.newArrayList();

    public String getReleaseInformation() {
        return releaseInformation;
    }

    public void setReleaseInformation(String releaseInformation) {
        this.releaseInformation = releaseInformation;
    }

    public List<ActionData> getActionData() {
        return actionData;
    }

    public void setActionData(List<ActionData> actionData) {
        this.actionData = actionData;
    }

    public void appendActionData(ActionData actionData) {
        this.actionData.add(actionData);
    }

    @Override
    public String toString() {
        return " {" + releaseInformation +
                "," + actionData + '}';
    }

}
