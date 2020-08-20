package lesson22.pojo;


public class ArticleBuilder {
    private String article_uuid;
    private String header;
    private String text;
    private String date;
    private String author_uuid;
    private String isApproved;
    private String isVisible;
    private String isCommercial;

    public ArticleBuilder setArticle_uuid(String article_uuid) {
        this.article_uuid = article_uuid;
        return this;
    }

    public ArticleBuilder setHeader(String header) {
        this.header = header;
        return this;
    }

    public ArticleBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public ArticleBuilder setDate(String date) {
        this.date = date;
        return this;
    }

    public ArticleBuilder setAuthor_uuid(String author_uuid) {
        this.author_uuid = author_uuid;
        return this;
    }

    public ArticleBuilder setIsApproved(String isApproved) {
        this.isApproved = isApproved;
        return this;
    }

    public ArticleBuilder setIsVisible(String isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public ArticleBuilder setIsCommercial(String isCommercial) {
        this.isCommercial = isCommercial;
        return this;
    }

    public Article createArticle() {
        return new Article(article_uuid, header, text, date, author_uuid, isApproved, isVisible, isCommercial);
    }
}