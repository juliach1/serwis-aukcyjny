package com.aukcje.enums;

public enum BidStatusEnum {

    TOO_LOW{
        @Override
        public String toString(){
            return "too_low";
        }
    },
    AUCTION_ENDED{
        @Override
        public String toString(){
            return "ended";
        }
    },
    BID_PLACED{
        @Override
        public String toString(){
            return "ok";
        }
    },
    USER_CREATED_AUCTION{
        @Override
        public String toString(){ return "user-created-auction"; }
    }
}
