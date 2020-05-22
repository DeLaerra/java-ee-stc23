package lesson10.Config;

public enum ChatCommands {
    REG_USER("/reg_user"),
    PRIVATE_MESSAGE("/pm"),
    QUIT("/quit");

    private final String command;

    ChatCommands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
