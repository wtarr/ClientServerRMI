import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import MessageLogger.*;

public class Server implements Observer {
    public static ArrayList<Client> clients;
    public static File root;
    private static Server uniqueInstance;
    private Subject messageLogger;
    private SystemInformation systemInformation;


    private Server(Subject messageLogger) {

        this.messageLogger = messageLogger;
        messageLogger.registerObserver(this);
        clients = new ArrayList<>();
        initiateServer();

    }

    public static Server getInstance(Subject messagesLogged)
    {
        if (uniqueInstance == null)
        {
            uniqueInstance = new Server(messagesLogged);
        }

        return uniqueInstance;
    }

    private void initiateServer() {
        try {

            try
            {
                // Try to access external config first (only if running byte code)
                URL url = this.getClass().getResource("config/systeminfo.xml");
                File file = new File(url.toURI());
                systemInformation = new SystemInformation(file, messageLogger);
            }
            catch (Exception e)
            {
                // other wise go for the one compiled in the JAR  (if running directly from JAR)
                InputStream in = this.getClass().getResourceAsStream("config/systeminfo.xml");
                systemInformation = new SystemInformation(in, messageLogger);
            }

            root = new File("ROOT");
            initialiseFolder(root);
            buildDirectoryListing();

            LocateRegistry.createRegistry(systemInformation.getPort());
            Naming.rebind("rmi://" + systemInformation.getAddress() + "/FTP_Servant", new FTP_Servant(messageLogger));

            messageLogger.setMessage("The FTP_Servant server is now ready");

        } catch (Exception e) {
            messageLogger.setMessage(e.toString());
        }

    }

    private void initialiseFolder(File file)
    {
        messageLogger.setMessage("Checking for root folder");

        if (!file.exists())
        {
            boolean mkdir = file.mkdir();

            if (mkdir)
            {
                messageLogger.setMessage("Root folder created.");
            }
        }
        else
        {
            messageLogger.setMessage("Folder found.");
        }

    }

    private void buildDirectoryListing()
    {
         Server.clients.clear();

        for (File clientFolder : root.listFiles())
        {
            Client c = new Client();
            c.setOwner(clientFolder.getName());
            for (File clientFile : clientFolder.listFiles())
            {
                c.filenameList.add(clientFile.getName());
            }
            Server.clients.add(c);
        }
    }

    @Override
    public void update(String msg) {
        // Receive and display messages from the message logger
        System.out.println(msg);
    }
}