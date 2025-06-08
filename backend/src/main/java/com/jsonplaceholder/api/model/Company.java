package com.jsonplaceholder.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Company {
    @Column(name = "company_name")
    private String name;
    
    @Column(name = "catch_phrase")
    private String catchPhrase;
    
    @Column(name = "bs")
    private String bs;
} 