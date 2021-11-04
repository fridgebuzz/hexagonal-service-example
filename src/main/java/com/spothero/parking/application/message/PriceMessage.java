package com.fridgebuzz.parking.application.message;

import java.io.Serializable;

/**
 * Representation of price response.
 *
 * @see com.fridgebuzz.parking.application.rest.PriceController
 */
public class PriceMessage implements Serializable
{
    private static final long serialVersionUID = 131015697644373776L;

    private int price;

    public PriceMessage() {}

    public PriceMessage(int price)
    {
        this.price = price;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }
}
