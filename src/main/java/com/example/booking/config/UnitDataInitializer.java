package com.example.booking.config;

import com.example.booking.model.AccommodationType;
import com.example.booking.model.Unit;
import com.example.booking.model.User;
import com.example.booking.repository.UnitRepository;
import com.example.booking.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class UnitDataInitializer {

    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    @PostConstruct
    public void generateRandomUnits() {
        if (unitRepository.count() >= 100) {
            return;
        }

        List<Unit> units = new ArrayList<>();
        List<User> authors = userRepository.findAll();

        if (authors.isEmpty()) {
            return;
        }

        for (int i = 0; i < 90; i++) {
            ThreadLocalRandom random = ThreadLocalRandom.current();

            Unit unit = Unit.builder()
                    .rooms(random.nextInt(1, 6)) // 1-5 rooms
                    .type(AccommodationType.values()[random.nextInt(AccommodationType.values().length)])
                    .floor(random.nextInt(1, 11)) // 1-10 floor
                    .available(random.nextBoolean())
                    .cost(BigDecimal.valueOf(random.nextInt(500, 5001))) // price from 500 to 5000
                    .markup(BigDecimal.valueOf(random.nextInt(50, 501))) // markup from 50 to 500
                    .description("Generated unit #" + (i + 1))
                    .author(authors.get(random.nextInt(authors.size())))
                    .build();

            units.add(unit);
        }

        unitRepository.saveAll(units);
    }
}
