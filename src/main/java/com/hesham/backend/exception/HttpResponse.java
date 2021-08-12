package com.hesham.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
public class HttpResponse {
    private int code; // like 200, 201, 404, 500
    private HttpStatus httpStatus;
    private String reasonPhrase;
    private String customMessage;

    // formatting timestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "America/Detroit")
    private Date timeStamp;

    public HttpResponse(int code, HttpStatus httpStatus, String reasonPhrase, String customMessage) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.reasonPhrase = reasonPhrase;
        this.customMessage = customMessage;
        this.timeStamp = new Date();
    }
}
