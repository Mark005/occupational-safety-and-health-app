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
     * @see Operation#method()
     **/
    @AliasFor(annotation = Operation.class, attribute = "method")
    String method() default "GET";

    /**
     * @see Operation#tags()
     */
    @AliasFor(annotation = Operation.class, attribute = "tags")
    String[] tags() default {};

    /**
     * @see Operation#summary()
     */
    @AliasFor(annotation = Operation.class, attribute = "summary")
    String value() default "Get list of elements";

    /**
     * @see Operation#description()
     */
    @AliasFor(annotation = Operation.class, attribute = "description")
    String description() default "";

    /**
     * @see Operation#requestBody()
     */
    @AliasFor(annotation = Operation.class, attribute = "requestBody")
    RequestBody requestBody() default @RequestBody();

    /**
     * @see Operation#externalDocs()
     */
    @AliasFor(annotation = Operation.class, attribute = "externalDocs")
    ExternalDocumentation externalDocs() default @ExternalDocumentation();

    /**
     * @see Operation#operationId()
     */
    @AliasFor(annotation = Operation.class, attribute = "operationId")
    String operationId() default "";

    /**
     * @see Operation#parameters()
     */
    @AliasFor(annotation = Operation.class, attribute = "parameters")
    Parameter[] parameters() default {};

    /**
     * @see Operation#deprecated()
     */
    @AliasFor(annotation = Operation.class, attribute = "deprecated")
    boolean deprecated() default false;

    /**
     * @see Operation#security()
     */
    @AliasFor(annotation = Operation.class, attribute = "security")
    SecurityRequirement[] security() default {};

    /**
     * @see Operation#servers()
     */
    @AliasFor(annotation = Operation.class, attribute = "servers")
    Server[] servers() default {};

    /**
     * @see Operation#extensions()
     */
    @AliasFor(annotation = Operation.class, attribute = "extensions")
    Extension[] extensions() default {};

    /**
     * @see Operation#hidden()
     */
    @AliasFor(annotation = Operation.class, attribute = "hidden")
    boolean hidden() default false;

    /**
     * @see Operation#ignoreJsonView()
     */
    @AliasFor(annotation = Operation.class, attribute = "ignoreJsonView")
    boolean ignoreJsonView() default false;
}
