package ua.dp.dzms.messagestore.service.impl;

import ua.dp.dzms.messagestore.entity.Message;
import ua.dp.dzms.messagestore.service.MessageStore;

import javax.activation.FileDataSource;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SerializationMessageStore implements MessageStore {
    private FileDataSource fileDataSource;

    public SerializationMessageStore(FileDataSource fileDataSource) {
        this.fileDataSource = fileDataSource;
    }

    @Override
    public void persist(Message message) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileDataSource.getFile(), true);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Problem with fos");
        }
    }

    @Override
    public void persist(Collection<Message> list) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileDataSource.getFile(), true);
        ) {
            ObjectOutputStream objectOutputStream = null;
            for (Message message : list) {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
            }
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem with oos");
        }
    }

    @Override
    public List<Message> read() {
        List<Message> messageList = new ArrayList<>();
        try (
                FileInputStream fileInputStream = (FileInputStream) fileDataSource.getInputStream();
        ) {
            ObjectInputStream objectInputStream = null;
            Message message;
            while (fileInputStream.available() != 0) {
                objectInputStream = new ObjectInputStream(fileInputStream);
                message = (Message) objectInputStream.readObject();
                messageList.add(message);
            }
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("can't read");
        }

        return messageList;
    }
}
