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
    private String description;
    private String altDescription;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;

}
