package com.yousra.store.repository;

import com.yousra.store.model.Command;
import com.yousra.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommandRepository extends JpaRepository<Command, UUID> {

    List<Command> findByUserOrderByIdDesc(User user);
}
