import java.rmi.*;
import java.rmi.server.*;
public class Server
{
	public static void main(String [] args)
	{
		try
		{
			Naming.rebind("rmi://localhost/FTP", new FTP());
			System.out.println( "The FTP server is now ready" );
		}
		catch (Exception e)
		{
			System.out.println( e);
		}
	}
}