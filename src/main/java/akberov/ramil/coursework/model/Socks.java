package akberov.ramil.coursework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Data
@NoArgsConstructor
@EqualsAndHashCode

public class Socks {
    @NotEmpty(message = "Обязательно заполните данное поле")
    private Colors colors;

    @NotEmpty(message = "Обязательно заполните данное поле")
    private Size size;

    @NotEmpty
    @Min(value = 0, message = "Минимальное процентное содержание хлопка в носках должно быть не меньше 0%")
    @Max(value = 100, message = "Максимальное процентное содержание хлопка в носках должно быть не больше 100%")
    private Integer cottonPart;

    @Positive
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return colors == socks.colors && size == socks.size && Objects.equals(cottonPart, socks.cottonPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colors, size, cottonPart);
    }

    @Override
    public String toString() {
        return colors +
                " " + size +
                " " + cottonPart +
                " " + quantity;
    }

}
