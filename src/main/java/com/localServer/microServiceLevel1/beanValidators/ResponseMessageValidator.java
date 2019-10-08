package com.localServer.microServiceLevel1.beanValidators;

import com.localServer.microServiceLevel1.models.ResponseMessage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Primary
public class ResponseMessageValidator implements Validator {
    @Override
    public boolean supports(Class < ? > aClass) {
        return ResponseMessage.class.equals ( aClass );
    }

    @Override
    public void validate(Object obj , Errors errors) {
        ValidationUtils.rejectIfEmpty ( errors , "message" , "message.empty" );
        ResponseMessage p = (ResponseMessage) obj;
        if (p.getMessage ( ).length ( ) < 2) {
            errors.rejectValue ( "message" , "length of message is too small" );
        } else if (p.getMessage ( ).length ( ) > 20) {
            errors.rejectValue ( "message" , "length of message is too large" );
        }
    }
}
