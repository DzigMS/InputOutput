package ua.dp.dzms.messagestore.service.impl;

import ua.dp.dzms.messagestore.entity.Message;
import ua.dp.dzms.messagestore.service.AppendObjectOutputStream;
import ua.dp.dzms.messagestore.service.MessageStore;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SerializationMessageStore implements MessageStore {
    private File fileData;

    public SerializationMessageStore(File fileData) {
        this.fileData = fileData;
    }

    @Override
    public void persist(Message message) {
        persist(Arrays.asList(new Message[]{message}));
    }

    @Override
    public void persist(Collection<Message> list) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileData, true)) {
            ObjectOutputStream objectOutputStream;
            if (fileData.length() != 0) {
                objectOutputStream = new AppendObjectOutputStream(fileOutputStream);
            } else {
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
            }

            for (Message message : list) {
                objectOutputStream.writeObject(message);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Problem with oos");
        }
    }

    @Override
    public List<Message> read() {
        List<Message> messageList = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(fileData);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Message message;
            while (fileInputStream.available() != 0) {
                message = (Message) objectInputStream.readObject();
                messageList.add(message);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("can't read");
        }

        return messageList;
    }
}
