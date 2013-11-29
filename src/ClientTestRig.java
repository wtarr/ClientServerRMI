import java.rmi.*;
import java.io.File;
import java.io.FileInputStream;

public class ClientTestRig {
    public static void main(String[] argv) {
        try {
            IFTP ftp = (IFTP) Naming.lookup("rmi://localhost/FTP");
            File f = new File("GDP.pdf");
            int len = (int) (f.length());
            FileInputStream fis = new FileInputStream(f);
            byte[] buffy = new byte[len];
            fis.read(buffy);

            boolean result = ftp.upload("GDP.pdf", buffy);

            System.out.println(result);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


