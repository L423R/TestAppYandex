package ru.asntl.callsharklibrary.utilities;

import java.io.File;
import java.util.UUID;

public class FileUtility {

    public static String generateFileName(){
        UUID uuid = UUID.randomUUID();
        /*String lastName = "";
        String[] split = file.getName().split("\\.");
        if (split.length>1){
            lastName = "."+split[split.length-1];
        }*/
        return uuid.toString();
    }
}
