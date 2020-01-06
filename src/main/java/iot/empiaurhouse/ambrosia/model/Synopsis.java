package iot.empiaurhouse.ambrosia.model;

import javax.persistence.*;

@Entity
public class Synopsis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    @Lob
    private String recipeSynopsis;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getRecipeSynopsis() {
        return recipeSynopsis;
    }

    public void setRecipeSynopsis(String recipeSynopsis) {
        this.recipeSynopsis = recipeSynopsis;
    }
}
