package ua.dp.dzms.messagestore.service.impl;

import org.junit.Assert;
import org.junit.Test;
import ua.dp.dzms.messagestore.entity.Message;
import ua.dp.dzms.messagestore.service.MessageStore;

import java.time.LocalDate;
import java.util.ArrayList;


public class SerializationMessageStoreTest {

    @Test
    public void readTest() throws Exception {
        MessageStore messageStore = new SerializationMessageStore();
        ArrayList messages = (ArrayList) messageStore.read();
        for (int i = 0; i < messages.size(); i++) {
            Assert.assertEquals(i, ((Message) messages.get(i)).getId());
        }
    }

    @Test
    public void persistTest() throws Exception {
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setId(i);
            message.setDate(LocalDate.of(2017, 12, 27));
            message.setContent("String = " + i);
            MessageStore messageStore = new SerializationMessageStore();
            messageStore.persist(message);
        }
    }

    @Test
    public void persistCollectionTest() throws Exception {
        MessageStore messageStore = new SerializationMessageStore();
        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setId(i);
            message.setDate(LocalDate.of(2017, 05, 03));
            message.setContent("String = " + i);
            messages.add(message);
        }
        messageStore.persist(messages);
    }
}