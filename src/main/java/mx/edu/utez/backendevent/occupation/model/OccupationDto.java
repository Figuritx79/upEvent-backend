package mx.edu.utez.backendevent.occupation.model;

import org.springframework.security.access.prepost.PostFilter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OccupationDto {

    @PositiveOrZero(groups = { Update.class, Delete.class,
            Read.class }, message = "To update, delete or read the id has been positive")
    @NotNull(groups = { Update.class, Delete.class, Read.class }, message = "To updatem delete, read you need the id")
    private long id;

    @NotNull(groups = { Update.class, Insert.class }, message = "You need the name of the occupation")
    @NotBlank(groups = { Update.class, Insert.class }, message = "The name doesnt have content")
    @NotEmpty(groups = { Update.class, Insert.class }, message = "The name is empty")
    private String name;

    public OccupationDto(@NotNull(groups = { Update.class,
            Insert.class }, message = "You need the name of the occupation") @NotBlank(groups = { Update.class,
                    Insert.class }, message = "The name doesnt have content") @NotEmpty(groups = { Update.class,
                            Insert.class }, message = "The name is empty") String name) {
        this.name = name;
    }

    public interface Insert {

    }

    public interface Update {

    }

    public interface Delete {

    }

    public interface Read {

    }
}
