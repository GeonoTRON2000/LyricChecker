
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
        
        about.add(Box.createRigidArea(new Dimension(0,10)));
        
//        JLabel label = new JLabel("About");
//        label.setFont(label.getFont ().deriveFont (24.0f));
//        label.setAlignmentX(Component.CENTER_ALIGNMENT);
//        about.add(label);
//        about.add(Box.createRigidArea(new Dimension(0,15)));
        JLabel title = new JLabel("Lyrics Checker, updated 2/27/15.");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(title);
        about.add(Box.createRigidArea(new Dimension(0,15)));
        
        JLabel creators = new JLabel("A CSC420 Design Project by: ");
        creators.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(creators);
        //creators.setFont(creators.getFont ().deriveFont (10.0f));
        //about.add(Box.createRigidArea(new Dimension(0,5)));
        
        JLabel creators1 = new JLabel("Brandon Liu '17, Carson Fleming '18, Marshall Vail '17");
        creators1.setAlignmentX(Component.CENTER_ALIGNMENT);
        //creators.setFont(creators.getFont ().deriveFont (10.0f));
        //about.add(Box.createRigidArea(new Dimension(0,15)));
        about.add(creators1);
        
        JLabel credz = new JLabel("Info and lyrics provided by MusixMatch and Metrolyrics.");
        credz.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createRigidArea(new Dimension(0,15)));
        about.add(credz);
        
        JLabel j = new JLabel("\"Built with WPEA 90.5 FM in mind.\"");
        j.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(Box.createRigidArea(new Dimension(0,15)));
        about.add(j);
        
        JLabel n = new JLabel("                   -Brandon :)");
        n.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.add(n);
        
        f.add(about);
        f.setSize(400,205);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    
    public static void main(String[] args)
    {
        new AboutGUI();
    }
}
