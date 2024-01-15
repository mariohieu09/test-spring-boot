package com.example.springproject.controller;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.UserRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.security.CustomUserDetail;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.example.springproject.constant.CommonConstants.DEFAULT_LANGUAGE;
import static com.example.springproject.constant.CommonConstants.LANGUAGE;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Operation(summary = "Get all users", description = "Retrieve a paginated list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Parameter(name = "size", description = "Number of items per page", required = false)
    @Parameter(name = "page", description = "Page number", required = false)
    @Parameter(name = "language", description = "Language for response messages", required = false)
    @GetMapping("/view-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseGeneral<PageResponse<UserResponse>> getAllUsers(){
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for(GrantedAuthority authority : userDetail.getAuthorities()){
            System.out.println(authority.getAuthority());
        }
        return null;
    }


    @Operation(summary = "Create a new user", description = "Creates a new user based on the provided request.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseGeneral<UserResponse> create(
            @RequestBody UserRequest request,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ){
        return null;
    }


    @Operation(summary = "Get user by ID", description = "Retrieve user details based on the provided ID.")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found")
    @Parameter(name = "id", description = "The ID of the user to retrieve", required = true)
    @Parameter(name = "language", description = "The language for response messages", required = false)
    @GetMapping("/{id}")
    public ResponseGeneral<UserResponse> getById(
            @PathVariable String id,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ){
        return null;
    }

}
