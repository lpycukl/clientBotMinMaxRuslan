package logic;

public enum Cell {
    BLACK("b"), WHITE("w"), EMPTY(" ");

    public final String string;

    Cell(String name) {
        this.string = name;
    }

    public Cell reverse() {
        if (this == BLACK) {
            return Cell.WHITE;
        } else return Cell.BLACK;
    }

    @Override
    public String toString() {
        return string;
    }

}