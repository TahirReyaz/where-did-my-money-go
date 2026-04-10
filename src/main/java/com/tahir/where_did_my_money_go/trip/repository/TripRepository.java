package com.tahir.where_did_my_money_go.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tahir.where_did_my_money_go.trip.entity.Trip;

import java.util.List;
import java.util.UUID;

public interface TripRepository extends JpaRepository<Trip, UUID> {

    List<Trip> findByUserId(UUID userId);
}