package com.lbi.aquerydemo.entities;

public class Feed {

	private String title;
	private String description;
	private String pubDate;
	private String thumbnailUrl;
	
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}	

	@Override
	public String toString() {
	    return "Feed [title=" + title + ", description=" + description +
	            ", pubDate=" + pubDate + ", thumbnailUrl=" + thumbnailUrl + "]";
	}
}
