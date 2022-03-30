package partalex.homework_one.options;

public enum Fence_Choose {
    FENCE_1("fence1"),
    FENCE_2("fence2"),
    FENCE_3("fence3");

    Fence_Choose(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text + ".jpg";
    }

    private final String text;

}
