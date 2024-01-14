package com.aukcje.enums;

public enum OfferStatusEnum {
    ACTIVE {
        @Override
        public String toString(){
            return "aktywna";
        }
        public Integer getId(){
            return 1;
        }
    },

    SUSPENDED {
        @Override
        public String toString(){
            return "wstrzymana";
        }
        public Integer getId(){ return 2; }
    },

    ENDED {
        @Override
        public String toString(){
            return "zakończona";
        }
        public Integer getId(){
            return 3;
        }
    },
    REMOVED {
        @Override
        public String toString(){
            return "usunięta";
        }
        public Integer getId(){
            return 4;
        }
    };

    public Integer getId(){
        return 0;
    }

}
