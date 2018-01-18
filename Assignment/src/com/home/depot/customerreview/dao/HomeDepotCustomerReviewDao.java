package com.home.depot.customerreview.dao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.dao.CustomerReviewDao;

public interface HomeDepotCustomerReviewDao extends CustomerReviewDao {
	Integer getNumberOfReviewsForGivenRatingRange(ProductModel productModel,double lowerLimit,double upperLimit);
	Integer getNumberOfReviewsForGivenRating(ProductModel productModel,double rating);

}
