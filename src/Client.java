import javax.swing.JOptionPane;
import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class Client
{
    private String username;
    private SystemInformation sysInfo;
    private IFTP ftp;
    private static Client uniqueInstance;

    private Client()
    {
        connect();
    }

    public static Client getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Client();
        }

        return uniqueInstance;
    }

    public boolean connect()
    {

        try {
            File f = new File(getClass().getResource("config/systeminfo.xml").toURI());
            sysInfo = new SystemInformation(f);
            ftp = (IFTP) Naming.lookup("rmi://" + sysInfo.getAddress() + ":" + sysInfo.getPort() + "/FTP");

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return true;
    }

    public boolean createNew(String txtUserName)
    {
        username = txtUserName.toLowerCase();

        try {
            return ftp.createnew(txtUserName);
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return false;
    }

    public boolean login(String txtUserName)
    {
        username = txtUserName.toLowerCase();

        try {
            return ftp.login(txtUserName);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public boolean logout()
    {
        try {
            return ftp.logout(username);
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return false;

    }

    public boolean upload(File file)
    {
        try {
            return ftp.upload(username, file.getName(), FileTransferHelper.sendHelper(file));
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return false;
    }

    public boolean download(File file)
    {
       try {
           byte[] rec = ftp.download(username, file.getName());
           FileTransferHelper.receiveHelper(file, rec);

           if (file.exists())
               return true;
       }
       catch (RemoteException e)
       {
                     e.printStackTrace();

       }
        return false;
    }

    public DefaultListModel<String> fetchDirectoryListing()
    {
        DefaultListModel<String> list = new DefaultListModel<String>();

        try {
            String message = ftp.fetchDirectoryListing(username);

            String[] array = message.split(";");
            for (String file : array) {
                list.addElement(file);
            }

            return list;


        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return new DefaultListModel<>();
    }

    public void exit() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
}