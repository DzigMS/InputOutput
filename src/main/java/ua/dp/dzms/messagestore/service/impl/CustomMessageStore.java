package ua.dp.dzms.messagestore.service.impl;

import ua.dp.dzms.messagestore.entity.Message;
import ua.dp.dzms.messagestore.service.MessageStore;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomMessageStore implements MessageStore {
    private File file;

    public CustomMessageStore(File file) {
        this.file = file;
    }

    @Override
    public void persist(Message message) {
        persist(Arrays.asList(new Message[]{message}));
    }

    @Override
    public void persist(Collection<Message> list) {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file, true))) {
            for (Message message : list) {
                dataOutputStream.writeInt(message.getId());
                dataOutputStream.writeUTF(message.getDate().toString());
                dataOutputStream.writeUTF(message.getContent());
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem with dos");
        }

    }

    @Override
    public List<Message> read() {
        List<Message> messageList = new ArrayList<>();
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            Message message;
            while (dataInputStream.available() > 0) {
                message = new Message();
                int id = dataInputStream.readInt();
                LocalDate date = LocalDate.parse(dataInputStream.readUTF());
                String content = dataInputStream.readUTF();
                message.setId(id);
                message.setDate(date);
                message.setContent(content);
                messageList.add(message);
            }
        } catch (IOException e) {
            throw new RuntimeException("can't read");
        }

        return messageList;
    }
}
