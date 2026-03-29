package base.util;
//package de.his.core.util;

import com.google.common.base.Function;

public enum ConverterFunctions {

    Int2Long() {

        @Override
        public Function<Object, Object> convert() {
            return new Function<Object, Object>() {

                @Override
                public Long apply(Object integer) {
                    return Long.valueOf(((Integer) integer).intValue());
                }

            };
        }
    },

    /**
     * Convert String to Long
     */
    String2Long() {

        @Override
        public Function<Object, Object> convert() {
            return new Function<Object, Object>() {

                @Override
                public Long apply(Object str) {
                    return Long.valueOf((String) str);
                }

            };
        }
    },

    /**
     * Convert String to Integer
     */
    String2Integer() {

        @Override
        public Function<Object, Object> convert() {
            return new Function<Object, Object>() {

                @Override
                public Integer apply(Object str) {
                    return Integer.valueOf((String) str);
                }

            };
        }
    };

    /**
     * @return a converter-Function
     */
    public abstract Function<Object, Object> convert();
}

