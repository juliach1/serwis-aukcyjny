package com.aukcje.enums;

public enum UserStatusEnum {

    ACTIVE {
        @Override
        public String toString(){
            return "AKTYWNY";
        }
        public Integer getId(){ return 1; }
    },
    BANNED {
        @Override
        public String toString(){ return "ZBANOWANY"; }
        public Integer getId(){ return 2; }
    },
    DELETED {
        @Override
        public String toString(){ return "USUNIĘTY"; }
        public Integer getId(){ return 3; }
    };

    public Integer getId(){ return 0; }
}
