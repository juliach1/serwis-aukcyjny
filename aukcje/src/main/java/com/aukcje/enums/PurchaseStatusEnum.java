package com.aukcje.enums;

public enum PurchaseStatusEnum {

    NEW{
        @Override
        public String toString(){ return "Nowe"; }
        public Integer getId(){ return 1; }
    },

    SENT{
        @Override
        public String toString(){ return "Wysłane"; }
        public Integer getId(){ return 2; }
    },

    COMPLETED{
        @Override
        public String toString(){ return "Zrealizowane"; }
        public Integer getId(){ return 3; }
    };

    public Integer getId() { return 0; }
}
