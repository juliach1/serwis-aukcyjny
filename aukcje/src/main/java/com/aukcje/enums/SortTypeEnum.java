package com.aukcje.enums;

public enum SortTypeEnum {
    NEWEST {
        @Override
        public String toString(){
            return "Najnowsze";
        }
    },
    PRICE_ASCENDING {
        @Override
        public String toString(){
            return "Cena (od najniższej)";
        }
    },
    PRICE_DESCENDING {
        @Override
        public String toString(){
            return "Cena (od najwyższej)";
        }
    },
    POPULARITY {
        @Override
        public String toString(){
            return "Popularność";
        }
    }
}

