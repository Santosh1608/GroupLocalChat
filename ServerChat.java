import java.net.*;
import java.io.*;
import java.util.*;
public class ServerChat
{
	ArrayList<Object> arr = new ArrayList<>();
	ServerSocket ser;
	public static void main(String[] args) {
		ServerChat start = new ServerChat();
		start.go();
	}
	public void go()
	{
		try
		{
			ServerSocket server = new ServerSocket(5000);
			while(true)
			{
				Socket client = server.accept();
				PrintWriter writer = new PrintWriter(client.getOutputStream());
				arr.add(writer);
				Thread t = new Thread(new ClientHandler(client));
				t.start();
				System.out.println("Got Connection");
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	class ClientHandler implements Runnable
	{
		BufferedReader reader;
		Socket sock;
		public ClientHandler(Socket client)
		{
           try
           {
             sock=client;
             InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
             reader=new BufferedReader(isReader);
           }
           catch(Exception ex)
           {
           	ex.printStackTrace();
           }
		}
		public void run()
		{
			String msg="";
			try
			{
				while((msg=reader.readLine())!=null)
				{
					tellEveryone(msg);
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		} 
	}
	public void tellEveryone(String msg)
	{
		Iterator it = arr.iterator();
		while(it.hasNext())
		{
			try
			{
				PrintWriter writer =(PrintWriter)it.next();
				writer.println(msg);
				writer.flush();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}