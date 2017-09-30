import java.io.DataInputStream;
import java.io.PrintStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Thread;


public class client extends Thread {

	static String roll_num=null;
    static String seat_num=null;
    static Grylayout gl= new Grylayout();

  public static void main(String[] args) {

  	client[] threads = new client[20];

  	gl.Grylayout1("0","0"); 

    Socket clientSocket = null;
    DataInputStream is = null;
    DataInputStream inputLine = null;

    /*
     * Open a socket on port 2222. Open the input and the output streams.
     */
    try {
      clientSocket = new Socket("localhost", 2222);
      is = new DataInputStream(clientSocket.getInputStream());
      inputLine = new DataInputStream(new BufferedInputStream(System.in));
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host");
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to host");
    }

    /*
     * If everything has been initialized then we want to write some data to the
     * socket we have opened a connection to on port 2222.
     */
    if (clientSocket != null && is != null) {
      try {
      	int n=0;
        /*
         * Keep on reading from/to the socket till we receive the "Ok" from the
         * server, once we received that then we break.
         */
        System.out.println("The client started. Type any text. To quit it type 'Ok'.");
        String responseLine;
        
        int i=0;
        while ((responseLine = is.readLine()) != null) {
          System.out.println(responseLine);
          	 //gl.frame.setVisible(false);
    	    if(i==0){
    	    	roll_num=responseLine;
    	    i++;
    		}else if(i==1){
    			seat_num=responseLine;
    			threads[n]=new client();
    			threads[n].start();
         // gl.Grylayout1(roll_num,seat_num);
          i=0;
          n++;
    		}
    	      
    	      
          if (responseLine.indexOf("Ok") != -1) {
            break;
          }
         
        }

        /*
         * Close the output stream, close the input stream, close the socket.
         */
        
        is.close();
        clientSocket.close();
      } catch (UnknownHostException e) {
        System.err.println("Trying to connect to unknown host: " + e);
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }

   
  }

   public void run() {
   		System.out.println(roll_num+seat_num);
        gl.Grylayout1(roll_num,seat_num);
     }

}


class Grylayout{
	JFrame frame = new JFrame("Teachers Display");
	JPanel panel = new JPanel();
    JLabel jl[] = new JLabel[20];

	void Grylayout1(String roll_num,String seat_num) {
	
	panel.setLayout(new GridLayout(4,5,3,3));
	
	String[] pics = new String[20];

	int s_num=Integer.parseInt(seat_num);

	if(roll_num.equals("0") && s_num== 0){

	for(int i=0;i<20;i++){
		pics[i] = "/home/bharadwajh/Desktop/pics/0.jpg";
		jl[i]=new JLabel();
		jl[i].setIcon(new ImageIcon(pics[i]));
		panel.add(jl[i]);
		}
	}else{
		s_num=s_num-1;
		pics[s_num]="/home/bharadwajh/Desktop/pics/"+roll_num+".jpg";
		jl[s_num].setIcon(new ImageIcon(pics[s_num]));
		
		for(int j=0;j<20;j++){
			panel.add(jl[j]);
		}
	}

    frame.add(panel);

    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

