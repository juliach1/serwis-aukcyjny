package com.aukcje.enums;

public enum SortTypeEnum {
    NAJNOWSZE {
        @Override
        public String toString(){
            return "Najnowsze";
        }
    },
    CENA_OD_NAJNIZSZEJ {
        @Override
        public String toString(){
            return "Cena (od najniższej)";
        }
    },
    CENA_OD_NAJWYZSZEJ{
        @Override
        public String toString(){
            return "Cena (od najwyższej)";
        }
    },
    POPULARNOSC{
        @Override
        public String toString(){
            return "Popularność";
        }
    }
}

