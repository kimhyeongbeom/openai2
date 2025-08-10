package panzer.leopard2.openai2.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImageRequestDTO {
    private String message;
    private String model;
    private int n;
}
