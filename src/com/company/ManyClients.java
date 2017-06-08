package com.company;

import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class ManyClients extends Thread {

    private ObjectInputStream my_in;
    private ObjectOutputStream my_out;
    private Socket user_s;
    private TreeMap<String, String> my_users;
    private String [] the_name;
    private ArrayList<ManyClients> user_list;
    private String unique;
    private MessageDigest Alg;
    boolean connect = true;

    public ManyClients(Socket client_s, TreeMap<String, String> my_users, ArrayList<ManyClients> user_list) {

        this.user_s = client_s;
        this.my_users = my_users;
        this.user_list = user_list;
        System.out.println(user_list.size());

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

                String the_n = (String) my_in.readObject(); // reading user info
                the_name = the_n.split(",");

                // the user tried to login
                if (the_name[0].equals("log")){

                    // spilt didnt work // new code
                    if(the_name.length != 3)
                        my_out.writeObject("create now");

                    // there is no username match or wrong password
                    else if (my_users.get(the_name[1]) == null || !my_users.get(the_name[1]).equals(the_name[2])) {
                        my_out.writeObject("create now");
                    }

                    // username and password match
                    else if (my_users.get(the_name[1]).equals(the_name[2])) { // match found
                        my_out.writeObject("Log Found");
                        System.out.println(" found something");
                        run_me = false;

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

                        run_me = false; // stop loop
                        my_users.put(the_name[1],the_name[2]);
                        my_out.writeObject("Success");
                    }

                    else
                        my_out.writeObject("create now");
                }

            }

            }catch (ClassNotFoundException ClassNotFoundException){
                System.out.println("No log");
            }

        }catch (IOException e){
            System.out.println("Run method failed");
        }

        // tell user someone is online new code
        for (int i=0; i < user_list.size(); ++i){
            user_list.get(i).sending(the_name[1]+" is online\n");
        }

        recieve();
    }

    public void recieve(){

        String reading;
        try{

            Alg = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException NoSuchAlgorithm){

        }

        Alg.update((the_name[1]+the_name[2]).getBytes());
        unique = new String(Alg.digest());

        boolean go = true;

        while (go){
            try {

                try {

                    reading = (String) my_in.readObject();

                    // user logging out
                    if(reading.equals(unique)){

                        // tell other users someone is leaving
                        for (int i = 0; i < user_list.size();++i ){


                            if (!user_list.get(i).getname().equals(the_name[1])){
                            reading = the_name[1]+" has logged out\n";
                            user_list.get(i).sending(reading);
                            }

                            else
                                user_list.get(i).sending(unique);

                        }

                        try {

                            my_out.close();
                            my_in.close();
                            user_s.close();

                        }catch (IOException IOException){

                        }

                        connect = false;
                        go = false;
                    }

                    else {

                        for (int i = 0; i < user_list.size(); ++i) {
                            user_list.get(i).sending(reading);
                        }
                    }

                }catch (ClassNotFoundException ClassNotfoundException){}
            }catch (IOException IOException){}
        }
    }


    public void sending(String hello){

        try{
            my_out.writeObject(hello);
        }catch (IOException IOException){

        }

    }

    public String getname(){

        return the_name[1];
    }

    public boolean connection(){

        return connect;
    }

}
