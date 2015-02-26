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
    
    panel.add(Box.createRigidArea(new Dimension(0,32)));
    
    panel.add(label);
    
    panel.add(Box.createRigidArea(new Dimension(0,35)));
    
    JButton button1 = new JButton("Check by Artist/Song");
    button1.setAlignmentX(Component.CENTER_ALIGNMENT);
    button1.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
       new SongGUI(badWords, qWords);
      }
    });   
    panel.add(button1);
    button1.setFocusPainted(false);
    
    panel.add(Box.createRigidArea(new Dimension(0,35)));
    
    JButton button2 = new JButton("Check by Artist/Album");
    button2.setAlignmentX(Component.CENTER_ALIGNMENT);
    button2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        new AlbumGUI(badWords, qWords);
      }
    });   
    panel.add(button2);
    
    panel.add(Box.createRigidArea(new Dimension(0,35)));
    
    JButton button3 = new JButton("Check by Spotify URI");
    button3.setAlignmentX(Component.CENTER_ALIGNMENT);
    button3.addActionListener(new ActionListener() {
     public void actionPerformed(ActionEvent e)
     {
      new SpotifyGUI(badWords, qWords).setVisible(true);
     }
    });
    panel.add(button3);
    
    panel.add(Box.createRigidArea(new Dimension(0,30)));
    
    JLabel credits = new JLabel("Brandon Liu, Carson Fleming, Marshall Vail");
    credits.setFont(label.getFont ().deriveFont (10.0f));
    credits.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(credits);
    
    frame.add(panel);
    frame.setSize(300,360);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
 
  public static String makeLevelLeft(String str) //make string 35 characters by truncating or adding spaces
  {
    if(str.length() > 35)
    {
      str = str.substring(0, 31) + "... ";
    }
    else
    {
      while(str.length() < 35)
        str += ' ';
    }
    return str;
  }
  
  public static String makeLevelRight(String str) //make string 15 characters by truncating or adding spaces
  {
    if (str.length() > 15)
    {
      str = str.substring(0, 11) + "... ";
    }
    else
    {
      while (str.length() < 15)
      {
        str = ' ' + str;
      }
    }
    return str;
  }
  
  public static void main(String[] args)
  {
    new MenuGUI();
  }
}
