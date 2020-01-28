package com.example.studentagency.bean;

/**
 * author：LongSh1z
 * email：2674461089@qq.com
 * time：2019/12/16
 * desc:
 */
public class NewsBean {
    private int newsId;
    private String newsPic;
    private String title;
    private String newsUrl;
    private String uploadTime;

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getNewsPic() {
        return newsPic;
    }

    public void setNewsPic(String newsPic) {
        this.newsPic = newsPic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
