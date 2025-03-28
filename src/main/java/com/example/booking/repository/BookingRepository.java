package com.example.booking.repository;

import com.example.booking.model.Booking;
import com.example.booking.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    List<Booking> findByUnitAndEndDateAfter(Unit unit, LocalDateTime now);
}