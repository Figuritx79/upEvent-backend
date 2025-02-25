package mx.edu.utez.backendevent.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    public String message;
    public Object result;
    public TypeResponse type;

    public ResponseObject(String message, TypeResponse type) {
        this.message = message;
        this.type = type;
    }

    public ResponseObject(TypeResponse type, String message) {
        this.message = message;
        this.type = type;
    }

}