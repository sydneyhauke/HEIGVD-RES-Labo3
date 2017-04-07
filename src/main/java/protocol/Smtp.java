package protocol;


public final class Smtp {
    public static final String CMD_EHLO = "EHLO ";
    public static final String CMD_MAIL_FROM = "MAIL FROM:";
    public static final String CMD_RCPT_TO = "RCPT TO:";
    public static final String CMD_DATA = "DATA";
    public static final String CMD_END_OF_DATA = ".";
    public static final String CMD_QUIT = "QUIT";
}
