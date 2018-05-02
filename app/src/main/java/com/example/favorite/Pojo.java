package com.example.favorite;

public class Pojo {
	
	private int id;
	private String  vid;
	private String  videoId;
	private String VideoName;
	private String VideoUrl;
	private String Duration;
	private String CategoryId;
	private String CategoryName;
	private String Description;
	private String ImageUrl;
	private String Vtype;
	private String Vview;
	
	public Pojo()
	{
		
	}
	
	public Pojo(String vid)
	{
		this.vid=vid;
	}
	
	public Pojo(String vid,String videoname,String imageurl,String videoduration,String catname,String vtype,String vview,String vplayid )
	{
		this.vid=vid;
 		this.VideoName=videoname;
		this.ImageUrl=imageurl;
 		this.Duration=videoduration;
		this.CategoryName=catname;
		this.Vtype=vtype;
		this.Vview=vview;
		this.videoId=vplayid;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getVId() {
		return vid;
	}

	public void setVId(String vid) {
		this.vid = vid;
	}
	
	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoid) {
		this.videoId = videoid;
	}
	
	public String getVideoName() {
		return VideoName;
	}

	public void setVideoName(String VideoName) {
		this.VideoName = VideoName;
	}
	
	public String getVideoUrl() {
		return VideoUrl;
	}

	public void setVideoUrl(String videourl) {
		this.VideoUrl = videourl;
	}
	
	public String getDuration() {
		return Duration;
	}

	public void setDuration(String duration) {
		this.Duration = duration;
	}
	
	public String getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(String catid) {
		this.CategoryId = catid;
	}
	
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String catname) {
		this.CategoryName = catname;
	}
	public String getDescription() {
		return Description;
	}

	public void setDescription(String desc) {
		this.Description = desc;
	}
	
	public String getImageUrl() {
		return ImageUrl;
	}

	public void setImageUrl(String imageurl) {
		this.ImageUrl = imageurl;
	}

	public String getVtype() {
		return Vtype;
	}

	public void setVtype(String Vtype) {
		this.Vtype = Vtype;
	}

	public String getVview() {
		return Vview;
	}

	public void setVview(String Vview) {
		this.Vview = Vview;
	}

}
