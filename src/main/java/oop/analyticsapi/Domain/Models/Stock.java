package oop.analyticsapi.Domain.Models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
    private String symbol;
    private Integer quantity;
}
