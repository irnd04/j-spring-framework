package j.shop.app.shop.converter;

import j.shop.app.exception.ShopErrorCode;
import j.shop.app.exception.ShopException;
import j.shop.app.shop.domain.Shop;
import j.shop.app.shop.service.ShopService;
import j.spring.framework.core.web.annotation.Autowired;
import j.spring.framework.core.web.annotation.Component;
import j.spring.framework.core.web.converter.Converter;

@Component
public class ShopConverter implements Converter<Shop> {

    private final ShopService shopService;

    @Autowired
    public ShopConverter(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public Shop convert(String s) {
        int shopId;
        try {
            shopId = Integer.parseInt(s);
        } catch (Exception e) {
            throw ShopException.of(ShopErrorCode.SHOP_ID_MUST_BE_NUMBER);
        }
        return shopService.getShopById(shopId);
    }
}
