package iot.empiaurhouse.ambrosia.commandobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CuisineCommand {
    private Long id;
    private String cuisineDescription;
}
