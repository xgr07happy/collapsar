package com.devpt.collapsar.configure.audit;

/**
 * Created by chenyong on 2016/5/25.
 */
public enum MaskType {
    ALL_SECRET{
        @Override
        public String getMaskText(String val) {
            return "**************";
        }
    },ID_CARD{
        @Override
        public String getMaskText(String val) {
            if (val == null || val.length() <= 10) {
                return val;
            }
            return val.substring(0, 6) + "********" + val.substring(val.length() - 4, val.length());
        }
    },MOBILE{
        @Override
        public String getMaskText(String val) {
            if (val == null || val.length() <= 7) {
                return val;
            }
            return val.substring(0, 3) + "****" + val.substring(val.length() - 4, val.length());
        }
    },BANK_CARD {
        @Override
        public String getMaskText(String val) {
            if (val == null || val.length() <= 8) {
                return val;
            }
            return val.substring(0, 4) + "****" + val.substring(val.length() - 4, val.length());
        }
    },CVN2 {
        @Override
        public String getMaskText(String val) {
            if (val == null || val.length() != 3) {
                return val;
            }
            return "**" + val.substring(val.length() - 1, val.length());
        }
    };
    public abstract String getMaskText(String val);
}
