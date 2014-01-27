import java.rmi.*;

/**
 *
 * @author William
 */

public interface IFTP extends Remote {
    
    public byte[] download(String username, String filename) throws RemoteException;

    public boolean upload(String username, String filename, byte[] file) throws RemoteException;

    public boolean login(String username) throws RemoteException;

    public boolean logout(String username) throws RemoteException;

    public boolean createnew(String username) throws RemoteException;

    public String fetchDirectoryListing(String username) throws RemoteException;
    
}
