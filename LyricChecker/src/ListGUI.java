import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ListGUI implements KeyListener
{
  private JTextField badAdd;
  private JTextField badRemove;
  private JTextField qAdd;
  private JTextField qRemove;
  private JTextArea output;
  
  public ListGUI()
  {
    JFrame as = new JFrame("View/Edit List");
    
    JPanel p = new JPanel();
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    p.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10)); //top left bottom right
    
    JLabel bad = new JLabel("Bad Words");
    bad.setFont(bad.getFont ().deriveFont (14.0f));
    bad.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(bad);
    
    badAdd = new JTextField("Add:", 30);
    badAdd.setMaximumSize(badAdd.getPreferredSize());
    p.add(badAdd);
    badAdd.addKeyListener(this);
    
    badRemove = new JTextField("Remove:", 30);
    badRemove.setMaximumSize(badRemove.getPreferredSize());
    p.add(badRemove);
    badRemove.addKeyListener(this);
    
    p.add(Box.createRigidArea(new Dimension(0,3))); //space between artfield/"song"
    
    JLabel q = new JLabel("Questionable Words");
    q.setFont(q.getFont ().deriveFont (14.0f));
    q.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(q);
    
    qAdd = new JTextField("Add:",30);
    qAdd.setMaximumSize(qAdd.getPreferredSize());
    p.add(qAdd);
    qAdd.addKeyListener(this);
    
    qRemove = new JTextField("Remove:",30);
    qRemove.setMaximumSize(qRemove.getPreferredSize());
    p.add(qRemove);
    qRemove.addKeyListener(this);
    
    p.add(Box.createRigidArea(new Dimension(0,10)));
    
    JLabel info = new JLabel("Input changes and press enter.");
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
    as.setSize(400,345);
    as.setLocationRelativeTo(null);
    as.setVisible(true);  
  }
  
  public void keyPressed(KeyEvent e) 
  {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_ENTER) 
    { 
      output.setBackground(Color.WHITE);
      if(!badAdd.getText().equals(null))
      { }
        
    }
  }
  public static void main(String[] args)
  {
    new ListGUI();
  }
  
  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}
}