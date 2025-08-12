package panzer.leopard2.openai2.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RecipeDTO {
    private String ingredients;
    private String cuisine;
    private String dietaryRestrictions;
}
