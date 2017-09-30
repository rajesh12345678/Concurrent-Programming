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


public class Client {
  public static void main(String[] args) {

  	  
  	Grylayout gl= new Grylayout();
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

        /*
         * Keep on reading from/to the socket till we receive the "Ok" from the
         * server, once we received that then we break.
         */
        System.out.println("The client started. Type any text. To quit it type 'Ok'.");
        String responseLine;
        String roll_num=null;
        String seat_num=null;
        int i=0;
        while ((responseLine = is.readLine()) != null) {
          System.out.println(responseLine);
          	 //gl.frame.setVisible(false);
    	    if(i==0){
    	    	roll_num=responseLine;
    	    i++;
    		}else if(i==1){
    			seat_num=responseLine;
    			gl.Grylayout1(roll_num,seat_num);
    			i=0;
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
}


class Grylayout{
	JFrame frame = new JFrame("Teachers Display");
	JPanel panel = new JPanel();
    JLabel jl[] = new JLabel[20];

	void Grylayout1(String roll_num,String seat_num) {

	panel.setLayout(new GridBagLayout());

  GridBagConstraints gbc = new GridBagConstraints();
	
	String[] pics = new String[20];

	int s_num=Integer.parseInt(seat_num);
  

	if(roll_num.equals("0") && s_num== 0){

    int row=0,col=0;
  int temp=0;


	for(int i=0;i<20;i++){
    if(temp == 5){
      row++;
      temp=0;
    }
    col=temp;
		pics[i] = "/home/bharadwajh/Desktop/pics/0.jpg";
		jl[i]=new JLabel();
		jl[i].setIcon(new ImageIcon(pics[i]));
    gbc.gridx = col;
    gbc.gridy = row;
		panel.add(jl[i],gbc);
		temp++;
    }
	}else{
		pics[s_num]="/home/bharadwajh/Desktop/pics/"+roll_num+".jpg";
		jl[s_num].setIcon(new ImageIcon(pics[s_num]));
    
    System.out.println(s_num%5);
    System.out.println(s_num/5);

    int x,y;
    
    x=s_num%5;
    y=s_num/5;

    gbc.gridx = x;
    gbc.gridy = y;
		
    panel.add(jl[s_num],gbc);
	}

    frame.add(panel);

    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

