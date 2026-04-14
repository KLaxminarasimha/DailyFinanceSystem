package com.uniquehire.loanagentmodule.repository;

import com.uniquehire.loanagentmodule.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent,Long>{

    boolean existsByPhone(String phone);

}
