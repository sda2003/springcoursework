package com.ijse.springcoursework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemReqDTO {
    private String name;
    private Double price;
    private String description;
    private Long itemCategoryId;
}
