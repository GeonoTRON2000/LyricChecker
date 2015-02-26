import java.awt.*;
import javax.swing.*;

import musixmatch.MusixMatch;
import musixmatch.Track;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class SongGUI implements KeyListener
{
  private JTextField artistField;
  private JTextField songField;
  private JTextArea output;
  private ArrayList<String> badWords;
  private ArrayList<String> qWords;
  
  public SongGUI(ArrayList<String> bWords, ArrayList<String> questionWords)
  {
    badWords = bWords;
    qWords = questionWords;
    
    JFrame as = new JFrame("Search by song");
    
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    p.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10)); //top left bottom right
    
    JLabel art = new JLabel("Artist");
    art.setFont(art.getFont ().deriveFont (14.0f));
    art.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    p.add(art);
    
    artistField = new JTextField(30);
    artistField.setMaximumSize(artistField.getPreferredSize());
    p.add(artistField);
    artistField.addKeyListener(this);
    
    p.add(Box.createRigidArea(new Dimension(0,3))); //space between artfield/"song"
    
    JLabel album = new JLabel("Song");
    album.setFont(album.getFont ().deriveFont (14.0f));
    album.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(album);
    
    songField = new JTextField(30);
    songField.setMaximumSize(songField.getPreferredSize());
    songField.addKeyListener(this);
    p.add(songField);
    
    p.add(Box.createRigidArea(new Dimension(0,10)));
    
    JLabel info = new JLabel("Input artist & album and press enter");
    info.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(info);
    p.add(Box.createRigidArea(new Dimension(0,3))); 
    
    output = new JTextArea();
    output.setFont(new Font("monospaced", Font.PLAIN, 14));
    output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    output.setLineWrap(true);
    output.setWrapStyleWord(true);
    output.setEditable(false);
    JScrollPane pane = new JScrollPane();
    pane.getViewport().add(output);
    p.add(pane);
    
    as.add(p);
    as.setSize(400,245);
    as.setLocationRelativeTo(null);
    as.setVisible(true);  
  }
  
  public void keyPressed(KeyEvent e) 
  {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_ENTER) 
    { 
      output.setBackground(Color.WHITE);
      runSongCheck();
      songField.requestFocusInWindow();
      songField.selectAll();
    }
  }
  public void runSongCheck() 
  {
    output.setText("");
    Track t = null;
    try {
      t = MusixMatch.trackSearch(songField.getText(), artistField.getText());
    } catch (IOException e) {
      output.setBackground(Color.WHITE);
	  output.append("No such song.");
	  return;
    }
    if (t == null) {
      output.setBackground(Color.WHITE);
  	  output.append("No such song.");
  	  return;    	
    }
    LyricChecker l = new LyricChecker(t.getName(), t.getArtist(), badWords, qWords);
    l.checkLyrics();
    if(!l.lookupFailed())
    {
      output.setText("Expletives:\r\n"+l.foundBadWords()+"\r\n\r\n"+"Possible expletives:\r\n"+l.foundQWords());
      if(!l.hasBadWords() && !l.hasQWords())
        output.setBackground(Color.GREEN);
      else if(l.hasQWords() && !l.hasBadWords())
        output.setBackground(Color.YELLOW);
      else
        output.setBackground(Color.RED);
    }
    else
    {
      if (t.isExplicit()) {
    	  output.setBackground(Color.RED);
    	  output.setText("No lyrics found, MusixMatch marked as explicit: "+t.getName()+".");
      } else {
    	  output.setBackground(Color.GREEN);
    	  output.setText("No lyrics found, MusixMatch marked as clean: "+t.getName()+".");
      }
    }
  }
  
  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
}