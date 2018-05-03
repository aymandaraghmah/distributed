package com.distributed.prject.distributedweb.services;

import com.distributed.prject.distributedweb.model.Coupon;
import com.distributed.prject.distributedweb.model.FoodCategory;
import com.distributed.prject.distributedweb.model.Product;
import com.distributed.prject.distributedweb.model.User;
import com.distributed.prject.distributedweb.repository.CategoryRepository;
import com.distributed.prject.distributedweb.repository.CouponRepository;
import com.distributed.prject.distributedweb.repository.ProductRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.distributed.prject.distributedweb.repository.UserRepository;
import com.distributed.prject.distributedweb.requests.LoginRequest;
import com.distributed.prject.distributedweb.security.UserAuthorizationService;

import java.util.ArrayList;
import java.util.List;

@Service

public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserAuthorizationService userAuthorizationService;

    public ResponseEntity<Object> signup(User user){

        User oldUser = userRepository.findUserByEmail(user.getEmail());

        if(oldUser != null )
            return new ResponseEntity<Object>("Email already exists.", HttpStatus.CONFLICT);

        userRepository.save(user);
        return new ResponseEntity<Object>("Account created", HttpStatus.CREATED);
    }
    public boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

    public ResponseEntity<Object> signIn(LoginRequest loginRequest)
    {
            User user = userRepository.findUserByEmail(loginRequest.getEmail());


            String sha256hex = DigestUtils.sha256Hex(loginRequest.getPassword());

            if (user == null || user.getPassword() == null
                    || !user.getPassword().equals(sha256hex)) {
                return new ResponseEntity<Object>("Incorrect username or password !",HttpStatus.FORBIDDEN);
            }
        List<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority("user"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getId(), sha256hex,authorities);


            boolean isAuthenticated = isAuthenticated(authentication);
            if (isAuthenticated) {
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            //System.out.println("user " + userAuthorizationService.getAuthenticatedUser().getId());

        return new ResponseEntity<>("Logged In",HttpStatus.OK);
        }

    public ResponseEntity<Object> updateUser(User user, int id) {
        user.setId(id);
        userRepository.save(user);
        return new ResponseEntity<>("updated",HttpStatus.OK);

    }

    public ResponseEntity<Object> deleteUser(int id) {
        User user = userRepository.findById(id);
        userRepository.delete(user);
        return new ResponseEntity<>("deleted",HttpStatus.OK);

    }

    public ResponseEntity<Object> addCouponToUser(int couponId, int userId) {
        Coupon coupon = couponRepository.findById(couponId);
        User user = userRepository.findById(userId);

        user.getCoupons().add(coupon);
        userRepository.save(user);
        return new ResponseEntity<>("added",HttpStatus.OK);
    }

    public ResponseEntity<Object> updateCoupon(Coupon coupon, int id) {
        coupon.setId(id);
        couponRepository.save(coupon);
        return new ResponseEntity<>("updated",HttpStatus.OK);

    }

    public ResponseEntity<Object> deleterCoupon(int id) {
        Coupon coupon = couponRepository.findById(id);
        couponRepository.delete(coupon);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    public ResponseEntity<Object> addProductToCategory(int productId, int categoryId) {
        FoodCategory foodCategory = categoryRepository.findById(categoryId);
        Product product= productRepository.findById(productId);
        product.setFoodCategory(foodCategory);
        productRepository.save(product);
        return new ResponseEntity<>("updated",HttpStatus.OK);

    }

    public ResponseEntity<Object> updateFoodCategory(FoodCategory foodCategory, int id) {
        foodCategory.setId(id);
        categoryRepository.save(foodCategory);
        return new ResponseEntity<>("updated",HttpStatus.OK);

    }
    public ResponseEntity<Object> updateProduct(Product product, int id) {
        Product oldProduct = productRepository.findById(id);
        if(product.getFoodCategory() == null)
            product.setFoodCategory(oldProduct.getFoodCategory());
        product.setId(id);
        productRepository.save(product);
        return new ResponseEntity<>("updated",HttpStatus.OK);

    }
    public ResponseEntity<Object> deleteCategory(int id) {
        FoodCategory foodCategory= categoryRepository.findById(id);
        categoryRepository.delete(foodCategory);
        return new ResponseEntity<>("deleted",HttpStatus.OK);

    }

    public ResponseEntity<Object> deleteProduct(int id) {
        Product product= productRepository.findById(id);
        productRepository.delete(product);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    public List<Product> getLatestProducts() {
        List<Product> products = productRepository.findAll(new Sort(Sort.Direction.DESC,"added"));
        int end = 7;
        if (products.size()<7)
            end = products.size()-1;

        return  products.subList(0,end);
    }
}
