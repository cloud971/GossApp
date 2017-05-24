import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.border.*;

public class GUI extends JFrame{

    private JButton send = new JButton("Send");
    private JTextArea my_area= new JTextArea(14,32);
    private JTextField my_text= new JTextField(26);
    private ObjectOutputStream displayme;
    private ObjectInputStream readme;
    private String name;


    public GUI(ObjectInputStream readme, ObjectOutputStream displayme, String name){

        this.displayme = displayme;
        this.readme = readme;
        this.name = name;

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
        goss.add(my_text,GridC);

        GridC.anchor = GridBagConstraints.LAST_LINE_END;
        goss.add(send,GridC);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gosspip();
                my_text.setText("");
            }
        });

        add(goss);
        pack();
        setVisible(true);
    }

    public void gosspip(){

        String message = my_text.getText();
        my_area.append(name+": "+message+"\n");

    }

}
