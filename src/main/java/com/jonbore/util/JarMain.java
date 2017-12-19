package com.jonbore.util;

public class JarMain {
    public static void main(String[] args){
        System.out.println(System.getProperty("user.home"));
        System.getProperties().forEach((k,v) ->{
            System.out.println(k+": "+v);
        });
        System.out.println("======================================");
        System.getenv().forEach((k,v) ->{
            System.out.println(k+": "+v);
        });
    }
}
