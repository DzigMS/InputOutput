package ua.dp.dzms.messagestore.service;

import org.junit.Test;
import ua.dp.dzms.messagestore.entity.Message;

import java.time.LocalDate;

import static org.junit.Assert.*;


public class SerializationMessageStoreTest {
    @Test
    public void readTest() throws Exception {
        MessageStore messageStore = new SerializationMessageStore();
        System.out.println(messageStore.read());
    }

    @Test
    public void persistTest() throws Exception {
        Message message = new Message();
        message.setId(0);
        message.setDate(LocalDate.of(2017, 12, 27));
        message.setContent("First");
        MessageStore messageStore = new SerializationMessageStore();
        messageStore.persist(message);
    }

}