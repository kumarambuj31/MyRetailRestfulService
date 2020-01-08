package com.target.casestudy.myretailrestfulservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown=true)
@Setter
@Getter
@Entity
@Table(name = "ProductPrice")
public class ProductPrice  implements Serializable {
    private static final long serialVersionUID = -8331537878236316444L;
    @Id
    int id;
    BigDecimal price;
    String currencyCode;
}
