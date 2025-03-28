package com.example.booking.controller;

import com.example.booking.dto.UnitDTO;
import com.example.booking.service.BookingService;
import com.example.booking.service.UnitService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;
    private final BookingService bookingService;

    @Parameters({
            @Parameter(name = "rooms", description = "rooms parameter", in = ParameterIn.QUERY),
            @Parameter(name = "floor", description = "floor parameter", in = ParameterIn.QUERY),
            @Parameter(name = "costFrom", description = "costFrom parameter", in = ParameterIn.QUERY),
            @Parameter(name = "costTo", description = "costTo parameter", in = ParameterIn.QUERY),
            @Parameter(name = "available", description = "description parameter", in = ParameterIn.QUERY),

    })
    @GetMapping
    public ResponseEntity<Page<UnitDTO>> getUnits(@RequestParam Map<String, Object> allParams,
                                                  @PageableDefault(page = 0, size = 10, sort = "cost", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(unitService.findAll(allParams, pageable));
    }


    @GetMapping(value = "/available", produces = "application/json")
    public ResponseEntity<Long> getUnits() {
        return ResponseEntity.ok(bookingService.getAvailableUnitsCount());
    }


    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> createUnit(@RequestBody UnitDTO unitDTO) {
        unitService.createUnit(unitDTO);
        return ResponseEntity.ok().build();
    }
}
