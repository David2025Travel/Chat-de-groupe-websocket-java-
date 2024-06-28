package chatRedux.davidaye.beans;

public class User {

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public User(String username) {
		this.username = username;
	}
	
	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		if(this.username.equals(user.username)) {
			return true;
		}
		return false;
	}
}
