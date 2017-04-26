package ua.dp.dzms.filemanager;

import java.io.File;
import java.io.FileFilter;

public class FileManager {

    public static int calculateFiles(String path) {
        File file = new File(path);
        validate(file);
        return countFiles(file);
    }

    public static int calculateDirs(String path){
        File file = new File(path);
        validate(file);
        return countDirs(file);
    }

    private static void validate(File file) {
        if (!file.exists()) {
            throw new RuntimeException("Directory not found");
        }else if (!file.isDirectory()){
            throw new RuntimeException("File is not a directory");
        }
    }

    private static int countFiles(File file){
        int count = 0;
        File[] subdirectories = file.listFiles();
        if (subdirectories != null) {
            for (File subFile : subdirectories) {
                if (subFile.isDirectory()) {
                    count += countFiles(subFile);
                } else {
                    count++;
                }
            }
        }
        return count;
    }

    private static int countDirs(File file){
        int count = 0;
        File[] subdirectories = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File fileFilter) {
                return fileFilter.isDirectory();
            }
        });
        if (subdirectories != null) {
            count = subdirectories.length;
            for (File subFile : subdirectories) {
                    count += countDirs(subFile);
            }
        }
        return count;
    }
}
