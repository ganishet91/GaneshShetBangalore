package com.home.depot.customerreview.service;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.CustomerReviewService;

public interface HomeDepotCustomerReviewService  extends CustomerReviewService{


Integer getNumberOfReviewsForGivenRatingRange(ProductModel productModel,double lowerLimit,double upperLimit);
Integer getNumberOfReviewsForGivenRating(ProductModel productModel,double rating);
}
