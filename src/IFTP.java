import java.rmi.*;
import java.rmi.server.*;
/**
 *
 * @author William
 */

public interface IFTP extends Remote {
    
    public byte[] download(String username, String filename) throws RemoteException;

    public boolean upload(String name, byte[] file) throws RemoteException;

    public boolean login() throws RemoteException;

    public boolean logout() throws RemoteException;

    public boolean createnew() throws RemoteException;

    public String fetchDirectoryListing() throws RemoteException;
    
}
