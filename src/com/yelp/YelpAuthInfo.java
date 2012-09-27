package com.yelp;

public class YelpAuthInfo {
	private String consumerKey;
    private String consumerSecret;
    private String token;
    private String tokenSecret;
    
    public YelpAuthInfo(String _consumerKey,
			String _consumerSecret,
			String _token,
			String _tokenSecret){
    	consumerKey=_consumerKey;
		consumerSecret=_consumerSecret;
		token=_token;
		tokenSecret=_tokenSecret;
	}
    
    public String consumerKey(String _consumerKey){
    	consumerKey=_consumerKey;
    	return consumerKey;
    }
    public String consumerKey(){
    	return consumerKey;
    }
    
    public String consumerSecret(String _consumerSecret){
    	consumerSecret=_consumerSecret;
    	return consumerSecret;
    }
    public String consumerSecret(){
    	return consumerSecret;
    }
    
    public String token(String _token){
    	token=_token;
    	return token;
    }
    public String token(){
    	return token;
    }
    
    public String tokenSecret(String _tokenSecret){
    	tokenSecret=_tokenSecret;
    	return tokenSecret;
    }
    public String tokenSecret(){
    	return tokenSecret;
    }

}
