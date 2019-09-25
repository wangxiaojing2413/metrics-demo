package com.alibaba.wuchong.metrics;

import java.util.Random;

class Database{
        static Random rn = new Random();
        String url;

       
        public boolean isConnected() {
               // TODOAuto-generated method stub
               return rn.nextBoolean();
        }

    public String getUrl() {
            return url;
    }
}