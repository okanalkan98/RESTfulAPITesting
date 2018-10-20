package RESTfulAPITesting.Beans;

public class Post {

	private String title;
	private String bar;
	private int userId;
	
	public void setTitle(String title) {
		this.title=title;
	}
	
	public void setBody(String bar) {
		this.bar=bar;
	}
	
	public void setUserId(int userId) {
		this.userId=userId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getBody() {
		return bar;
	}
	
	public int getUserId() {
		return userId;
	}
}
