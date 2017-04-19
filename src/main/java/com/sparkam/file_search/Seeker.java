package com.sparkam.file_search;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Created by Alexander on 19.04.2017.
 */
public class Seeker extends SimpleFileVisitor<Path> {

    private final PathMatcher matcher;
    private int numMatches = 0;
    private List<Path> foundFiles = new ArrayList<Path>();

    public Seeker(String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException e) {
        System.err.println("I/O error. Can't access file " + file);
        return CONTINUE;
    }

    private void find(Path file) {
        Path name = file.getFileName();

        if (file.toFile().isDirectory()) {
            System.out.println(file);
        }

        if (name != null && matcher.matches(name)) {
            numMatches++;
            foundFiles.add(file);
        }
    }

    public void done() {
        System.out.println(
                        "+-----------------------------------------+\n" +
                        "|               Found files               |\n" +
                        "+-----------------------------------------+");

        for (Path file : foundFiles) {
            System.out.println(file);
        }

        System.out.println("Found " + numMatches + " files");
    }
}
