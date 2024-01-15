package com.example.springproject.controller;

import com.example.springproject.dto.base.PageResponse;
import com.example.springproject.dto.base.ResponseGeneral;
import com.example.springproject.dto.request.UserRequest;
<<<<<<< HEAD
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
=======
import com.example.springproject.dto.request.UserUpdateRequest;
import com.example.springproject.dto.response.UserResponse;
import com.example.springproject.dto.response.UserUpdateResponse;
import com.example.springproject.service.UserService;
import com.example.springproject.service.base.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.example.springproject.constant.CommonConstants.*;
import static com.example.springproject.constant.MessageCodeConstant.*;

/**
 * Controller class for handling user-related operations in the RESTful API.
 *
 * This class is annotated with `@RestController`, indicating that it processes REST requests and produces JSON responses.
 * It uses the Lombok annotations `@Slf4j` for logging and `@RequiredArgsConstructor` for automatically injecting dependencies.
 * The base request mapping for all endpoints in this controller is "/api/v1/users".
 *
 * The class includes methods to perform CRUD operations on user entities, such as retrieving a user by ID, creating a new user,
 * searching for users, retrieving all users, and deleting a user. Each method produces a response wrapped in the `ResponseGeneral` class,
 * which follows a standardized format including a status, message, and data payload.
 *
 * The `getById` method handles GET requests to retrieve a user by ID, while the `create` method handles POST requests to create a new user.
 * The `getUserBySearch` and `getAllUser` methods handle GET requests to search for users based on a keyword and retrieve all users, respectively.
 * The `delete` method handles DELETE requests to delete a user by ID.
 *
 * Request parameters, such as ID, keyword, size, and page, are specified using annotations like `@PathVariable` and `@RequestParam`.
 * Language information is extracted from the request header and used for localized message retrieval via the `MessageService`.
 *
 * Logging statements are included using the SLF4J logger to capture relevant information for each operation.
 *
 * Note: The class demonstrates the use of standard HTTP methods, request parameters, and response structures for a user-related API.
 *
 * @author [nguyenanhtu123]
 * @version 1.0
 * @since [1/6/2023]
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final MessageService messageService;

    /**
     * Handles POST requests to create a new user.
     *
     * @param request  The request body containing user creation details.
     * @param language The language for message localization.
     * @return A ResponseEntity with a standardized response containing the localized message and the created user data.
     */
>>>>>>> main
    @PostMapping
    public ResponseGeneral<UserResponse> create(
            @RequestBody UserRequest request,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
<<<<<<< HEAD
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
=======
    ) {
        log.info("(create) Request : {}", request);
        return ResponseGeneral.ofCreated(messageService.getMessage(CREATE_USER, language),
                userService.create(request));
    }
    @PutMapping("/{id}")
    public ResponseGeneral<UserUpdateResponse> update(
            @RequestBody UserUpdateRequest request,
            @PathVariable String id,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(create) Request : {}", request);
        return ResponseGeneral.ofSuccess(messageService.getMessage(UPDATE_USER, language),
                userService.update(id,request));
    }

    /**
     * Handles GET requests to retrieve all users.
     *
     * @param size     The number of users to include in each page of the result.
     * @param page     The page number of the result to retrieve.
     * @param language The language for message localization.
     * @return A ResponseEntity with a standardized response containing the localized message and a paginated list of all users.
     */
    @GetMapping("/all")
    public ResponseGeneral<PageResponse<UserResponse>> viewAllUser(
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "page", defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(listAllUser) size : {}, page: {}", size, page);
        return ResponseGeneral.ofSuccess(messageService.getMessage(LIST_USER, language),
                userService.viewAll(size, page)
        );
    }

    /**
     * Handles DELETE requests to delete a user by ID.
     *
     * @param id       The ID of the user to delete.
     * @param language The language for message localization.
     * @return A ResponseEntity with a standardized response containing the localized success message.
     */
    @DeleteMapping("{id}")
    public ResponseGeneral<Void> delete(
            @PathVariable String id,
            @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
    ) {
        log.info("(delete) id : {}", id);
        userService.delete(id);
        return ResponseGeneral.ofSuccess(messageService.getMessage(DELETE_USER, language));
    }
}
>>>>>>> main
