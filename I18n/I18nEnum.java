/**
 * TODO
 * @author Johan
 */
public enum TODO {
    ENUM("ENG","FR");
    
    private static Language language = Language.ENG;
    private final String englishName;
    private final String frenchName;
    
    private Action(String englishName, String frenchName) {
        this.englishName = englishName;
        this.frenchName = frenchName;
    }
    
    public static void setLanguage(Language language_) {
        language = language_;
    }
    
    public static Language getLanguage() {
        return language;
    }
    
    public String getName() {
        switch(language) {
            case ENG:
                return this.englishName;
            default:
                return this.frenchName;
        }
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
