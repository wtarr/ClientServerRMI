import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author William
 */
public class FTP extends UnicastRemoteObject implements IFTP {

    
    public FTP () throws RemoteException
    {
        
    }


    @Override
    public byte[] download(String username, String filename) throws RemoteException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean upload(String name, byte[] file) throws RemoteException {

        System.out.println(name);
        
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(name));
            
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        
            bos.write(file);
            bos.close();

        } catch (Exception ex) {
            System.out.print(ex);
        }
        
        return true;
    }

    @Override
    public boolean login() throws RemoteException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean logout() throws RemoteException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean createnew() throws RemoteException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String fetchDirectoryListing() throws RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}
