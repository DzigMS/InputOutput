package ua.dp.dzms.filemanager;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {

    public static int calculateFiles(String path) {
        File file = new File(path);
        validateDir(file);
        return countFiles(file);
    }

    public static int calculateDirs(String path) {
        File file = new File(path);
        validateDir(file);
        return countDirs(file);
    }

    public static boolean copy(String from, String to) {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        if (fileFrom.exists() || fileTo.exists() || fileTo.isDirectory()) {
            try {
                Files.copy(fileFrom.toPath(), fileTo.toPath());
                return true;
            } catch (IOException e) {
                throw new RuntimeException("Can't copy");
            }
        } else return false;
    }

    public static boolean move(String from, String to) {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        if (fileFrom.exists() || fileTo.exists() || fileTo.isDirectory()) {
            try {
                Files.move(fileFrom.toPath(), fileTo.toPath());
                return true;
            } catch (IOException e) {
                throw new RuntimeException("Can't move");
            }
        } else return false;
    }

    private static void validateDir(File file) {
        if (!file.exists()){
            throw new RuntimeException("File not found");
        }else if (!file.isDirectory()) {
            throw new RuntimeException("File is not a directory");
        }
    }

    private static int countFiles(File file) {
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

    private static int countDirs(File file) {
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
