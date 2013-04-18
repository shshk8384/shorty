package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Required;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class UsedToken extends Model {
	@Required
	@Unique
	public String token;
	
	@Required
	@ManyToOne
	public User user;
	
	@Required
	public Date used_at;
}
