package com.kakaopay.housingfinance.utils;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {
    private static final int CHARACTERISTICS = 0;

    public static <T> Stream<T> stream(Iterator<T> iterator) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, CHARACTERISTICS), false);
    }
}
