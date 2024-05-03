package com.inventorymanagementsystem.utils;

import javafx.scene.layout.Pane;

public class ArticlePane extends Pane {
    private Integer articleId;
    private ArticlePane original;
    private String Titre;
    private String Contenu;

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public String getContenu() {
        return Contenu;
    }

    public void setContenu(String contenu) {
        Contenu = contenu;
    }

    public ArticlePane getOriginal() {
        return original;
    }

    public void setOriginal(ArticlePane original) {
        this.original = original;
    }

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