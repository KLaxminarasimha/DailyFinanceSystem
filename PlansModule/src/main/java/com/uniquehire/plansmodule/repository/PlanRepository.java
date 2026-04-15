package com.uniquehire.plansmodule.repository;


import com.uniquehire.plansmodule.entity.Plan;
import com.uniquehire.plansmodule.enums.PlanStatus;
import com.uniquehire.plansmodule.enums.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByStatus(PlanStatus status);

    boolean existsByName(PlanType name);
}
