import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.MessageDigest;
import javax.swing.border.*;

public class GUI extends JFrame{

    private JButton send = new JButton("Send");
    private JButton sign_out = new JButton("Log Out");
    private JTextArea my_area= new JTextArea(14,32);
    private JTextField my_text= new JTextField(26);
    private JScrollPane scroll;
    private ObjectOutputStream displayme;
    private ObjectInputStream readme;
    private String name;
    private String unique;


    public GUI(ObjectInputStream readme, ObjectOutputStream displayme, String name, String unique){

        this.displayme = displayme;
        this.readme = readme;
        this.name = name;
        this.unique = unique;

    }

    public void create(boolean[]go){

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
        scroll = new JScrollPane(my_area);
        goss.add(scroll,GridC);

        GridC.gridx =1;
        GridC.gridy=2;
        GridC.anchor = GridBagConstraints.LINE_START;

       // my_field.setBorder(BorderFactory.createCompoundBorder(my_border,
         //       BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        goss.add(my_text,GridC);

        GridC.gridx=2;
        GridC.insets = new Insets(-15,-75,10,10);
        goss.add(send,GridC);

        GridC.gridy = 3;

        goss.add(sign_out,GridC);

        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gosspip();
                my_text.setText("");
            }
        });

        sign_out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                leave(unique);
            }
        });

        add(goss);
        pack();
        setVisible(true);
    }

    public void gosspip(){


        if (my_text.getText().length()==0)
            return;

       String message = name+": "+ my_text.getText()+"\n";

        try{

            displayme.writeObject(message);

        }catch (IOException IOException){

        }
    }


    public void sendme(String mee){

        my_area.append(mee);
    }

    public void leave(String message){

        try{

            displayme.writeObject(message);

        }catch (IOException IOException){

        }
    }

    public void close(){

        try {
                displayme.close();
                readme.close();
        }catch (IOException IOException){

        }
    }
}
