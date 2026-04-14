package com.uniquehire.paymentservice.dtos.Request;

import com.uniquehire.paymentservice.enums.FineStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFineStatusRequest {

    @NotNull(message = "Fine status is requied")
    private FineStatus status;

    private Long paymentId;

}
