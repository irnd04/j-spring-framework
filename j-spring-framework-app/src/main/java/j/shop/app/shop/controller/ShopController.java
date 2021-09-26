package j.shop.app.shop.controller;

import j.shop.app.shop.domain.Shop;
import j.shop.app.shop.service.DefaultShopService;
import j.shop.app.shop.service.ShopService;
import j.shop.app.shop.web.ShopRequest;
import j.shop.app.web.ShopResponse;
import j.spring.framework.core.web.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shops")
@ResponseBody
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(DefaultShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ShopResponse<List<Shop>> findShops() {
        return ShopResponse.success(shopService.getShops());
    }

    @RequestMapping(value = "/{shop}", method = RequestMethod.GET)
    public ShopResponse<Shop> getShop(@PathVariable Shop shop) {
        return ShopResponse.success(shop);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ShopResponse<Shop> addShop(@RequestBody ShopRequest request) {
        request.validate();
        return ShopResponse.success(shopService.addShop(request.toShop()));
    }

    @RequestMapping(value = "/{shopId}", method = RequestMethod.DELETE)
    public ShopResponse<Shop> deleteShop(@PathVariable int shopId) {
        return ShopResponse.success(shopService.deleteShopById(shopId));
    }

    @RequestMapping(value = "/{shopId}", method = RequestMethod.PUT)
    public ShopResponse<Shop> updateShop(@PathVariable int shopId, @RequestBody ShopRequest request) {
        request.validate();
        Shop shop = request.toShop(shopId);
        shopService.updateShop(shop);
        return ShopResponse.success(shop);
    }

}
