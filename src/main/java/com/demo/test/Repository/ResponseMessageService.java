package com.demo.test.Repository;

import com.demo.test.models.ResponseMessage;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ResponseMessageService implements ResponseMessageContract {

    static public List<ResponseMessage> responseMessageList=new ArrayList();
    static {
        responseMessageList.add(new ResponseMessage("Hello World",UUID.randomUUID().toString()));
        responseMessageList.add(new ResponseMessage("I am new Here",UUID.randomUUID().toString()));
        responseMessageList.add(new ResponseMessage("Nice to meet You",UUID.randomUUID().toString()));
        responseMessageList.add(new ResponseMessage("Lets be friends",UUID.randomUUID().toString()));
    }

    @Override
    public ResponseMessage getMessage(String mid) {
        return  responseMessageList.stream()
                .filter(responseMessage -> responseMessage.getMid().equalsIgnoreCase(mid))
                .findFirst()
                .orElseGet(() -> new ResponseMessage(mid, "Not Message found with given ID"));
    }

    @Override
    public List<ResponseMessage> getAllMessages() {
        return responseMessageList;
    }

    @Override
    public ResponseMessage saveMessage(ResponseMessage message) {
        responseMessageList.add(message);
        return message;
    }

    @Override
    public List<ResponseMessage> deleteMessage(String mid) {
        responseMessageList.removeIf(responseMessage -> responseMessage.getMid().equalsIgnoreCase(mid));
        return getAllMessages();
    }
}
