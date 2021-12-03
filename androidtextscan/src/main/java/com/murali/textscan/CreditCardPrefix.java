package com.murali.textscan;

public class CreditCardPrefix {

    // Credit Card type prefix starting range
    int rangeStart;

    // Credit Card type prefix ending range
    int rangeEnd;

    // Credit Card type prefix length
    int prefixLength;


    CreditCardPrefix(int rangeStart, int rangeEnd, int prefixLength) {

        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
        this.prefixLength = prefixLength;
    }
}
