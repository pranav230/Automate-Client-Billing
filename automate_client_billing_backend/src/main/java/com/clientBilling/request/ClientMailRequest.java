package com.clientBilling.request;

import lombok.Data;

@Data
public class ClientMailRequest {
    private Integer projectId;
    private String toClientComment;

    public ClientMailRequest(Integer projectId, String toClientComment) {
        this.projectId = projectId;
        this.toClientComment = toClientComment;
    }
}
