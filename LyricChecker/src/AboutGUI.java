
import java.awt.*;
import javax.swing.*;
public class AboutGUI extends JPanel
{
    public AboutGUI() 
    {
        JFrame f = new JFrame("About");
        f.setTitle("About");
        
        JPanel about = new JPanel();
        about.setLayout(new BoxLayout(about, BoxLayout.Y_AXIS));
        about.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10));
        
        about.add(Box.createRigidArea(new Dimension(0,32)));
        
        JLabel label = new JLabel("About");
        label.setFont(label.getFont ().deriveFont (24.0f));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        about.add(label);
        
        String instructions = "\n1. Input Artist and Song/Album\n\n" +
        "2. Press Enter\n\n" + 
        "3. Returns Explicit, Clean or Questionable";
        
        JTextPane ins= new JTextPane();
        ins.setText(instructions);
        ins.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        about.add(Box.createRigidArea(new Dimension(0,32)));
        about.add(ins);
        
        JLabel creators = new JLabel("Carson Fleming, Brandon Liu, Marshall Vail");
        creators.setAlignmentX(Component.CENTER_ALIGNMENT);
        creators.setFont(creators.getFont ().deriveFont (10.0f));
        about.add(Box.createRigidArea(new Dimension(0,32)));
        about.add(creators);
        
        JLabel credz = new JLabel("Credits: MusixMatch, Metrolyrics");
        credz.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createRigidArea(new Dimension(0,32)));
        about.add(credz);
        
        f.add(about);
        f.setSize(300,360);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    
    public static void main(String[] args)
    {
        new AboutGUI();
    }
}
