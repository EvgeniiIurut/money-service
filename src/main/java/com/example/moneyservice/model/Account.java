package com.example.moneyservice.model;

import com.example.moneyservice.exception.NegativeAmountException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal balance;
    @Version
    private Integer version;

    public void reduceBalance(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new NegativeAmountException(id);
        }
        balance = balance.add(amount.negate());
    }

    public void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

}
