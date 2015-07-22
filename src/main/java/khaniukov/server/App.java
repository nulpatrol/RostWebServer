package khaniukov.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main application class
 *
 * @author Rostislav Khaniukov
 */
public class App {

    public static final String FRAME_TITLE        = "RostWebServer";
    public static final Logger errorLogger = LogManager.getLogger(WebServer.class);
    public static final Logger requestLogger = LogManager.getLogger("Request");

    /**
     * Main application method
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(600, 400);
        UIManager.put("TextArea.margin", new Insets(10, 10, 10, 10));

        JTextArea area = new JTextArea();

        area.append("[OK] RostWebServer initialised" + System.getProperty("line.separator"));
        area.append("World" + System.getProperty("line.separator"));
        area.append("World" + System.getProperty("line.separator"));

        area.setEditable(false);

        JScrollPane sp = new JScrollPane(area);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        frame.getContentPane().add(sp);
        frame.setResizable(false);
        frame.setVisible(true);

        ServerSocket servers;
        Socket client;
        Config.initialize(WebServer.class.getResource("/config.xml").getFile());

        try {
            servers = new ServerSocket(Config.getIntParam("Port"));
            while (true) {
                client = servers.accept();
                new WebServer(client).start();
            }
        } catch (IOException e) {
            errorLogger.error("[I/O error]" + e.getMessage());
            System.exit(-1);
        }
    }
}
