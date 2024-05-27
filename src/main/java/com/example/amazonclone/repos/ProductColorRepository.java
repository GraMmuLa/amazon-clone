package com.example.amazonclone.repos;

import com.example.amazonclone.models.ProductColor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductColorRepository extends RefreshableRepository<ProductColor, Long> {
    @Query(value = "SELECT id,price,color_id,product_id,main_image_id,created_at FROM (SELECT pr.id, pr.price, pr.color_id, pr.product_id, pr.main_image_id, pr.created_at FROM product_colors AS pr " +
            "LEFT JOIN discounts AS ds ON pr.id = ds.product_color_id " +
            "WHERE ds.price IS NULL " +
            "UNION " +
            "SELECT pr.id AS id, (pr.price-ds.price), pr.color_id AS color_id, pr.product_id AS product_id, pr.main_image_id AS main_image_id, pr.created_at AS created_at FROM product_colors AS pr INNER JOIN discounts AS ds " +
            "ON pr.id = ds.product_color_id) a ORDER BY price ASC",
            nativeQuery = true)
    public Page<ProductColor> findAllPriceAsc(Pageable pageable);
    @Query(value = "SELECT id,price,color_id,product_id,main_image_id,created_at FROM (SELECT pr.id, pr.price, pr.color_id, pr.product_id, pr.main_image_id, pr.created_at FROM product_colors AS pr " +
            "LEFT JOIN discounts AS ds ON pr.id = ds.product_color_id " +
            "WHERE ds.price IS NULL " +
            "UNION " +
            "SELECT pr.id AS id, (pr.price-ds.price), pr.color_id AS color_id, pr.product_id AS product_id, pr.main_image_id AS main_image_id, pr.created_at AS created_at FROM product_colors AS pr INNER JOIN discounts AS ds " +
            "ON pr.id = ds.product_color_id) a ORDER BY price ASC",
            nativeQuery = true)
    public List<ProductColor> findAllPriceAsc();

    @Query(value = "SELECT id,price,color_id,product_id,main_image_id,created_at FROM (SELECT pr.id, pr.price, pr.color_id, pr.product_id, pr.main_image_id, pr.created_at FROM product_colors AS pr " +
            "LEFT JOIN discounts AS ds ON pr.id = ds.product_color_id " +
            "WHERE ds.price IS NULL " +
            "UNION " +
            "SELECT pr.id AS id, (pr.price-ds.price), pr.color_id AS color_id, pr.product_id AS product_id, pr.main_image_id AS main_image_id, pr.created_at AS created_at FROM product_colors AS pr INNER JOIN discounts AS ds " +
            "ON pr.id = ds.product_color_id) a ORDER BY price DESC",
            nativeQuery = true)
    public Page<ProductColor> findAllPriceDesc(Pageable pageable);
    @Query(value = "SELECT id,price,color_id,product_id,main_image_id,created_at FROM (SELECT pr.id, pr.price, pr.color_id, pr.product_id, pr.main_image_id, pr.created_at FROM product_colors AS pr " +
            "LEFT JOIN discounts AS ds ON pr.id = ds.product_color_id " +
            "WHERE ds.price IS NULL " +
            "UNION " +
            "SELECT pr.id AS id, (pr.price-ds.price), pr.color_id AS color_id, pr.product_id AS product_id, pr.main_image_id AS main_image_id, pr.created_at AS created_at FROM product_colors AS pr INNER JOIN discounts AS ds " +
            "ON pr.id = ds.product_color_id) a ORDER BY price DESC",
            nativeQuery = true)
    public List<ProductColor> findAllPriceDesc();

    public Page<ProductColor> findAllByOrderByCreatedAtDesc(Pageable pageable);
    public List<ProductColor> findAllByOrderByCreatedAtDesc();

    @Query(value = "SELECT id,price,color_id,product_id,main_image_id,created_at FROM (SELECT pr.id, pr.price, pr.color_id, pr.product_id, pr.main_image_id, pr.created_at FROM product_colors AS pr " +
            "LEFT JOIN discounts AS ds ON pr.id = ds.product_color_id " +
            "WHERE ds.price IS NULL " +
            "UNION " +
            "SELECT pr.id AS id, (pr.price-ds.price), pr.color_id AS color_id, pr.product_id AS product_id, pr.main_image_id AS main_image_id, pr.created_at AS created_at FROM product_colors AS pr INNER JOIN discounts AS ds " +
            "ON pr.id = ds.product_color_id) a WHERE price BETWEEN :priceFrom AND :priceTo",
            nativeQuery = true)
    public Page<ProductColor> findAllPriceFromTo(@Param("priceFrom") Double priceFrom,
                                                 @Param("priceTo") Double priceTo,
                                                 Pageable pageable);

    //TODO
//    @Query(value="SELECT ")
//    public Page<ProductColor> findAllByDiscount(@Param("discountPercent") Long discountPerCent);
}