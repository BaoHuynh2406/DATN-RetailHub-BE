package com.project.retailhub.data.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject<OBJ> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int code = 200;

    private String message;

    private OBJ data;

}