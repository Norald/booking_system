package com.example.booking.model;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.StaticMetamodel;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "units")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@StaticMetamodel(Unit.class)
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rooms;

    @Enumerated(EnumType.STRING)
    private AccommodationType type;
    
    private int floor;
    private boolean available;
    private BigDecimal markup;
    private BigDecimal cost;
    private String description;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}

