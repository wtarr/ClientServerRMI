import javax.swing.JOptionPane;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class Client
{
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
        //throw new UnsupportedOperationException();
        return true;
    }

    public boolean createNew(String txtUserName)
    {
        throw new UnsupportedOperationException();
    }

    public boolean login(String txtUserName)
    {
        throw new UnsupportedOperationException();
    }

    public boolean logout()
    {
        throw new UnsupportedOperationException();
    }

    public void upload(File file)
    {
        throw new UnsupportedOperationException();
    }

    public boolean download(File file)
    {
        throw new UnsupportedOperationException();
    }

    public DefaultListModel<String> fetchDirectoryListing()
    {
        throw new UnsupportedOperationException();
    }

    public void exit() {

    }

}