import java.io.File;
import java.rmi.*;
import java.rmi.server.*;

/**
 * @author William
 */
public class FTP extends UnicastRemoteObject implements IFTP {


    public FTP() throws RemoteException {

    }


    @Override
    public synchronized byte[] download(String username, String filename) throws RemoteException {

        File dir = new File(Server.root, username.toLowerCase());
        File file = new File(dir, filename);

        return FileTransferHelper.sendHelper(file);
    }

    public synchronized boolean upload(String username, String filename, byte[] file) throws RemoteException {

        System.out.println("Receiving " + filename + " from " + username);

        File clientFolder = new File(Server.root, username.toLowerCase());

        File clientFile = new File(clientFolder, filename);

        boolean successful = FileTransferHelper.receiveHelper(clientFile, file);

        if (successful) {
            for (Client c : Server.clients) {
                if (c.getOwner().equals(username.toLowerCase())) {
                    if (!c.filenameList.contains(filename))
                        c.filenameList.add(filename);
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public synchronized boolean login(String username) throws RemoteException {

        boolean result = false;

        for (Client ele : Server.clients) {
            if (ele.getOwner().equals(username.toLowerCase())) {
                ele.setOnline(true);
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean logout(String username) throws RemoteException {

        boolean result = false;

        for (Client ele : Server.clients) {
            if (ele.getOwner().equals(username.toLowerCase())) {
                ele.setOnline(false);
                result = true;
            }
        }

        return result;

    }

    @Override
    public synchronized boolean createnew(String username) throws RemoteException {
        boolean result = true;
        // check if they exist!!!
        for (Client element : Server.clients) {
            if (element.getOwner().equals(username.toLowerCase())) {
                result = false;
                throw new RemoteException("User already exists!");
            }
        }

        Client newdir = new Client();
        newdir.setOwner(username.toLowerCase());
        Server.clients.add(newdir);

        File newuser = new File(Server.root, username.toLowerCase());
        newuser.mkdir();

        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public synchronized String fetchDirectoryListing(String username) throws RemoteException {

        System.out.println("Request for listing recieved from " + username);
        for (Client c : Server.clients) {
            if (c.getOwner().equals(username.toLowerCase())) {
                StringBuilder sb = new StringBuilder();

                for (String file : c.filenameList) {
                    sb.append(file);
                    sb.append(";");
                }

                System.out.println(sb.toString());

                return sb.toString();
            }

        }

        return "";

    }
}
