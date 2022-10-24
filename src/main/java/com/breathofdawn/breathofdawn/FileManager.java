package com.breathofdawn.breathofdawn;

import org.bukkit.Bukkit;

import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

public class FileManager {

    public static void CreateDir(String path){
        // Create file objects
        File dir = new File(path);
        // Check if plugin directory exists
        if(!dir.exists()){
            dir.mkdir();
        }
    }

    public static void CreateFile(String path) {
        try {

            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
                Bukkit.getLogger().info("File created: " + file.getName());
            }else{
                Bukkit.getLogger().info("File" + file.getName() + "already exists.");
            }
        } catch (IOException e) {
            Bukkit.getLogger().info("An error occurred.");
            e.printStackTrace();
        }
    }
}