package com.devicemonitoring.agent.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionResult {
    private String status; // SUCCESS, FAILED
    private String result; // Detail message
}
