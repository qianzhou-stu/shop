package com.andreas.shop.serviceImp;

import com.andreas.shop.common.Constant;
import com.andreas.shop.dao.CartMapper;
import com.andreas.shop.dao.ProductMapper;
import com.andreas.shop.exception.ShopBussinessException;
import com.andreas.shop.exception.ShopException;
import com.andreas.shop.pojo.Cart;
import com.andreas.shop.pojo.Product;
import com.andreas.shop.pojo.vo.CartVO;
import com.andreas.shop.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author andreaszhou
 * @ClassName CartServiceImpl
 * @Description TODO
 * @date 2021/2/1 15:13
 * @Version 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;

    @Override
    public List<CartVO> addCart(Integer userId, Integer productId, Integer count) throws ShopBussinessException {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        isValid(productId, count);
        if (cart == null) {
            //这个商品之前不在购物车里，需要新增一个记录
            cart = new Cart();
            cart.setProductId(productId);
            cart.setUserId(userId);
            cart.setQuantity(count);
            cart.setSelected(Constant.Cart.CHECKED);
            int i = cartMapper.insertSelective(cart);
            if (i == 0) {
                throw new ShopBussinessException(ShopException.INSERT_FAILED);
            }
        } else {
            //这个商品已经在购物车里了，则数量相加
            count = cart.getQuantity() + count;
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            newCart.setProductId(productId);
            newCart.setQuantity(count);
            newCart.setSelected(Constant.Cart.CHECKED);
            int i = cartMapper.updateByPrimaryKeySelective(newCart);
            if (i == 0) {
                throw new ShopBussinessException(ShopException.ADD_FAILURE);
            }
        }
        return this.listCart(userId);
    }


    /*
     * 在这里我们写一个方法，用来判断商品的状态、
     * 这个是一个验证的作用，验证是不是有相应的产品并且已经上架了
     * 或者验证库存是不是存在。
     * 1.商品是否存在，以及商品是否是上架的
     * 2.判断库存是不是足够的。
     * */
    private void isValid(Integer productId, Integer count) throws ShopBussinessException {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null || product.getStatus().equals(Constant.SaleStatus.NOT_SALT)) {
            throw new ShopBussinessException(ShopException.NOT_SALE);
        }
        if (count > product.getStock()) {
            throw new ShopBussinessException(ShopException.NOT_STOCK);
        }
    }

    @Override
    public List<CartVO> listCart(Integer userId) {

        List<CartVO> cartVOS = cartMapper.selectList(userId);
        for (int i = 0; i < cartVOS.size(); i++) {
            CartVO cartVO = cartVOS.get(i);
            cartVO.setTotalPrice(cartVO.getPrice() * cartVO.getQuantity());
        }
        return cartVOS;
    }

    @Override
    public List<CartVO> deleteCart(Integer userId, Integer productId) throws ShopBussinessException {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法删除
            throw new ShopBussinessException(ShopException.DELETE_FAILED);
        }
        //这个商品再购物车里，可以删除
        int i = cartMapper.deleteByPrimaryKey(cart.getId());
        if (i == 0) {
            throw new ShopBussinessException(ShopException.DELETE_FAILED);
        }
        return this.listCart(userId);
    }

    @Override
    public List<CartVO> updateCart(Integer userId, Integer productId, Integer count) throws ShopBussinessException {
        isValid(productId, count);
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法更新
            throw new ShopBussinessException(ShopException.UPDATE_FAILED);
        } else {
            //这个商品已经在购物车里了，则更新数量
            Cart newCart = new Cart();
            newCart.setQuantity(count);
            newCart.setId(cart.getId());
            newCart.setProductId(cart.getProductId());
            newCart.setUserId(cart.getUserId());
            newCart.setSelected(Constant.Cart.CHECKED);
            cartMapper.updateByPrimaryKeySelective(newCart);
        }
        return this.listCart(userId);
    }

    @Override
    public List<CartVO> selectOrNot(Integer userId, Integer productId, Integer selected) throws ShopBussinessException {
        Cart cart = cartMapper.selectCartByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个商品之前不在购物车里，无法选择/不选中
            throw new ShopBussinessException(ShopException.UPDATE_FAILED);
        } else {
            //这个商品已经在购物车里了，则可以选中/不选中
            cartMapper.selectOrNot(userId,productId,selected);
        }
        return this.listCart(userId);
    }

    @Override
    public List<CartVO> selectAllOrNot(Integer userId, Integer selected) {
        //改变选中状态
        cartMapper.selectOrNot(userId, null, selected);
        return this.listCart(userId);
    }
}
