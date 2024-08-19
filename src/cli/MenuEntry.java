package cli;

public class MenuEntry {
    private String name;
    private Executable action;

    public MenuEntry(String name) {
        this.name = name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getName() {
        return this.name;
    }

    public void setAction(Executable newAction) {
        this.action = newAction;
    }

    public void execute() {
        this.action.execute();
    }
}
