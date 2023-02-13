package com.aukcje.enums;

public enum OfferTypeEnum {
    AUKCJA {
        @Override
        public String toString(){
            return "aukcja";
        }
        public Integer getId(){ return 2; }
    },
    KUP_TERAZ {
        @Override
        public String toString(){
            return "kup teraz";
        }
        public Integer getId(){ return 1; }
    }

}
