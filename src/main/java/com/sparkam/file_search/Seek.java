package com.sparkam.file_search;

import java.io.*;
import java.nio.file.*;


public class Seek {

    public static void main(String[] args) throws IOException {


        if (args.length != 2) {
            usage();
        }

        Path startingDir = Paths.get(args[1]);
        String pattern = args[0];

        Seeker seeker = new Seeker(pattern);
        System.out.println(
                "+-----------------------------------------+\n" +
                "|            Search directories           |\n" +
                "+-----------------------------------------+");
        Files.walkFileTree(startingDir, seeker);
        seeker.done();
    }

    private static void usage() {
        System.err.println("java -jar file_search.jar <file/pattern> <path>");
        System.exit(-1);
    }
}