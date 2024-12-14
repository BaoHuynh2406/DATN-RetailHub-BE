package com.project.retailhub.service;


import com.project.retailhub.data.dto.request.UserRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;


/**
 * This interface defines the contract for user-related operations.
 */
public interface UserService {
    /**
     * Adds a new user to the system.
     *
     * @param request The user request containing the necessary information for creating a new user.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    void addNewUser(UserRequest request);

    /**
     * Updates an existing user in the system.
     *
     * @param request The user request containing the updated information for the user.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    void updateUser(UserRequest request);

    /**
     * Retrieves a user by their employee ID.
     *
     * @param idEmployee The unique identifier of the user.
     * @return The user response containing the user's information.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    UserResponse getUser(long idEmployee);

    /**
     * Retrieves the current logged-in user's information.
     *
     * @return The user response containing the logged-in user's information.
     */
    UserResponse getMyInfo();

    /**
     * Deletes a user from the system.
     *
     * @param idEmployee The unique identifier of the user to be deleted.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteUser(long idEmployee);

    /**
     * Restores a previously deleted user.
     *
     * @param idEmployee The unique identifier of the user to be restored.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    void restoreUser(long idEmployee);

    /**
     * Toggles the active status of a user.
     *
     * @param idEmployee The unique identifier of the user whose status needs to be toggled.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    void toggleActiveUser(long idEmployee);

    /**
     * Retrieves all users from the system.
     *
     * @return A list of user responses containing the information of all users.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    List<UserResponse> findAllUser();

    /**
     * Retrieves all users from the system in a paginated manner.
     *
     * @param page The page number to retrieve.
     * @param size The number of users per page.
     * @return A page response containing the list of user responses and pagination information.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    PageResponse<UserResponse> getAllUserPagination(int page, int size);

    /**
     * Retrieves all available users from the system in a paginated manner.
     *
     * @param page The page number to retrieve.
     * @param size The number of users per page.
     * @return A page response containing the list of available user responses and pagination information.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    PageResponse<UserResponse> getAllAvailableUsersPagination(int page, int size);

    /**
     * Retrieves all deleted users from the system in a paginated manner.
     *
     * @param page The page number to retrieve.
     * @param size The number of users per page.
     * @return A page response containing the list of deleted user responses and pagination information.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    PageResponse<UserResponse> getAllDeletedUsersPagination(int page, int size);

    /**
     * Retrieves all available users from the system.
     *
     * @return A list of user responses containing the information of all available users.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    List<UserResponse> findAllAvailableUsers();

    /**
     * Retrieves all deleted users from the system.
     *
     * @return A list of user responses containing the information of all deleted users.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    List<UserResponse> findAllDeletedUsers();

    /**
     * Retrieves a user by their email address.
     *
     * @param email The email address of the user.
     * @return The user response containing the user's information.
     */
    UserResponse getByEmail(String email);
}
