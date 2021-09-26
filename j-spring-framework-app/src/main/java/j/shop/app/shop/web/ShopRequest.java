package j.shop.app.shop.web;

import j.shop.app.exception.ShopErrorCode;
import j.shop.app.exception.ShopException;
import j.shop.app.shop.domain.Shop;
import j.spring.framework.core.web.util.StringUtils;

public class ShopRequest {
    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Shop toShop() {
        Shop shop = new Shop();
        shop.setAddress(getAddress());
        shop.setName(getName());
        return shop;
    }

    public Shop toShop(int shopId) {
        Shop shop = this.toShop();
        shop.setId(shopId);
        return shop;
    }

    public void validate() {
        if (StringUtils.isBlank(this.getName()) || StringUtils.isBlank(this.getAddress())) {
            throw ShopException.of(ShopErrorCode.SHOP_REQUEST_DATA_INVALID);
        }
    }

    @Override
    public String toString() {
        return "ShopRequest{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
