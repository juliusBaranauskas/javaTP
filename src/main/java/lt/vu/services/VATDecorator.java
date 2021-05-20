package lt.vu.services;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

@Decorator
public abstract class VATDecorator implements PriceCalculator {

    @Inject
    @Delegate
    @Any
    PriceCalculator priceCalculator;

    public double calcPrice(int id, double price) {

        double other = priceCalculator.calcPrice(id, price);

        // add value added tax if price is over the threshold
        if (price > 20) {
            price *= 1.21;
        }

        // return average
        return (price + other ) / 2;
    }
}