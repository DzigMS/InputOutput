package ua.dp.dzms.messagestore.service.impl;

import ua.dp.dzms.messagestore.service.MessageStoreTest;


public class SerializationMessageStoreTest extends MessageStoreTest {

    @Override
    public void getMessageStore() {
        messageStore = new SerializationMessageStore(file);
    }
}