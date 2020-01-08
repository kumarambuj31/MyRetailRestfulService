package com.target.casestudy.myretailrestfulservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Setter
@Getter
@AllArgsConstructor
@ToString
public class ProductDetails {
    private int id;
    private String name;
    private ProductPrice productPrice;
}
