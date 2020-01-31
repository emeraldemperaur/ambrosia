package iot.empiaurhouse.ambrosia.commandobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeID;
    private String description;
    private String altDescription;
    private String thumbUrl;
    private BigDecimal amount;
    private BigDecimal avgUnitPrice;
    private UnitOfMeasureCommand unitOfMeasure;

}
