package controllers;

import groovy.json.JsonBuilder;

import java.io.IOException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import models.Entry;
import models.UsedToken;
import models.User;
import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Scope.Session;

public class API extends Controller {
	@Before
	public  static void checkAccess() {
		String user = params.get("user");
		String token = params.get("token");
		String timestamp = params.get("timestamp");
		
		if (user == null || token == null || timestamp == null) {
			Logger.info("Parameters missing. (%s, %s, %s)", user, token, timestamp);
			forbidden("Authentication parameters missing.");
		}
		
		User currentUser = User.find("login", user).first();
		if (UsedToken.find("token", token).fetch().size() > 0 || currentUser == null || !currentUser.checkToken(token, timestamp)) {
			forbidden("Access denied!");
		}
		
		UsedToken ut = new UsedToken();
		ut.token = token;
		ut.used_at = new Date();
		ut.user = currentUser;
		ut.save();
		
		session.put("currentUser", currentUser.login);
	}
	
	public static void createEntry() {
		User currentUser = User.find("login", session.get("currentUser")).first();
		
		Entry entry = new Entry();
		entry.url = params.get("url");
		entry.slug = params.get("slug");
		entry.createdAt = new Date();
		entry.creator = currentUser;
		
		renderJSON(entry.validateAndSave());
	}
}
