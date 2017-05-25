import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class ClientGUI extends JFrame{

    // creates username textfield
    private JLabel username = new JLabel("Username ");
    private JTextField textUsername = new JTextField(10);

    //creates password textfield
    private JLabel password = new JLabel("Password ");
    private JPasswordField passText = new JPasswordField(10);

    // adding two buttons
    private JButton Login = new JButton("Login");
    private JButton Create = new JButton("Create");

    private ObjectOutputStream display_me;
    private ObjectInputStream read_me;

    private JPanel error;
    private JFrame my_error;
    private JLabel errormsg = new JLabel("Error Try Again");
    private JButton cancel_me = new JButton("OK");
    private String option;
    private String usr_name;
    private char [] ident;
    private String pass;
    private String passme;
    private Socket my_socket;

    // constructor
    ClientGUI(){

        super("GossApp"); //title of jframe

        try{

            my_socket = new Socket(InetAddress.getByName("127.0.0.1"), 444);
            this.display_me = new ObjectOutputStream(my_socket.getOutputStream());
            this.read_me = new ObjectInputStream(my_socket.getInputStream());

            System.out.print("connected!!!");


        }catch (IOException e){
            System.out.println("cant connect");
        }

        CreatGui(); // creates gui

    }


    public void CreatGui(){

        JPanel myPanel = new JPanel(new GridBagLayout()); // creates a panel of type gridbaglayout
        GridBagConstraints GridC = new GridBagConstraints();// constraints for layout
        GridC.insets = new Insets(0,10,0,10); // spacing

        // position 0,0 is corner
        GridC.gridx = 0;
        GridC.gridy = 0;
        myPanel.add(username, GridC); // adding to panel

        // position 0,1 adding user text field below username
        GridC.gridy = 1;
        myPanel.add(textUsername, GridC);

        // position 0, 2 adding password name below user
        GridC.gridx = 0;
        GridC.gridy = 2;
        myPanel.add(password, GridC);

        //position 0,3 adding password text field below password
        GridC.gridy = 3;
        myPanel.add(passText, GridC);

        // adding login button to panel next to password text
        GridC.gridx = 1;
        GridC.gridy = 3;
        myPanel.add(Login, GridC);

        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*
                String my_name = textUsername.getText();
                char [] my_password = passText.getPassword();
                String my_stringword = new String(my_password);
                */
                option ="log";
                System.out.println("log pressed");
                whileLog(option);
            }
        });

        // adding create button gui next to user text field
        GridC.gridx = 1;
        GridC.gridy = 1;
        myPanel.add(Create,GridC);

        Create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                option= "create";
                whileLog(option);
            }
        });

        add(myPanel); //adds gui to jframe
        setSize(270,170); // creates dimensions of frame
        setLocationRelativeTo(null); // center gui
        setVisible(true); // I can see!

        my_error = new JFrame("Error");
        error = new JPanel(new GridBagLayout());
        GridC.gridx= 1;
        GridC.gridy = 1;

        error.add(cancel_me,GridC);
        cancel_me.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                my_error.setVisible(false);
            }
        });

        GridC.gridy = 2;
        error.add(errormsg,GridC);
        my_error.add(error);
        my_error.pack();
        my_error.setLocationRelativeTo(null);
        my_error.setVisible(false);

    }

    public void whileLog(String info) {

        char[] my_password;
        my_password = passText.getPassword();
        usr_name = textUsername.getText();
        pass = new String(my_password);
        passme = info+","+usr_name+","+pass;

        System.out.println(passme);

        try {

            display_me.writeObject(passme);

                try {

                    String me = (String) read_me.readObject();


                    if (me.equals("create now")) {

                        my_error.setVisible(true);
                    }

                    else {

                        setVisible(false);
                        GUI talking = new GUI(read_me,display_me,usr_name);
                        talking.create();


                        Thread m = new Thread(){
                          public void run(){
                              String me;

                              while (true){
                                  try
                                  {
                                      try {
                                          me = (String) read_me.readObject();
                                          System.out.println(me);
                                          talking.sendme(me);
                                      }catch (ClassNotFoundException ClassNotfoundException){}
                                  }catch (IOException IOException){

                                  }

                              }
                          }

                        }; m.start();
                    }

                } catch (ClassNotFoundException ClassNotFoundException) {

                }

        }catch (IOException IOException){

            }

    }
}
