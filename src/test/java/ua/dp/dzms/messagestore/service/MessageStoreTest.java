package ua.dp.dzms.messagestore.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.dp.dzms.messagestore.entity.Message;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class MessageStoreTest {
    public MessageStore messageStore;
    public File file = new File("src/main/resources/MessageStore/History");


    @Before
    public void before() {
        file.delete();
        file = new File("src/main/resources/MessageStore/History");
        getMessageStore();
    }

    public void persist(){
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setId(i);
            message.setDate(LocalDate.of(2017, 5, 3));
            message.setContent("String = " + i);
            messageStore.persist(message);
        }
    }

    public void persistCollection(){
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 5; i < 9; i++) {
            Message message = new Message();
            message.setId(i);
            message.setDate(LocalDate.of(2017, 5, 3));
            message.setContent("String = " + i);
            messages.add(message);
        }
        messageStore.persist(messages);
    }

    @Test
    public void readTest(){
        persist();
        persistCollection();
        ArrayList messages = (ArrayList) messageStore.read();
        for (int i = 0; i < messages.size(); i++) {
            Assert.assertEquals(i, ((Message) messages.get(i)).getId());
            Assert.assertEquals(LocalDate.of(2017, 5, 3), ((Message) messages.get(i)).getDate());
        }
    }

    public abstract void getMessageStore();
}