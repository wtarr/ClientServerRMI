import java.rmi.*;
import java.io.File;
import java.io.FileInputStream;

public class ClientTestRig {
    public static void main(String[] argv) {
        try {
            IFTP ftp = (IFTP) Naming.lookup("rmi://localhost:1099/FTP");
            String username = "WiLlIam";

            boolean result = createNew(ftp, username);

            if (result)
            {
                System.out.println("create new successful");
            }
            else
            {
                System.out.println("Already have account");
            }

            result = ftp.login(username);

            if (result)
            {
                //System.out.println("login successful");
                //result = testUpload(username, ftp);
            }

            if (result)
            {
                //System.out.println("testUpload successful");
                System.out.println(ftp.fetchDirectoryListing(username));
            }

            if (result)
            {
                System.out.println("downloading");
                ftp.download(username, "GDP.pdf");
            }

            System.out.println("Logout = " + ftp.logout(username));



        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean createNew(IFTP ftp, String username)
    {
        boolean result = false;

        try {
            result = ftp.createnew(username);
        } catch (RemoteException e) {
            System.out.println("User already exists!");
        }

        return result;

    }

    public static boolean testUpload(String user, IFTP ftp) {
        boolean result = false;
        File f = new File("GDP.pdf");
        int len = (int) (f.length());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);

            byte[] buffy = new byte[len];
            fis.read(buffy);

            result = ftp.upload(user, "GDP.pdf", buffy);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;

    }
}


