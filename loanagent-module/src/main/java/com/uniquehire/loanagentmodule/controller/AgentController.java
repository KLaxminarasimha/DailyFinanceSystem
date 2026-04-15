package com.uniquehire.loanagentmodule.controller;

import com.uniquehire.loanagentmodule.dto.Request.AgentRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.AgentResponseDTO;
import com.uniquehire.loanagentmodule.service.AgentService;
import com.uniquehire.loanagentmodule.utils.ApiResponse;

import com.uniquehire.loanagentmodule.utils.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    // Create Agent
    @PostMapping
    public ResponseEntity<ApiResponse<AgentResponseDTO>> createAgent(
            @Valid @RequestBody AgentRequestDTO request) {

        AgentResponseDTO agent = agentService.createAgent(request);

        return ResponseUtil.success(agent, "Agent created successfully", HttpStatus.CREATED);
    }

    // Get All Agents
    @GetMapping
    public ResponseEntity<ApiResponse<List<AgentResponseDTO>>> getAllAgents() {

        List<AgentResponseDTO> agents = agentService.getAllAgents();

        return ResponseUtil.success(agents, "Agents retrieved successfully", HttpStatus.OK);
    }
}
