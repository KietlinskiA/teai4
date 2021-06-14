package pl.kietlinski.teai4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private long id;
    @NotNull
    @Size(min = 2)
    private String mark;
    @NotNull
    @Size(min = 1)
    private String model;
    @NotNull
    @Size(min = 2)
    private String color;
    @NotNull
    @Min(1885)
    @Max(2021)
    private int year;
}
