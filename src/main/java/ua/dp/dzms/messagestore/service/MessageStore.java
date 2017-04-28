package ua.dp.dzms.messagestore.service;

import ua.dp.dzms.messagestore.entity.Message;

import java.util.Collection;
import java.util.List;

public interface MessageStore {
    void persist(Message message);
    void persist(Collection<Message> list);
    List<Message> read();
}
