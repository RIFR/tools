package se.rifr.tools.change.collector;

public class ActionData {

    private String action;
    private String actionNote;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionNote() {
        return actionNote;
    }

    public void setActionNote(String actionNote) {
        this.actionNote = actionNote;
    }

    @Override
    public String toString() {
        return " {" + action +
                "," + actionNote + '}';
    }

}
