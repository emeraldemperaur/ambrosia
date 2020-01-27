package iot.empiaurhouse.ambrosia.commandobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SynopsisCommand {
    private Long id;
    private RecipeCommand recipe;
    private String recipeSynopsis;
}
