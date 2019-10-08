package com.localServer.microServiceLevel1.Repository;

import com.localServer.microServiceLevel1.models.ResponseMessage;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
public interface ResponseMessageContract  {

     ResponseMessage getMessage(String mid);

     List < ResponseMessage > getAllMessages();

     ResponseMessage saveMessage(ResponseMessage message);

     List < ResponseMessage > deleteMessage(String mid);
}
