package br.com.rodrigo.screenmatch.model;

public enum Category {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime"),
    FANTASIA("Fantasy", "Fantasia"),
    ANIMACAO("Animation", "Animação");

    private final String OmdbCategory;
    private final String portugeseCategory;

    Category(String OmdbCategory, String portugeseCategory) {

        this.OmdbCategory = OmdbCategory;
        this.portugeseCategory = portugeseCategory;
    }

    public static Category fromString(String text){
        for(Category category : Category.values()){
            if(category.OmdbCategory.equalsIgnoreCase(text))
                return category;
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a serie.");
    }

    public static Category fromPortugese(String text){
        for(Category category : Category.values()){
            if(category.portugeseCategory.equalsIgnoreCase(text))
                return category;
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a serie.");
    }
}
