package org.dom;

public enum RecyclingCategory {
    PLASTIC,
    GLASS,
    METAL,
    PAPER,
    ORGANIC,
    OTHER
}

/*fixed domain set
no behavior evolution needed
good OCP via enum extension (or later replacement with strategy if needed)*/