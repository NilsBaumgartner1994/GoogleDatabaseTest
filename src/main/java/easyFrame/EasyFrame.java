package easyFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import easyBasic.Logger;

public class EasyFrame {
	
	JFrame frame;
	JPanel panel;
	
	public EasyFrame(String title, int width, int height) {
		frame = new JFrame();

		// make sure the program exits when the frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(title);
		frame.setSize(width, height);

		// This will center the JFrame in the middle of the screen
		frame.setLocationRelativeTo(null);
		
		frame.setVisible(true);
		
		initGameBoardView();
	}
	
	public EasyFrame() {
		this("MyApplication",640*2,480*2);
	}
	
	private void initGameBoardView() {
		panel = new JPanel();
		 
        // JButton mit Text "Drück mich" wird erstellt
        EasyFrameButton button = new EasyFrameButton(panel,"Drück mich");
        
        button.addActionListener(getClickedActionListener());
 
        frame.add(panel);
 
        // Fenstergröße wird so angepasst, dass 
        // der Inhalt reinpasst    
        //frame.pack();
	}
	
	public ActionListener getClickedActionListener() {
		ActionListener listener = new ActionListener() { 
      	  public void actionPerformed(ActionEvent e) { 
      		    Logger.print("Hallo");
      		  } 
		};
      	return listener;
	}

}
