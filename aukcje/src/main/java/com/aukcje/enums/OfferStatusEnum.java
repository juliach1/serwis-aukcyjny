package com.aukcje.enums;

public enum OfferStatusEnum {
    AKTYWNA{
        @Override
        public String toString(){
            return "aktywna";
        }
        public Integer getId(){
            return 1;
        }
    },

    ZAKONCZONA{
        @Override
        public String toString(){
            return "zakończona";
        }
        public Integer getId(){
            return 2;
        }
    },

    WSTRZYMANA{
        @Override
        public String toString(){
            return "wstrzymana";
        }
        public Integer getId(){
            return 3;
        }
    },

    USUNIETA{
        @Override
        public String toString(){
            return "usunięta";
        }
        public Integer getId(){
            return 4;
        }
    }

}
