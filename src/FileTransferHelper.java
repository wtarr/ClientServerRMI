import MessageLogger.Observer;
import MessageLogger.Subject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 06/12/13
 * Time: 02:53
 * To change this template use File | Settings | File Templates.
 */
public class FileTransferHelper {

    private Subject messageLogger;

    public FileTransferHelper(Subject messageLogger)
    {
        this.messageLogger = messageLogger;
    }

    public boolean receiveHelper(File destination, byte[] file)
    {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destination);

            BufferedOutputStream bos = new BufferedOutputStream(fos);

            bos.write(file);
            bos.close();

            return true;

        } catch (Exception ex) {
            System.out.print(ex);
        }

        return false;
    }

    public byte[] sendHelper(File file)
    {
        byte[] buffy = new byte[0];

        if (!file.exists())
        {
            return new byte[0];
        }

        int len = (int) (file.length());

        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
            buffy = new byte[len];
            fis.read(buffy);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffy;
    }
}
