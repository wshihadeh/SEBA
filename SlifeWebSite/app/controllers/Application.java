package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index(int confirm) {
    	
    	List<Ad> latestAds = Ad.find("order by createDate desc").fetch(9);
    	List<Question> latestQuestions = Question.find("order by id desc").fetch(9);
    	List<RentOffer> latestRooms = RentOffer.find("order by id desc").fetch(9);
    	List<SaleOffer> latestOffers = SaleOffer.find("order by id desc").fetch(9);
    	
        render(latestAds,latestQuestions,latestRooms,latestOffers,confirm);
    }
    
    
    
    
    
    

}