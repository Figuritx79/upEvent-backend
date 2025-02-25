package mx.edu.utez.backendevent.role.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoleDto {

    @PositiveOrZero(groups = { Read.class, Update.class,
            Delete.class }, message = "Required id for update, delete and read")
    private long id;

    @NotBlank(groups = { Update.class, Insert.class,
    }, message = "Required name for update and insert ")
    @NotEmpty(groups = { Update.class, Insert.class })
    private String name;

    public interface Insert {

    }

    public interface Update {

    }

    public interface Delete {

    }

    public interface Read {

    }

}