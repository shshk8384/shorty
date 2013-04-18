package models;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.CharSet;

import net.sf.ehcache.search.expression.BaseCriteria;

import play.data.validation.Password;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model {
	@Required
	public String name;
	
	@Required
	public String login;
	
	@Password
	public String password;
	
	@Required
	public String apiKey;
	
	@OneToMany
	public List<Entry> entries;
	
	@Override
	public String toString() {
		return name;
	}
	
	public String generateToken(String timestamp) {
		String result = apiKey + timestamp;
		try {
			
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			byte[] hash = digest.digest(result.getBytes());
			return new String(Hex.encodeHex(hash));
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean checkToken(String token, String timestamp) {
		return token.equals(generateToken(timestamp));
	}
}
