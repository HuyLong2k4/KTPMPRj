package main.java.sample.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class HouseholdFees {
    private int id;
    private int householdId;
    private int feesId;
    private BigDecimal amount;
    private LocalDate month;
}
