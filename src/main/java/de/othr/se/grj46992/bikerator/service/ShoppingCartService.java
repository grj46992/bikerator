package de.othr.se.grj46992.bikerator.service;

import de.othr.se.grj46992.bikerator.entity.ShoppingCart;
import de.othr.se.grj46992.bikerator.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService implements ShoppingCartServiceIF {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        ShoppingCart newCart = shoppingCartRepository.save(shoppingCart);
        return newCart;
    }
}