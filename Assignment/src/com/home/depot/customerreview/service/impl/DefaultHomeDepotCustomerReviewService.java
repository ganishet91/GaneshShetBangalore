package com.home.depot.customerreview.service.impl;



import org.apache.bcel.verifier.exc.AssertionViolatedException;
import org.apache.commons.lang.StringUtils;

import com.home.depot.customerreview.dao.HomeDepotCustomerReviewDao;
import com.home.depot.customerreview.service.HomeDepotCustomerReviewService;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.customerreview.impl.DefaultCustomerReviewService;
import de.hybris.platform.customerreview.jalo.CustomerReview;
import de.hybris.platform.customerreview.jalo.CustomerReviewManager;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.user.User;
import de.hybris.platform.servicelayer.config.ConfigurationService;

public class DefaultHomeDepotCustomerReviewService extends DefaultCustomerReviewService
		implements HomeDepotCustomerReviewService {
	
	private static final String REVIEW_COMMENT_CURSE_WORDS="review.comment.curse.words";
	
	private HomeDepotCustomerReviewDao homeDepotCustomerReviewDao;
	private ConfigurationService configurationService;

	public ConfigurationService getConfigurationService() {
		return configurationService;
	}

	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	public HomeDepotCustomerReviewDao getHomeDepotCustomerReviewDao() {
		return homeDepotCustomerReviewDao;
	}

	public void setHomeDepotCustomerReviewDao(HomeDepotCustomerReviewDao homeDepotCustomerReviewDao) {
		this.homeDepotCustomerReviewDao = homeDepotCustomerReviewDao;
	}

	@Override
	public Integer getNumberOfReviewsForGivenRatingRange(ProductModel productModel, double lowerLimit,
			double upperLimit) {
		if(productModel!=null)
		{
			if(lowerLimit == upperLimit)
			{
				//Return getNumberOfReviewsForGivenRating
				return getNumberOfReviewsForGivenRating(productModel,lowerLimit);
				
			}
			else
			{
				return getHomeDepotCustomerReviewDao().getNumberOfReviewsForGivenRatingRange(productModel, lowerLimit, upperLimit);
			}
		}
		return null;
	}

	@Override
	public Integer getNumberOfReviewsForGivenRating(ProductModel productModel, double rating) {
		if(productModel!=null)
		{
			return getHomeDepotCustomerReviewDao().getNumberOfReviewsForGivenRating(productModel, rating);
		}
		return null;
	}

	@Override
	public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user,
			ProductModel product) {
		if(rating!=null && rating < 0)
		{
			throw new AssertionViolatedException("Rating cannot be lesser than 0 "+rating);
		}
		//define curse words as comma separated in project/local.properties file
		String curseWords = getConfigurationService().getConfiguration().getString(REVIEW_COMMENT_CURSE_WORDS);
		String[] cursWordsArray = curseWords.split(",");
		for (String curseWord : cursWordsArray) {
			if(StringUtils.isNotEmpty(comment) && comment.contains(curseWord))
			{
				throw new AssertionViolatedException("Contains curse word can't create review "+curseWord);
			}
		}
		
		CustomerReview review = CustomerReviewManager.getInstance().createCustomerReview(rating, headline, comment,
				(User) this.getModelService().getSource(user), (Product) this.getModelService().getSource(product));
		return (CustomerReviewModel) this.getModelService().get(review);
	}
}
