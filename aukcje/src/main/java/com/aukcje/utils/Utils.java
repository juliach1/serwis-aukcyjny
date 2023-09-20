package com.aukcje.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static boolean isLong(String str){
        if(str == null) return false;

        try {
            double d = Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isDateBeforeNow(LocalDateTime localDateTime){
        return localDateTime.isBefore(LocalDateTime.now());
    }


//    public static Page<User> listToPageMapper(List<User> list, Pageable pageable){
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), list.size());
//        if(start > list.size())
//            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
//
//        return new PageImpl<>(list.subList(start, end), pageable, list.size());
//    }

    public static <T> Page<T> listToPageMapper(List<T> list, Pageable pageable){
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if(start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
