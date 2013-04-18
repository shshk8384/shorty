package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void redir(String slug) {
    	Logger.info("Fetching %s...", slug);
    	Entry e = Entry.find("slug", slug).first();
    	Logger.info("Redirecting from %s to %s", slug, e.url);
    	redirect(e.url, false);
    }
    
    public static void token(String login, String timestamp) {
    	User user = User.find("login", login).first();
    	String token = user.generateToken(timestamp);
    	renderJSON(token);
    }

}