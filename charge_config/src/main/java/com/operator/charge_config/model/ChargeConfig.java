package com.operator.charge_config.model;

import com.operator.charge_config.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChargeConfig extends BaseEntity {

    @Column(name = "operator", nullable = false)
    private String operator;

    @Column(name = "charge_code", nullable = false)
    private String chargeCode;

}
