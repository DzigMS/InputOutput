package ua.dp.dzms.filemanager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest(FileManager.class)
public class FileManagerTest {

    public void before() throws Exception {
        File mockParentDir = mock(File.class);
        when(mockParentDir.exists()).thenReturn(true);
        when(mockParentDir.isDirectory()).thenReturn(true);

        File mockDir1 = mock(File.class);
        File mockDir2 = mock(File.class);
        File mockDir3 = mock(File.class);

        when(mockDir1.exists()).thenReturn(true);
        when(mockDir2.exists()).thenReturn(true);
        when(mockDir3.exists()).thenReturn(true);

        when(mockDir1.isDirectory()).thenReturn(true);
        when(mockDir2.isDirectory()).thenReturn(true);
        when(mockDir3.isDirectory()).thenReturn(true);

        File mockFile1 = mock(File.class);
        File mockFile2 = mock(File.class);
        File mockFile3 = mock(File.class);
        File mockFile4 = mock(File.class);

        when(mockFile1.exists()).thenReturn(true);
        when(mockFile2.exists()).thenReturn(true);
        when(mockFile3.exists()).thenReturn(true);
        when(mockFile4.exists()).thenReturn(true);

        when(mockFile1.isDirectory()).thenReturn(false);
        when(mockFile2.isDirectory()).thenReturn(false);
        when(mockFile3.isDirectory()).thenReturn(false);
        when(mockFile4.isDirectory()).thenReturn(false);

        File[] listFileParentDir = {mockDir1, mockFile1, mockDir2, mockFile2};

        File[] listFileDir1 = {mockDir3, mockFile3};

        File[] listFileDir3 = {mockFile4};

        when(mockParentDir.listFiles()).thenReturn(listFileParentDir);
        when(mockParentDir.listFiles((FileFilter) Matchers.any())).thenReturn(new File[]{mockDir1, mockDir2});
        when(mockDir1.listFiles()).thenReturn(listFileDir1);
        when(mockDir1.listFiles((FileFilter) Matchers.any())).thenReturn(new File[]{mockDir3});
        when(mockDir3.listFiles()).thenReturn(listFileDir3);
        when(mockDir3.listFiles((FileFilter) Matchers.any())).thenReturn(new File[]{});

        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Test").thenReturn(mockParentDir);
        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Dir1").thenReturn(mockDir1);
        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Dir2").thenReturn(mockDir2);
        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Dir3").thenReturn(mockDir3);

        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("File1").thenReturn(mockFile1);
    }

    @Test
    public void calculateFilesTest() throws Exception {
        before();
        assertEquals(4, FileManager.calculateFiles("Test"));
        assertEquals(2, FileManager.calculateFiles("Dir1"));
        assertEquals(0, FileManager.calculateFiles("Dir2"));
        assertEquals(1, FileManager.calculateFiles("Dir3"));
    }

    @Test(expected = RuntimeException.class)
    public void calculateFilesTestException() throws Exception {
        before();
        FileManager.calculateFiles("File1");
    }

    @Test
    public void calculateDirsTest() throws Exception {
        before();
        assertEquals(3, FileManager.calculateDirs("Test"));
        assertEquals(1, FileManager.calculateDirs("Dir1"));
        assertEquals(0, FileManager.calculateDirs("Dir2"));
        assertEquals(0, FileManager.calculateDirs("Dir3"));
    }

    @Test(expected = RuntimeException.class)
    public void calculateDirsTestException() throws Exception {
        before();
        FileManager.calculateDirs("File1");
    }

    @Test
    public void copyTest() throws IOException {
        String copyFrom = "src/main/resources/FileManagerTest/Copy/CopyFrom/ParentDir";
        String copyTo = "src/main/resources/FileManagerTest/Copy/CopyTo/";

        assertTrue(FileManager.copy(copyFrom, copyTo));
        assertTrue((new File(copyTo + "/ParentDir/File1")).exists());
        assertFalse(FileManager.copy(copyFrom + "/Dir3", copyTo));
    }

    @Test
    public void moveTest() throws IOException {
        String copyFrom = "src/main/resources/FileManagerTest/Copy/CopyFrom/ParentDir";
        String copyTo = "src/main/resources/FileManagerTest/Move/From/";
        String moveFrom = "src/main/resources/FileManagerTest/Move/From/ParentDir";
        String moveTo = "src/main/resources/FileManagerTest/Move/To";
//        FileManager.copy(copyFrom, copyTo);

        assertTrue(FileManager.move(moveFrom, moveTo));
        assertTrue((new File(moveTo + "/ParentDir/File1")).exists());
        assertFalse(new File(moveFrom).exists());
        assertFalse(FileManager.copy(moveFrom + "/Dir3", moveTo));
    }
}