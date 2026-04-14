package com.uniquehire.loanagentmodule.service;

import com.uniquehire.loanagentmodule.dto.Request.AgentRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.AgentResponseDTO;

import java.util.List;

public interface AgentService {

    AgentResponseDTO createAgent(AgentRequestDTO request);

    List<AgentResponseDTO> getAllAgents();

}
