package lk.ijse.eca.userservice.dto;

import lombok.Data;

@Data
public class ResponseDTO {

    private int code;
    private String message;
    private Object data;

    public ResponseDTO() {
    }

    public ResponseDTO(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}