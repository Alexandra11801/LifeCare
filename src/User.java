import sun.security.util.Password;
import java.awt.*;
import java.io.InputStream;

public class User {

	private String name;
	private String surmname;
	private String password;
	private String email;
	private InputStream avatar;
	private String imageName;

	public User(String name, String surmname, String password, String email, InputStream avatar) {
		this.name = name;
		this.surmname = surmname;
		this.password = password;
		this.email = email;
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurmname() {
		return surmname;
	}

	public void setSurmname(String surmname) {
		this.surmname = surmname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public InputStream getAvatar() {
		return avatar;
	}

	public void setAvatar(InputStream avatar) {
		this.avatar = avatar;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
}
