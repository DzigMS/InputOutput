package ua.dp.dzms.messagestore.service;

import ua.dp.dzms.messagestore.entity.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SerializationMessageStore implements MessageStore {
    @Override
    public void persist(Message message) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File("src/main/resources/History"), true);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Problem with fos");
        }
    }

    @Override
    public void persist(Collection<Message> list) {
    }

    @Override
    public List<Message> read() {
        List<Message> messageList = new ArrayList<>();
        try (
                FileInputStream fileInputStream = new FileInputStream(new File("src/main/resources/History"));

        ) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Message message = (Message) objectInputStream.readObject();
            while (message != null) {
                System.out.println("Yes");
                messageList.add(message);
                System.out.println("2");
                objectInputStream = new ObjectInputStream(fileInputStream);
                message = (Message) objectInputStream.readObject();
            }
        } catch (IOException e) {
//            throw new RuntimeException("can't read");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return messageList;
    }
}
