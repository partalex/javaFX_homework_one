package partalex.homework_one.options;

public enum Terrain_Choose {
    CONCRETE("concrete"),
    SPACE("space"),
    GRASS("grass");

    Terrain_Choose(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text + ".jpg";
    }

    private final String text;

}
