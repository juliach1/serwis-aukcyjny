package com.aukcje.enums;

public enum OfferTypeEnum {
    AUCTION {
        @Override
        public String toString(){
            return "aukcja";
        }
        public Integer getId(){ return 2; }
    },
    BUY_NOW {
        @Override
        public String toString(){
            return "kup teraz";
        }
        public Integer getId(){ return 1; }
    };

    public Integer getId(){ return 0; }
}
