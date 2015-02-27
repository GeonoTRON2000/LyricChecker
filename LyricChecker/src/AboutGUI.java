
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
        about.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        about.add(Box.createRigidArea(new Dimension(0,15)));
        
//        JLabel label = new JLabel("About");
//        label.setFont(label.getFont ().deriveFont (24.0f));
//        label.setAlignmentX(Component.CENTER_ALIGNMENT);
//        about.add(label);
//        about.add(Box.createRigidArea(new Dimension(0,15)));
      
        JLabel creators = new JLabel("CSC420 Design Project by: ");
        creators.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(creators);
        //creators.setFont(creators.getFont ().deriveFont (10.0f));
        //about.add(Box.createRigidArea(new Dimension(0,5)));
        
        JLabel creators1 = new JLabel("Brandon Liu, Carson Fleming, Marshall Vail");
        creators1.setAlignmentX(Component.CENTER_ALIGNMENT);
        //creators.setFont(creators.getFont ().deriveFont (10.0f));
        //about.add(Box.createRigidArea(new Dimension(0,15)));
        about.add(creators1);
        
        JLabel credz = new JLabel("Info and lyrics provided by MusixMatch and Metrolyrics");
        credz.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createRigidArea(new Dimension(0,15)));
        about.add(credz);
        
        JLabel j = new JLabel("\"Built with WPEA 90.5 in mind\" -Brandon");
        j.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createRigidArea(new Dimension(0,15)));
        about.add(j);
        
        f.add(about);
        f.setSize(400,170);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    
    public static void main(String[] args)
    {
        new AboutGUI();
    }
}
