package mx.edu.utez.backendevent.gender.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenderDto {
   
    @NotNull
    @PositiveOrZero
    private Long id;

    private String name;
    

    public interface Insert {
    
        
    }

    public interface Update {
    
        
    }

    public interface Read {
    
        
    }

    public interface Delete {
    
        
    }
}
