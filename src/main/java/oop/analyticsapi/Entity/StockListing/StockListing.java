package oop.analyticsapi.Entity.StockListing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(StockListingId.class)
@Table(name="available_stocks")
public class StockListing {
    @Id
    private String symbol;
    private String name;
    @Column(name = "last_indexed_date")
    private LocalDate lastIndexedDate;

}
