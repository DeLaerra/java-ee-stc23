package lesson22.pojo;


public class Article {
    private String article_uuid;
    private String header;
    private String text;
    private String date;
    private String author_uuid;
    private String isApproved;
    private String isVisible;
    private String isCommercial;


    public Article() {
    }

    public Article(String article_uuid, String header, String text, String date, String author_uuid, String isApproved, String isVisible, String isCommercial) {
        this.article_uuid = article_uuid;
        this.header = header;
        this.text = text;
        this.date = date;
        this.author_uuid = author_uuid;
        this.isApproved = isApproved;
        this.isVisible = isVisible;
        this.isCommercial = isCommercial;
    }

    public String getArticle_uuid() {
        return article_uuid;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor_uuid() {
        return author_uuid;
    }

    public String isApproved() {
        return isApproved;
    }

    public String isVisible() {
        return isVisible;
    }

    public String isCommercial() {
        return isCommercial;
    }

    @Override
    public String toString() {
        return  "article_uuid='" + article_uuid + '\'' +
                ", header='" + header + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", author_uuid='" + author_uuid + '\'' +
                ", isApproved=" + isApproved +
                ", isVisible=" + isVisible +
                ", isCommercial=" + isCommercial +
                '}';
    }
}
