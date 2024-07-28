package com.mycompany.graph.util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
public class TSPGen {
    private final int x;
    private final int y;

    TSPGen(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    double distance(final TSPGen other) {
        // distance formula
        return sqrt(pow(getX() - other.getX(), 2)) + pow(getY() - other.getY(), 2);
    }


}