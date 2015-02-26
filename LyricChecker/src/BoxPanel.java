
import java.awt.*;
import java.applet.Applet;
import javax.swing.*;
import java.awt.Frame;
import java.awt.event.*;

public class BoxPanel extends JPanel
{
    JFrame frame;
    public BoxPanel()
    {
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
        
        panel.add(Box.createRigidArea(new Dimension(0,100)));
        JButton button1 = new JButton("Check Artist/Song");
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFrame as = new JFrame("Artist/Song");
                as.setTitle("Artist/Song");
                
                JPanel p = new JPanel();
                p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
                //p.setBorder(BorderFactory.createLoweredBevelBorder());
                p.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10)); //top left bottom right
                
                JLabel art = new JLabel("Artist");
                art.setFont(art.getFont ().deriveFont (14.0f));
                art.setAlignmentX(Component.CENTER_ALIGNMENT);

                p.add(art);
                
                JTextField artfield = new JTextField(30);
                artfield.setMaximumSize(artfield.getPreferredSize());
                p.add(artfield);
                
                p.add(Box.createRigidArea(new Dimension(0,3))); //space between artfield/"song"
                
                JLabel song = new JLabel("Song");
                song.setFont(song.getFont ().deriveFont (14.0f));
                song.setAlignmentX(Component.CENTER_ALIGNMENT);
                p.add(song);
                
                JTextField sfield = new JTextField(30);
                sfield.setMaximumSize(sfield.getPreferredSize());
                p.add(sfield);
                
                p.add(Box.createRigidArea(new Dimension(0,10)));
                
                JLabel info = new JLabel("Input artist & song and press enter");
                info.setAlignmentX(Component.RIGHT_ALIGNMENT);
                p.add(info);
                p.add(Box.createRigidArea(new Dimension(0,3))); 
                
                JTextArea output = new JTextArea();
                output.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                output.setLineWrap(true);
                output.setWrapStyleWord(true);
                output.setEditable(false);
                p.add(output);
                
                as.add(p);
                as.setSize(400,300);
                as.setLocationRelativeTo(null);
                as.setVisible(true);                
            }
        });   
        panel.add(button1);
        
        panel.add(Box.createRigidArea(new Dimension(0,50)));
        
        JButton button2 = new JButton("Check Artist/Album");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JFrame ab = new JFrame("Artist/Album");
                ab.setTitle("Artist/Album");
                
                JPanel p2 = new JPanel();
                p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
                p2.setBorder(BorderFactory.createEmptyBorder(6, 10, 10, 10)); //top left bottom right
                
                JLabel artist = new JLabel("Artist");
                artist.setFont(artist.getFont ().deriveFont (14.0f));
                artist.setAlignmentX(Component.CENTER_ALIGNMENT);

                p2.add(artist);
                
                JTextField artistfield = new JTextField(30);
                artistfield.setMaximumSize(artistfield.getPreferredSize());
                p2.add(artistfield);
                
                p2.add(Box.createRigidArea(new Dimension(0,3))); //space between artfield/"song"
                
                JLabel album = new JLabel("Album");
                album.setFont(album.getFont ().deriveFont (14.0f));
                album.setAlignmentX(Component.CENTER_ALIGNMENT);
                p2.add(album);
                
                JTextField afield = new JTextField(30);
                afield.setMaximumSize(afield.getPreferredSize());
                p2.add(afield);
                
                p2.add(Box.createRigidArea(new Dimension(0,10)));
                
                JLabel info1 = new JLabel("Input artist & album and press enter");
                info1.setAlignmentX(Component.RIGHT_ALIGNMENT);
                p2.add(info1);
                
                p2.add(Box.createRigidArea(new Dimension(0,3))); 
                
                JTextArea output1 = new JTextArea();
                output1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                output1.setLineWrap(true);
                output1.setWrapStyleWord(true);
                output1.setEditable(false);
                p2.add(output1);
                
                ab.add(p2);
                ab.setSize(400,300);
                ab.setLocationRelativeTo(null);
                ab.setVisible(true);   
            }
        });   
        panel.add(button2);
        
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
       BoxPanel main = new BoxPanel();
   }
}
