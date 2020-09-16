package com.malyshev2202.store.backend.shell;


import com.malyshev2202.store.backend.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent

public class ShellImpl {
    @Autowired
    HelloClient client;
    @ShellMethod("greeting you friend. Use --fname, --lname as keys ")
    public String hello( String fname, String lname){
        return client.sayHello(fname,lname);

    }
}

