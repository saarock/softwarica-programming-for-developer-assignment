package com.mycompany.graph.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.mycompany.graph.World;

public class TSPUtils {
    private final static Random R = new Random(10000);
    public static final TSPGen[] CITIES = generateDate(100);
    private TSPUtils() {
        throw new RuntimeException("NO!");
    }

    private static TSPGen[] generateDate(final int numDataPnt) {
        final TSPGen[] data = new TSPGen[numDataPnt];
        for (int i =0; i<numDataPnt; i++) {
            data[i] = new TSPGen(TSPUtils.randomIndex(World.WIDTH),
            TSPUtils.randomIndex(World.HEIGHT));
        }
        return data;
    }

    static int randomIndex(final int limit) {
        return R.nextInt(limit);
    }


    static<T> List<T>[] split(final List<T> list) {
        final List<T> first = new ArrayList<>();
        final List<T> second  = new ArrayList<>();
        final int size = list.size();
        IntStream.range(0, size).forEach(i -> {
            if (i < (size+1)/2) {
                first.add(list.get(i));
            } else {
                second.add(list.get(i));
            }
         });

         return (List<T>[]) new List[] {first, second};
    }

}
