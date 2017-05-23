package com.company;

import java.io.*;
import java.net.*;
import java.util.*;


public class ManyClients extends Thread {

    private ObjectInputStream my_in;
    private ObjectOutputStream my_out;
    private Socket user_s;
    private TreeMap<String, String> my_users;

    public ManyClients(Socket client_s, TreeMap<String, String> my_users) {

        this.user_s = client_s;
        this.my_users = my_users;

        System.out.print("someone connected");

        try {


            // setting up ports
            this.my_out = new ObjectOutputStream(user_s.getOutputStream());
            this.my_out.flush();
            this.my_in = new ObjectInputStream(user_s.getInputStream());


        } catch (IOException IOException) {
            System.out.println("No one logged in");
        }
    }

    public void run(){

        boolean run_me = true;

        try {

            try {


            while(run_me) {  // loop until user enters correct info

                String[] the_name = (String[]) my_in.readObject(); // reading user info
                System.out.println(the_name[0]);
                run_me = false;

                // the user tried to login
                if (the_name[0].equals("log")){

                    // there is no username match or wrong password
                    if (my_users.get(the_name[1]) == null || !my_users.get(the_name[1]).equals(the_name[2])) {
                        my_out.writeObject("mo match case 1");
                    }

                    // username and password match
                    else if (my_users.get(the_name[1]).equals(the_name[2])) { // match found
                        my_out.writeObject("Log Found");

                        System.out.println("match found");

                    }

                }

                // user is creating an account
                else if(the_name[0].equals("create")){

                    // check if username exist and if password is null
                    if (my_users.get(the_name[1]) == null && the_name[2] !=null){

                        String info = "\n"+the_name[1] + "," + the_name[2];

                        BufferedWriter out = new BufferedWriter(new FileWriter("user_things.txt",true));
                        out.write(info);
                        out.close();

                        run_me = false;

                        my_out.writeObject("sucess");
                        my_users.put(the_name[1],the_name[2]);
                    }
                }

            }

            }catch (ClassNotFoundException ClassNotFoundException){
                System.out.println("No log");
            }

        }catch (IOException e){
            System.out.println("Run method failed");
        }

    }
}
