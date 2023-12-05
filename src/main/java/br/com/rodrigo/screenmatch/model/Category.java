package br.com.rodrigo.screenmatch.model;

public enum Category {
    ACAO("Action"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    COMEDIA("Comedy"),
    CRIME("Crime"),
    FANTASIA("Fantasy"),
    ANIMACAO("Animation");

    private final String OmdbCategory;

    Category(String OmdbCategory) {
        this.OmdbCategory = OmdbCategory;
    }

    public static Category fromString(String text){
        for(Category category : Category.values()){
            if(category.OmdbCategory.equalsIgnoreCase(text))
                return category;
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a serie.");
    }
}
