package ua.dp.dzms.filemanager;

import org.junit.Before;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FileManagerTest {

    @Before
    public void before() throws Exception {
        File mockParentDir = Mockito.mock(File.class);

        File mockDir1 = Mockito.mock(File.class);
        File mockDir2 = Mockito.mock(File.class);
        File mockDir3 = Mockito.mock(File.class);

        Mockito.when(mockDir1.exists()).thenReturn(true);
        Mockito.when(mockDir2.exists()).thenReturn(true);
        Mockito.when(mockDir3.exists()).thenReturn(true);

        File mockFile1 = Mockito.mock(File.class);
        File mockFile2 = Mockito.mock(File.class);
        File mockFile3 = Mockito.mock(File.class);
        File mockFile4 = Mockito.mock(File.class);

        File[] listFileParentDir = {mockDir1, mockFile1, mockDir2, mockFile2};

        File[] listFileDir1 = {mockDir3, mockFile3};

        File[] listFileDir3 = {mockFile4};

        Mockito.when(mockParentDir.listFiles()).thenReturn(listFileParentDir);
        Mockito.when(mockDir1.listFiles()).thenReturn(listFileDir1);

        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Test").thenReturn(mockParentDir);
        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Dir1").thenReturn(mockDir1);
        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Dir2").thenReturn(mockDir2);
        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments("Dir3").thenReturn(mockDir3);
    }

    @org.junit.Test
    public void calculateFilesTest() throws Exception {
    }

    @org.junit.Test
    public void calculateDirsTest() throws Exception {

    }

}