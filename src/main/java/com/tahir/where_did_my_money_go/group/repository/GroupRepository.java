package com.tahir.where_did_my_money_go.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tahir.where_did_my_money_go.group.entity.Group;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}