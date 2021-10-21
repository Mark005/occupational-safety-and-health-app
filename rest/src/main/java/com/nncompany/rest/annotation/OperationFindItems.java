package com.nncompany.rest.annotation;

import com.nncompany.api.dto.RequestError;
import com.nncompany.api.model.wrappers.ResponseList;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Operation
public @interface OperationFindItems {

    /**
     * The list of possible responses as they are returned from executing this operation.
     *
     * @see Operation#responses()
     **/
    ApiResponse[] responses() default {
            @ApiResponse(responseCode = "200", description = "Successful search result",
                    content = @Content(schema = @Schema(implementation = ResponseList.class))),

            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = RequestError.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = RequestError.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(schema = @Schema(implementation = RequestError.class))),
    };

    /**
     * @see Operation#summary()
     */
    @AliasFor(annotation = Operation.class, attribute = "summary")
    String value() default "Get list of elements";

    /**
     * @see Operation#security()
     */
    @AliasFor(annotation = Operation.class, attribute = "security")
    SecurityRequirement[] security() default {
            @SecurityRequirement(name = "AUTHORIZATION")
    };
}
