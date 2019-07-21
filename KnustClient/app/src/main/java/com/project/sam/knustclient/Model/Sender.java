package com.project.sam.knustclient.Model;



/**
 * Created by A.Richard on 14/10/2017.
 */

public class Sender {
    public String to;
    public Notification notification;

    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }
}
