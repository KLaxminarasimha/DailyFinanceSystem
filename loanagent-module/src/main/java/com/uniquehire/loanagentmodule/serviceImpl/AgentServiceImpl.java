package com.uniquehire.loanagentmodule.serviceImpl;

import com.uniquehire.loanagentmodule.dto.Request.AgentRequestDTO;
import com.uniquehire.loanagentmodule.dto.Response.AgentResponseDTO;
import com.uniquehire.loanagentmodule.entity.Agent;
import com.uniquehire.loanagentmodule.repository.AgentRepository;
import com.uniquehire.loanagentmodule.service.AgentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    @Override
    public AgentResponseDTO createAgent(AgentRequestDTO request) {

        if (agentRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Agent already exists with this phone");
        }

        Agent agent = mapToEntity(request);

        Agent savedAgent = agentRepository.save(agent);

        return mapToResponse(savedAgent);
    }

    @Override
    public List<AgentResponseDTO> getAllAgents() {

        return agentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private Agent mapToEntity(AgentRequestDTO request) {

        return Agent.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .area(request.getArea())
                .collectionTarget(request.getCollectionTarget())
                .commissionRate(request.getCommissionRate())
                .assignedLoans(0)
                .build();
    }

    private AgentResponseDTO mapToResponse(Agent agent) {

        AgentResponseDTO response = new AgentResponseDTO();

        response.setAgentId(agent.getAgentId());
        response.setName(agent.getName());
        response.setPhone(agent.getPhone());
        response.setEmail(agent.getEmail());
        response.setArea(agent.getArea());
        response.setAssignedLoans(agent.getAssignedLoans());
        response.setCollectionTarget(agent.getCollectionTarget());
        response.setCommissionRate(agent.getCommissionRate());

        return response;
    }
}