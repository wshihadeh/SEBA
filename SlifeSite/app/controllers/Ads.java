package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Ads  extends Application {

	 public static void index() {
	        List<Ads> ads = Ad.all().fetch();
	        render(ads);
	    }

	 public static void list(String search, Integer size, Integer page) {
	        List<Ads> ads = null;
	        page = page != null ? page : 1;
	        if(search.trim().length() == 0) {
	            ads = Ad.all().fetch(page, size);
	        } else {
	            search = search.toLowerCase();
	            ads = Ad.find("lower(headline) like ?1 OR lower(description) like ?2", "%"+search+"%", "%"+search+"%").fetch(page, size);
	        }
	        render(ads, search, size, page);
	    }
}
