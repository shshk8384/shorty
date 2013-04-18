package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.binding.As;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.data.validation.URL;
import play.data.validation.Unique;
import play.db.jpa.Model;

@Entity
public class Entry extends Model {
	@Unique
	@Required
	@MinSize(value=1)
	public String slug;
	
	@Unique
	@URL
	@Required
	public String url;
	
	@Required
	@ManyToOne
	public User creator;
	
	@Required
	public Date createdAt;
	
	public String toString() {
		return slug;
	}
}
