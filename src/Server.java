import java.io.File;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Client> clients;
    public static File root;





    public Server() {
        clients = new ArrayList<Client>();
        initiateServer();
    }

    private void initiateServer() {
        try {

            root = new File("ROOT");
            initialiseFolder(root);
            buildDirectoryListing();

            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://localhost/FTP", new FTP());
            System.out.println("The FTP server is now ready");

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void initialiseFolder(File file)
    {
        System.out.println("Checking for root folder...");

        if (!file.exists())
        {
            boolean mkdir = file.mkdir();

            if (mkdir)
            {
                System.out.println("Root folder created.");
            }
        }
        else
        {
            System.out.println("Folder found.");
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

    public static void main(String[] args) {
        Server server = new Server();
    }
}