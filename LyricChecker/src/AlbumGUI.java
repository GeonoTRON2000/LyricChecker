import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.IOException;
import musixmatch.*;

public class AlbumGUI implements KeyListener
{
  private JTextField artistField;
  private JTextField albumField;
  private JTextArea output;
  private ArrayList<String> badWords;
  private ArrayList<String> qWords;
  
  public AlbumGUI(ArrayList<String> bWords, ArrayList<String> questionWords)
  { 
    badWords = bWords;
    qWords = questionWords;
    
    JFrame as = new JFrame("Album/Artist");
    as.setTitle("Search by album");
    
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
    
    JLabel album = new JLabel("Album");
    album.setFont(album.getFont ().deriveFont (14.0f));
    album.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(album);
    
    albumField = new JTextField(30);
    albumField.setMaximumSize(albumField.getPreferredSize());
    albumField.addKeyListener(this);
    p.add(albumField);
    
    p.add(Box.createRigidArea(new Dimension(0,10)));
    
    JLabel info = new JLabel("Input artist & album and press enter");
    info.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(info);
    p.add(Box.createRigidArea(new Dimension(0,3))); 
    
    output = new JTextArea();
    output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    output.setLineWrap(true);
    output.setWrapStyleWord(true);
    output.setEditable(false);
    JScrollPane pane = new JScrollPane();
    pane.getViewport().add(output);
    p.add(pane);
    
    as.add(p);
    as.setSize(400,300);
    as.setLocationRelativeTo(null);
    as.setVisible(true);  
  }
  public void keyPressed(KeyEvent e) 
  {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_ENTER) 
    { 
      output.setBackground(Color.WHITE);
      runAlbumCheck();
      albumField.requestFocusInWindow();
      albumField.selectAll();
    }
  }
  public void runAlbumCheck() {
    try {
      output.setText("");
      Playlist album = MusixMatch.albumSearch(albumField.getText(), artistField.getText());
      if (album == null)  {
        output.setText("No such album.");
        return;
      }
      for (Track t : album) {
        output.append(t.getName());
        if (t.isExplicit()) 
        {
          LyricChecker lc = new LyricChecker(t.getName(), t.getArtist(), badWords, qWords);
          if (lc.hasBadWords()) output.append(" [Bad Words]");
          else if (lc.hasQWords()) output.append(" [Questionable]");
          else output.append(" [Clean]");
        }
        else {
          output.append(" [Clean]");
        }
        output.append("\r\n");
   
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
}