package main.java.sample.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HouseholdPayments {
    private long id;
    private int householdId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String note;
}
