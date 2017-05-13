package ua.dp.dzms.filemanager;

import java.io.*;

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
        if (fileFrom.exists() && fileTo.exists() && fileTo.isDirectory()) {
            copyFilesAndDirs(fileFrom, fileTo);
            return true;
        } else return false;
    }


    public static boolean move(String from, String to) {
        File fileFrom = new File(from);
        File fileTo = new File(to);
        if (copy(from, to)) {
            deleteFile(fileFrom);
            return true;
        } else return false;
    }

    private static void validateDir(File file) {
        if (!file.exists()) {
            throw new RuntimeException("File not found");
        } else if (!file.isDirectory()) {
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

    private static void copyFilesAndDirs(File fileFrom, File fileTo) {
        if (fileFrom.isDirectory()) {
            File dirCopy = getCopyFileBy(fileTo.getPath() + "/" + fileFrom.getName());
            dirCopy.mkdir();
            File[] files = fileFrom.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    copyFilesAndDirs(subFile, dirCopy);
                }
            }
        } else {
            writeData(fileFrom, fileTo);
        }
    }

    private static void writeData(File fileFrom, File fileTo) {
        File fileCopy = getCopyFileBy(fileTo.getPath() + "/" + fileFrom.getName());
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileFrom));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileCopy))) {
            int read;
            byte[] b = new byte[4096];
            while ((read = bufferedInputStream.read(b)) > 0) {
                bufferedOutputStream.write(b, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't copy");
        }
    }

    private static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File subFile : files) {
                deleteFile(subFile);
                subFile.delete();
            }
        }
        file.delete();
    }

    private static File getCopyFileBy(String path) {
        File copyFile = new File(path);
        return (!copyFile.exists()) ? copyFile : (new File(path + "-copy"));
    }
}
