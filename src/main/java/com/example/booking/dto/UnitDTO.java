package com.example.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitDTO {
    private Long id;
    private int rooms;
    private String type;
    private int floor;
    private boolean available;
    private BigDecimal cost;
    private String description;
    private Long authorId;
}
