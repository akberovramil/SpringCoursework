package akberov.ramil.coursework.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode

public class Socks {
    @NotEmpty(message = "Обязательно заполните данное поле")
    private Colors colors;

    @NotEmpty(message = "Обязательно заполните данное поле")
    private Size size;

    @NotEmpty
    @Range(min = 0, max = 100, message = "Процентное содержание хлопка в носках должно быть не менньше 0%  и не больше 100%")
    private Integer cottonPart;

    @Positive(message = "Количество должно быть положительным числом")
    private Long quantity;

    public Socks(Colors colors, Size size, Integer cottonPart, Long quantity) {
        this.colors = colors;
        this.size = size;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    public Socks(Colors colors, Size size, Integer cottonPart) {
        this.colors = colors;
        this.size = size;
        this.cottonPart = cottonPart;
    }

}
