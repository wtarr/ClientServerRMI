import java.io.File;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import MessageLogger.*;

public class Server implements Observer {
    public static ArrayList<Client> clients;
    public static File root;
    private static Server uniqueInstance;
    private Subject messageLogger;


    private Server(Subject messageLogger) {

        this.messageLogger = messageLogger;
        messageLogger.registerObserver(this);
        clients = new ArrayList<Client>();
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

            root = new File("ROOT");
            initialiseFolder(root);
            buildDirectoryListing();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/FTP", new FTP(messageLogger));

            messageLogger.setMessage("The FTP server is now ready");

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
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println(msg);
    }
}