package ua.dp.dzms.messagestore.service.impl;

import ua.dp.dzms.messagestore.entity.Message;
import ua.dp.dzms.messagestore.service.MessageStore;

import java.util.Collection;
import java.util.List;

public class CustomMessageStore implements MessageStore {
    @Override
    public void persist(Message message) {

    }

    @Override
    public void persist(Collection<Message> list) {

    }

    @Override
    public List<Message> read() {
        return null;
    }
}
