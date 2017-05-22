package com.company;
import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class A_server {

    //private ObjectInputStream my_in;
    //private ObjectOutputStream my_out;
    private Socket a_socket;
    private ServerSocket server_a;
    private ManyClients someclient;
    private TreeMap<String,String> my_users;

    // setting up server
    public void call_server() {

        insert(); // store all users into treemap from file

        try {

            server_a = new ServerSocket(444);   // creates a socket at the portnumber

            // loops waiting for connection
            while (true) {

                try {

                    a_socket = server_a.accept(); // get port connected

                    // client thread
                    someclient = new ManyClients(a_socket,my_users);
                    someclient.start();

                } catch (EOFException eofException) {
                    System.out.print("ended");
                }
            }

        } catch (IOException exception) {
            System.out.print("Exit");
        }
        try{
        a_socket.close();

        }catch (IOException IOException){}

    }
/*
    // checking status of connection and setting up streams to recieve
    public void status() throws IOException {

        System.out.print("someone connected");
        someclient = new ManyClients(a_socket);

        // setting up ports
        my_out = new ObjectOutputStream(a_socket.getOutputStream());
        my_out.flush();
        my_in = new ObjectInputStream(a_socket.getInputStream());

        }

    // sends a message to the client
    public void send_me(){

        try {

            my_out.writeObject("WELCOME");  // welcomes the client to the program

        }catch (IOException ioException){
            System.out.println("failed");
        }

    }
*/

    //reads and maps users from file into tree
    public int insert() {


        // need to do this so i can read from the file
        try {

            // creating a new scanner obj opening the file
            BufferedReader read = new BufferedReader(new FileReader("user_things.txt"));

            String[] user_info;
            String check_me;
            String name;
            String pass;

            my_users = new TreeMap();

            // i have something to read
            while ((check_me = read.readLine()) != null) {

                user_info = check_me.split(",");

                name = user_info[0];
                pass = user_info[1];

                my_users.put(name,pass);
            }
/*
            for (Map.Entry<String, String> entry : my_users.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                System.out.printf("%s : %s\n", key, value);
            }
*/
            read.close(); // close the file
        }

        // file cannot be open
        catch (IOException e) {
            System.out.println("failed no file");
        }

        return 1;
    }
}
