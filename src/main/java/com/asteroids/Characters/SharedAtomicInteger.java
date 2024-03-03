package com.asteroids.Characters;

import java.util.concurrent.atomic.AtomicInteger;

public class SharedAtomicInteger {
    private static final AtomicInteger currentImageIndex = new AtomicInteger(0);

    public static AtomicInteger getCurrentImageIndex() {
        return currentImageIndex;
    }

   

}
