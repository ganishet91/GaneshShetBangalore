package com.home.depot.customerreview.dao.impl;

import java.util.Collections;
import com.home.depot.customerreview.dao.HomeDepotCustomerReviewDao;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.dao.impl.DefaultCustomerReviewDao;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

public class DefaultHomeDepotCustomerReviewDao extends DefaultCustomerReviewDao implements HomeDepotCustomerReviewDao {

	@Override
	public Integer getNumberOfReviewsForGivenRatingRange(ProductModel productModel, double lowerLimit,
			double upperLimit) {
		String query = "SELECT count(*) FROM {" +CustomerReviewModel._TYPECODE + "} WHERE {" + CustomerReviewModel.PRODUCT + "} = ?product AND {" + CustomerReviewModel.RATING + "} >= ?lowerLimit AND {" + CustomerReviewModel.RATING + "} <= ?upperLimit";
		FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
		fsQuery.addQueryParameter(CustomerReviewModel.PRODUCT, productModel);
		fsQuery.addQueryParameter("lowerLimit", lowerLimit);
		fsQuery.addQueryParameter("upperLimit", upperLimit);
		fsQuery.setResultClassList(Collections.singletonList(Integer.class));
		SearchResult searchResult = this.getFlexibleSearchService().search(fsQuery);
		return (Integer) searchResult.getResult().iterator().next();
		
	}

	@Override
	public Integer getNumberOfReviewsForGivenRating(ProductModel productModel, double rating) {
		String query = "SELECT count(*) FROM {" +CustomerReviewModel._TYPECODE + "} WHERE {" + CustomerReviewModel.PRODUCT + "} = ?product AND {" + CustomerReviewModel.RATING + "} = ?rating";
		FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
		fsQuery.addQueryParameter(CustomerReviewModel.PRODUCT, productModel);
		fsQuery.addQueryParameter(CustomerReviewModel.RATING, rating);
		fsQuery.setResultClassList(Collections.singletonList(Integer.class));
		SearchResult searchResult = this.getFlexibleSearchService().search(fsQuery);
		return (Integer) searchResult.getResult().iterator().next();
	}

}
