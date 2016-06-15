package com.sys.ekey.shop.common;

/**
 * 库存不足异常
 * Created by Maryn on 2016/4/22.
 */
public class ProductOutOfStockException extends EKShopException {

    public ProductOutOfStockException(){}

    public ProductOutOfStockException(String message) {
        super(message);
    }
}
