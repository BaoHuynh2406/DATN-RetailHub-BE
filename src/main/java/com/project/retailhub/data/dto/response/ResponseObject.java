package com.project.retailhub.data.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject<OBJ> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private boolean success = true;

    private int status = 200;

    private String message;

    private OBJ data;

}