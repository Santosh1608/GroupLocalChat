import java.net.*;
import javax.swing.*;
import java.awt.*;      
import java.awt.event.*;
import java.io.*;
public class ClientChat
{
	JFrame frame = new JFrame("Group chat");
	JTextField text = new JTextField(14); 
	JButton btnsend = new JButton("Send");
	JTextArea area = new JTextArea(20,20);
	JPanel panelA = new JPanel();          
	JPanel panelB = new JPanel();
	Socket sock;
	PrintWriter write;
	BufferedReader read; 
	public static void main(String[] args){ 
		
		ClientChat start = new ClientChat(); 
		start.go();                            
	}                      
	public void setUpNetwork()                                     
	{ 
		try
		{
        sock = new Socket("192.168.43.150",5000);             
		write = new PrintWriter(sock.getOutputStream());        
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
                                                           
		
	}                                                               
	public void go()                                                                     
	{
		setUpNetwork();
	    Thread thread = new Thread(new Listen());   
	    thread.start();                                                                                   
		area.setLineWrap(true);                                                            
		JScrollPane scroll = new JScrollPane(area);                                         
	   	scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);    
    	scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    	btnsend.addActionListener(new SendListener());
		panelB.add(text);                                                                   
		panelB.add(btnsend);                                                               
		panelA.add(scroll);                                                                
		frame.getContentPane().add(BorderLayout.SOUTH,panelB);                              
		frame.getContentPane().add(BorderLayout.CENTER,panelA);                              
		frame.setSize(400,400);                                                            
		frame.setVisible(true);
	}
	class SendListener implements ActionListener
	{
	  public void actionPerformed(ActionEvent e)
	  {
       write.println(text.getText());
       write.flush();
       text.setText("");
	  }
	}
	class Listen implements Runnable
	{
		InputStreamReader in;
		public void run()
		{
		   try
		   {
            in = new InputStreamReader(sock.getInputStream());
            read = new BufferedReader(in);
            String msg="";
            while((msg=read.readLine())!=null)
            {
           	 area.append(msg+"\n");
            }
		  }
		  catch(Exception ex)
		  {
             ex.printStackTrace();
		  }

    	}
	}
}