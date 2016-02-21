package com.thoughtworks.utility;

import java.io.*;

public  class OutputWriter {

    public  void writeFile() throws IOException {
        File file = new File("trackmanagement-output.txt");
        // creates the file
        file.createNewFile();
        // creates a FileWriter Object
        FileWriter writer = new FileWriter(file);
        // Writes the content to the file
        writer.write("This\n is\n an\n example\n");
        writer.flush();
        writer.close();
    }
}
