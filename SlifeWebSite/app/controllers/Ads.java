package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import com.sun.tools.doclets.internal.toolkit.util.DocFinder.Output;

import models.*;

public class Ads  extends Application {

	 public static void index() {
	        List<Ad> ads = Ad.all().fetch();
	        List<Category> cats = Category.find("categorytype_id=?1","1").fetch();
	        
	       
	        int max = (Integer) Ad.find(
	        		" select max(adCount) as maxPob from (select count(*) as adCount from ad group by category_id) as tb "
	        	   ).fetch().get(0);
	        
	        
	        int min = (Integer) Ad.find(
	        		" select min(adCount) as maxPob from (select count(*) as adCount from ad group by category_id) as tb "
	        	   ).fetch().get(0);
	        
	        render(ads,cats,max,min);
	    }

	 public static void list(String search, int category, Integer size, Integer page, int firstPage, int lastPage) {
	        List<Ad> ads = null;
	        
	        List<Category> cats = Category.find("categorytype_id=?1","1").fetch();
	        
	   
	        
	        int pagesCount=0;

	        page = page != null ? page : 1;
	        if(search.trim().length() == 0) {
	        	Long l=null;
	        	if(category==0){
	        	
	        		ads = Ad.find("order by createDate desc").fetch(page, size);
		            l= Ad.count();
	        	}else{
	        		
		        
		            ads = Ad.find(" category_id=?1 order by createDate desc",category).fetch(page, size);
		            l= Ad.count(" category_id=?1 ",category);
	        		
	        	}
	            
	            Long l2=(l/10);
	            if ((l%10)>0) l2=(long) (Math.floor(l2)+1);
	            pagesCount=Integer.valueOf(l2.intValue());
	            
	        } else {
	            search = search.toLowerCase();
	            Long l= null;
	            if(category==0){
	            ads = Ad.find("(lower(headline) like ?1 OR lower(description) like ?2)", "%"+search+"%", "%"+search+"%").fetch(page, size);
	            l= Ad.count("(lower(headline) like ?1 OR lower(description) like ?2)", "%"+search+"%", "%"+search+"%");
	            }else {
	            ads = Ad.find(" category_id=?1 and (lower(headline) like ?2 OR lower(description) like ?3)",category, "%"+search+"%", "%"+search+"%").fetch(page, size);
	            l= Ad.count("category_id=?1 and (lower(headline) like ?2 OR lower(description) like ?3)",category, "%"+search+"%", "%"+search+"%");
	             }
	            
	            Long l2=(l/10);
	            if ((l%10)>0) l2=(long) (Math.floor(l2)+1);
	            pagesCount=Integer.valueOf(l2.intValue());
	            
	        }
	        
	        
	        
	        if((lastPage-page)<=2){
	           firstPage=page-2;
	           lastPage=page+7;
	           if(lastPage > pagesCount) lastPage=pagesCount;
	        	
	        }else if((page-firstPage)<=2){
		           firstPage=page-7;
		           lastPage=page+2;
		           if(firstPage<1) {
		        	   firstPage=1;
		   	           lastPage=10;
		           }
		        	
		        }
	        
	        if(lastPage>pagesCount)
	        	lastPage=pagesCount;
	        
	        render(ads, search, size, page,pagesCount,firstPage,lastPage,cats);
	    }
	 
	 public static void getImage(long id) {
  	   //final Ad ad = Ad.findById(id);
  	   //notFoundIfNull(ad);
  	   //response.setContentTypeIfNotSet(ad.image.type());
  	   //renderBinary(ad.image.get());
  	
  	  
  	}
}
