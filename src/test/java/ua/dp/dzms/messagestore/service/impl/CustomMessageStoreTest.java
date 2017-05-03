package ua.dp.dzms.messagestore.service.impl;

import org.junit.Assert;
import org.junit.Test;
import ua.dp.dzms.messagestore.entity.Message;
import ua.dp.dzms.messagestore.service.MessageStore;
import ua.dp.dzms.messagestore.service.MessageStoreTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomMessageStoreTest extends MessageStoreTest{

    @Override
    public void init() {
        messageStore = new CustomMessageStore();
    }
}