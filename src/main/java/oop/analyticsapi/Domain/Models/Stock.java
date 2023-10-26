package oop.analyticsapi.Domain.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String symbol;
    private Integer quantity;
    private LocalDate dateAdded;
    private Double price;
}
