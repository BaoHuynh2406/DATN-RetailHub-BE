package com.project.retailhub.service;

import com.project.retailhub.data.dto.request.product.ProductRequest;
import com.project.retailhub.data.dto.response.Pagination.PageResponse;
import com.project.retailhub.data.dto.response.UserResponse;
import com.project.retailhub.data.dto.response.product.ProductResponse;
import com.project.retailhub.data.repository.CategoryRepository;

import java.util.List;

/**
 * This interface defines the contract for the Product Service.
 * It provides methods for managing products in the RetailHub application.
 */
public interface ProductService
{
    /**
     * Adds a new product to the system.
     *
     * @param request The request object containing the product details.
     */
    void addProduct(ProductRequest request);

    /**
     * Updates an existing product in the system.
     *
     * @param request The request object containing the updated product details.
     */
    void updateProduct(ProductRequest request);

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param productId The unique identifier of the product.
     * @return The product response object containing the product details.
     */
    ProductResponse getProduct(long productId);

    /**
     * Deletes a product from the system.
     *
     * @param productId The unique identifier of the product.
     */
    void deleteProduct(long productId);

    /**
     * Restores a deleted product in the system.
     *
     * @param productId The unique identifier of the product.
     */
    void restoreProduct(long productId);

    /**
     * Retrieves all products from the system.
     *
     * @return A list of product response objects containing the product details.
     */
    List<ProductResponse> findAllProduct();

    /**
     * Retrieves all available products from the system.
     *
     * @return A list of product response objects containing the available product details.
     */
    List<ProductResponse> findAllAvailableProduct();

    /**
     * Retrieves all deleted products from the system.
     *
     * @return A list of product response objects containing the deleted product details.
     */
    List<ProductResponse> findAllDeletedProduct();

    /**
     * Retrieves a product by its barcode.
     *
     * @param barcode The barcode of the product.
     * @return The product response object containing the product details.
     */
    ProductResponse getByBarcode(String barcode);

    /**
     * Retrieves all products belonging to a specific category.
     *
     * @param categoryId The unique identifier of the category.
     * @return A list of product response objects containing the product details.
     */
    List<ProductResponse> getAllByCategoryId(int categoryId);

    /**
     * Retrieves all products using pagination.
     *
     * @param page The current page number.
     * @param size The number of products per page.
     * @return The page response object containing the product details and pagination information.
     */
    PageResponse<ProductResponse> findAllProductPagination(int page, int size);

    /**
     * Retrieves all available products using pagination.
     *
     * @param page The current page number.
     * @param size The number of products per page.
     * @return The page response object containing the available product details and pagination information.
     */
    PageResponse<ProductResponse> findAllProductPaginationAvailable(int page, int size);

    /**
     * Retrieves all deleted products using pagination.
     *
     * @param page The current page number.
     * @param size The number of products per page.
     * @return The page response object containing the deleted product details and pagination information.
     */
    PageResponse<ProductResponse> findAllProductPaginationDeleted(int page, int size);
}
