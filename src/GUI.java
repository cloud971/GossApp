
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class GUI extends JFrame{

    private JButton send = new JButton("Send");
    private JTextArea my_area= new JTextArea(14,32);
    private JTextField my_field= new JTextField(26);


    public GUI(){

    }

    public void create(){

        JPanel goss = new JPanel(new GridBagLayout());
        GridBagConstraints GridC = new GridBagConstraints();// constraints for layout
        GridC.insets = new Insets(10,10,10,10); // spacing

        GridC.gridx = 1;
        GridC.gridy = 1;
        my_area.setEditable(false);

        Border my_border = BorderFactory.createLineBorder(Color.BLACK);
        my_area.setBorder(BorderFactory.createCompoundBorder(my_border,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        my_area.setLineWrap(true);
        goss.add(my_area,GridC);

        GridC.gridx =1;
        GridC.gridy=2;
        GridC.anchor = GridBagConstraints.LINE_START;

       // my_field.setBorder(BorderFactory.createCompoundBorder(my_border,
         //       BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        goss.add(my_field,GridC);

        GridC.anchor = GridBagConstraints.LAST_LINE_END;
        goss.add(send,GridC);

        add(goss);
        pack();
        setVisible(true);
    }

}
