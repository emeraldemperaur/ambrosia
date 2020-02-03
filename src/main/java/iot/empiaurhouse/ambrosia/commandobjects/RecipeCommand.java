package iot.empiaurhouse.ambrosia.commandobjects;

import iot.empiaurhouse.ambrosia.model.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Integer preference;
    private String originator;
    private Byte[] image;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private SynopsisCommand synopsis;
    private Set<CuisineCommand> cuisines = new HashSet<>();
    private Set<CategoryCommand> categories = new HashSet<>();

}
