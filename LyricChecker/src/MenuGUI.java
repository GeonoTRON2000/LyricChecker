import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuGUI extends JPanel
{
  private static final long serialVersionUID = 4552711288895606017L;
  private ArrayList<String> badWords;
  private ArrayList<String> qWords;
  
  public MenuGUI()
  {
    badWords = new ArrayList<String>(Arrays.asList("asshole","bitch","cocksucker","cunt","dick","fuck","nigga","nigger","piss","pussy","shit"));
    qWords = new ArrayList<String>(Arrays.asList(" ass ","bastard","damn","hell","sex"));
    JFrame frame;

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
    frame = new JFrame("Lyrics Checker");
    frame.setTitle("Lyrics Checker 4.20");
    
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createLoweredBevelBorder());
    
    JLabel label = new JLabel("Lyrics Checker");
    label.setFont(label.getFont ().deriveFont (24.0f));
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    label.setBorder(BorderFactory.createTitledBorder("4.20"));
    
    panel.add(Box.createRigidArea(new Dimension(0,50)));
    
    panel.add(label);
    
    panel.add(Box.createRigidArea(new Dimension(0,50)));
    
    JButton button1 = new JButton("Check Artist/Song");
    button1.setAlignmentX(Component.CENTER_ALIGNMENT);
    button1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
    	  new SongGUI(badWords, qWords);
      }
    });   
    panel.add(button1);
    
    panel.add(Box.createRigidArea(new Dimension(0,50)));
    
    JButton button2 = new JButton("Check Artist/Album");
    button2.setAlignmentX(Component.CENTER_ALIGNMENT);
    button2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        new AlbumGUI(badWords, qWords);
      }
    });   
    panel.add(button2);
    
    panel.add(Box.createRigidArea(new Dimension(0,50)));
    
    JButton button3 = new JButton("Check Spotify ID(s)");
    button3.setAlignmentX(Component.CENTER_ALIGNMENT);
    button3.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e)
    	{
    		new SpotifyGUI(badWords, qWords).setVisible(true);
    	}
    });
    panel.add(button3);
    
    panel.add(Box.createRigidArea(new Dimension(0,50)));
    
    JLabel credits = new JLabel("Brandon Liu, Carson Fleming, Marshall Vail");
    credits.setFont(label.getFont ().deriveFont (5.0f));
    credits.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(credits);
    
    frame.add(panel);
    frame.setSize(400,400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
 
  
  public static void main(String[] args)
  {
	new MenuGUI();
  }
}
