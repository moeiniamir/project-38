package model;

import java.io.Serializable;


public class Cell extends QualityHaver implements Serializable {
    public int row;
    public int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }
}