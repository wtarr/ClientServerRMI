import FTP_Helpers.*;
import MessageLogger.Subject;

import javax.swing.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client
{
    private String username;
    private SystemInformation sysInfo;
    private IFTP ftp;
    private static Client uniqueInstance;
    private boolean connected = false;
    private boolean loggedIn = false;
    private Subject messageLogger;

    private Client()
    {

    }

    public static Client getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Client();
        }

        return uniqueInstance;
    }

    public boolean isConnected()
    {
        return connected;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getUsername() {
        return username;
    }

    public void setMessageLogger(Subject messageLogger)
    {
        this.messageLogger = messageLogger;
    }

    public boolean connect()
    {

        try {
            try
            {
                // Try to access external config first (only if running byte code)
                URL url = this.getClass().getResource("config/systeminfo.xml");
                File file = new File(url.toURI());
                sysInfo = new SystemInformation(file, messageLogger);
            }
            catch (Exception e)
            {
                // other wise go for the one compiled in the JAR  (if running directly from JAR)
                InputStream in = this.getClass().getResourceAsStream("config/systeminfo.xml");
                sysInfo = new SystemInformation(in, messageLogger);
            }

            ftp = (IFTP) Naming.lookup("rmi://" + sysInfo.getAddress() + ":" + sysInfo.getPort() + "/FTP_Servant");
            connected = true;
            return connected;

        } catch (Exception e) {
            messageLogger.setMessage("Error connecting to server :\n\n" + e.toString());
        }

        return false;
    }

    public boolean createNew(String txtUserName)
    {
        username = txtUserName.toLowerCase();

        try {
            return ftp.createnew(txtUserName);
        } catch (RemoteException e) {
            messageLogger.setMessage("Error user creating new user:\n\n" + e.toString());
        }

        return false;
    }

    public boolean login(String txtUserName)
    {
        username = txtUserName.toLowerCase();

        try {
            loggedIn = ftp.login(txtUserName);
            return loggedIn;
        }
        catch (RemoteException e)
        {
            messageLogger.setMessage("Error logging in :\n\n" + e.toString());
        }

        return false;
    }

    public boolean logout()
    {
        try {
            if (loggedIn)
                return ftp.logout(username);
            else
                System.exit(0);
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
           messageLogger.setMessage("Error during upload :\n\n" + e.toString());
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
           messageLogger.setMessage("Error during download : \n\n" + e.toString());
       }
        return false;
    }

    public DefaultListModel<String> fetchDirectoryListing()
    {
        DefaultListModel<String> list = new DefaultListModel<>();

        try {
            String message = ftp.fetchDirectoryListing(username);

            String[] array = message.split(";");
            for (String file : array) {
                list.addElement(file);
            }

            return list;


        } catch (RemoteException e) {
            messageLogger.setMessage("Error fetching directory listing : \n\n" + e.toString());
        }

        return new DefaultListModel<>();
    }

    public void exit() {

    }



}