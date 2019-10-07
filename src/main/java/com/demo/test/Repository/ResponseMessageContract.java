package com.demo.test.Repository;

import com.demo.test.models.ResponseMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResponseMessageContract  {

    public ResponseMessage getMessage(String mid);
    public List<ResponseMessage> getAllMessages();
    public ResponseMessage saveMessage(ResponseMessage message);
    public List<ResponseMessage> deleteMessage(String mid);
}
