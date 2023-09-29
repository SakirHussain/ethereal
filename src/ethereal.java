package com.craftinginterpreters.ethereal;

import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Ethereal {
    static boolean hadError = false;
    public static void main(String args[]) throws IOException{
        if(args.length > 1){
            System.out.println("Usage : r3al [script]");
            System.exit(64);
        }
        else if (args.length == 1) {
            runFile(args[0]);
        }
        else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte bytes[] = Files.readAllBytes(Paths.get(path));
        if(hadError) { System.exit(65);}
        run(new String(bytes, Charset.defaultCharset()));


        
    }

    private static void runPrompt() throws IOException{
        Scanner sc =  new Scanner(System.in);

        for(;;){
            System.out.println("> ");
            String line = sc.nextLine();
            if(line == null){
                break;
            }
            run(line);
            hadError = false;
        }
    }

    private static void run(String source){
        Scanner sc = new Scanner(source);
        List<Token> tokens = sc.scanTokens();

        for(Token token: tokens){
            System.out.println(token);
        }
    }

    static void error(int line, String message){
        report(line, " ", message);
    }

    private static void report(int line, String where, String message){
        System.err.println("[line "+line+"] Error"+where+": "+message);
        hadError = true;
    }
}