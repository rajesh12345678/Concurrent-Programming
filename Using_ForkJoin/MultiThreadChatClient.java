import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




public class MultiThreadChatClient implements Runnable {
  

  // The client socket
  private static Socket clientSocket = null;
  // The output stream
  public static PrintStream os = null;
  // The input stream
  private static DataInputStream is = null;

  private static BufferedReader inputLine = null;
  private static boolean closed = false;


  public static void main(String[] args) {

  	try
  {
  Login frame=new Login();
  frame.setSize(300,100);
  frame.setVisible(true);
  }
  catch(Exception e)
  {JOptionPane.showMessageDialog(null, e.getMessage());}


   // The default port.
    int portNumber = 2222;
    // The default host.
    String host = "localhost";

    if (args.length < 2) {
      System.out
          .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
              + "Now using host=" + host + ", portNumber=" + portNumber);
    } else {
      host = args[0];
      portNumber = Integer.valueOf(args[1]).intValue();
    }

    /*
     * Open a socket on a given host and port. Open input and output streams.
     */
    try {
      clientSocket = new Socket(host, portNumber);
      inputLine = new BufferedReader(new InputStreamReader(System.in));
      os = new PrintStream(clientSocket.getOutputStream());
      is = new DataInputStream(clientSocket.getInputStream());
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host " + host);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to the host "
          + host);
    }

    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on the port portNumber.
     */
    if (clientSocket != null && os != null) {
      try {

        /* Create a thread to read from the server. */
        new Thread(new MultiThreadChatClient()).start();
        while (!closed) {
          os.println(inputLine.readLine().trim());
        }
        /*
         * Close the output stream, close the input stream, close the socket.
         */
        os.close();
        is.close();
        clientSocket.close();
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }

  /*
   * Create a thread to read from the server. (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  
  public void run() {
    /*
     * Keep on reading from the socket till we receive "Bye" from the
     * server. Once we received that then we want to break.
     */
    String responseLine;
    try {
      while ((responseLine = is.readLine()) != null) {
        //System.out.println(responseLine);
        if (responseLine.indexOf("*** Bye") != -1)
          break;
      }
      closed = true;
    } catch (IOException e) {
      System.err.println("IOException:  " + e);
    }
  }
}


class Login extends JFrame implements ActionListener
{
 JButton SUBMIT;
 JPanel panel,panel1;
 JLabel label1,label2,label3;
 JTextField  text1,text2,text3;
  Login()
  {
  label1 = new JLabel();
  label1.setText("Name:");
  text1 = new JTextField(15);

  label2 = new JLabel();
  label2.setText("Roll number:");
  text2 = new JPasswordField(15);

  label3 = new JLabel();
  label3.setText("Seat Number:");
  text3 = new JTextField(15);

  
 
  SUBMIT=new JButton("SUBMIT");
  
  panel=new JPanel(new GridLayout(4,1));
  panel.add(label1);
  panel.add(text1);
  panel.add(label2);
  panel.add(text2);
  panel.add(label3);
  panel.add(text3);
  panel.add(SUBMIT);
  add(panel,BorderLayout.CENTER);
  SUBMIT.addActionListener(this);
  setTitle("LOGIN FORM");
  }
 public void actionPerformed(ActionEvent ae)
  {
  String value1=text1.getText();
  
  String value2=text2.getText();
 
  String value3=text3.getText();
  
  
  if (value1.equals("barru") && value2.equals("1") || value1.equals("barru") && value2.equals("2") ||value1.equals("barru") && value2.equals("3") ||value1.equals("barru") && value2.equals("4") ||
    value1.equals("barru") && value2.equals("5") ||value1.equals("barru") && value2.equals("6") ||value1.equals("barru") && value2.equals("7") ||value1.equals("barru") && value2.equals("8") ||value1.equals("barru") && value2.equals("9") ||
    value1.equals("barru") && value2.equals("10") ||value1.equals("barru") && value2.equals("11") ||value1.equals("barru") && value2.equals("12") ||value1.equals("barru") && value2.equals("140") ||value1.equals("barru") && value2.equals("14") ||
    value1.equals("barru") && value2.equals("15") ||value1.equals("barru") && value2.equals("16") ||value1.equals("barru") && value2.equals("17") ||value1.equals("barru") && value2.equals("18") ||value1.equals("barru") && value2.equals("19") ||
    value1.equals("barru") && value2.equals("20")) {
  
   MultiThreadChatClient.os.println(value1);
   MultiThreadChatClient.os.println(value2);
   MultiThreadChatClient.os.println(value3);
  
  NextPage page=new NextPage();
  page.setVisible(true);
  JLabel label = new JLabel("Welcome:"+value1+" your seat number is :"+value3);
  page.getContentPane().add(label);
  page.setVisible(false);
  }
  else{
  System.out.println("enter the valid username and password");
  JOptionPane.showMessageDialog(this,"Incorrect login or password",
  "Error",JOptionPane.ERROR_MESSAGE);
  }
}
}