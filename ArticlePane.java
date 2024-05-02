package com.inventorymanagementsystem.utils;

import javafx.scene.layout.Pane;

public class ArticlePane extends Pane {
    private Integer articleId;
    public  ArticlePane()
    {super();}
    public ArticlePane(int articleId) {
        super();
        this.articleId = articleId;
    }
    public ArticlePane(Pane P,int articleId) {
        super(P);
        this.articleId = articleId;
    }

    public int getArticleId() {
        return articleId;
    }
    public void setArticleId(Integer ArticleId) {
         articleId=ArticleId;
    }

    @Override
    public String toString() {
        return "Article_id= " + articleId + ", " + super.toString();
    }

}