package com.wafer.wtp.annotation;

import java.lang.annotation.*;  

/**
 * @author wafer
 */
@Target(ElementType.PARAMETER)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface Field {  
    String value();  
}  