package ua.dp.dzms.messagestore.service.impl;

import ua.dp.dzms.messagestore.entity.Message;
import ua.dp.dzms.messagestore.service.MessageStore;

import javax.activation.FileDataSource;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomMessageStore implements MessageStore {
    private FileDataSource fileDataSource;

    public CustomMessageStore(FileDataSource fileDataSource) {
        this.fileDataSource = fileDataSource;
    }

    @Override
    public void persist(Message message) {
        persist(Arrays.asList(new Message[]{message}));
    }

    @Override
    public void persist(Collection<Message> list) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(fileDataSource.getFile(), true)
        ) {
            DataOutputStream dataOutputStream = null;
            for (Message message : list) {
                dataOutputStream = new DataOutputStream(fileOutputStream);
                dataOutputStream.writeInt(message.getId());
                dataOutputStream.writeUTF(message.getDate().toString());
                dataOutputStream.writeUTF(message.getContent());
                dataOutputStream.flush();
            }
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Problem with dos");
        }

    }

    @Override
    public List<Message> read() {
        List<Message> messageList = new ArrayList<>();
        try (
                FileInputStream fileInputStream = (FileInputStream) fileDataSource.getInputStream();

        ) {
            DataInputStream dataInputStream = null;
            Message message;
            while (fileInputStream.available() != 0) {
                dataInputStream = new DataInputStream(fileInputStream);
                message = new Message();
                int id = dataInputStream.readInt();
                LocalDate date = LocalDate.parse(dataInputStream.readUTF());
                String content = dataInputStream.readUTF();
                message.setId(id);
                message.setDate(date);
                message.setContent(content);
                messageList.add(message);
            }
            if (dataInputStream != null) {
                dataInputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("can't read");
        }

        return messageList;
    }
}
