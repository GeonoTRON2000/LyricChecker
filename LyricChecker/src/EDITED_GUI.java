import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;
import java.awt.Frame;

public class BoxPanel extends JPanel{
    // instance variables - replace the example below with your own
    
    public BoxPanel()
    {
        setLayout(new BoxLayout(this, BoxLayout(this, BoxLayout.Y_AXIS)));
        
        JFrame frame = new JFrame("Artist/Album");
        frame.setTitle("Check Artist/Album");
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout(BoxLayout.Y_AXIS)));
        panel.setBorder(BorderFactory.createLoweredBevelBevelBorder());
        
        JLabel artist = new JLabel("Artist: ");
        label.setAlignmentX(SwingConstants.CENTER);
        label.setFont(label.getFont ().deriveFont (16.0f));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0,100)));
        
        JTextField artistfield = new JTextField();
        
        
    }
    
    public static void main(String[] args)
    {
        BoxPanel second = new BoxPanel();
    }
}
