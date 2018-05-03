package com.distributed.prject.distributedweb.controller;

import com.distributed.prject.distributedweb.model.Coupon;
import com.distributed.prject.distributedweb.model.User;
import com.distributed.prject.distributedweb.repository.CouponRepository;
import com.distributed.prject.distributedweb.repository.UserRepository;
import com.distributed.prject.distributedweb.requests.LoginRequest;
import com.distributed.prject.distributedweb.security.UserAuthorizationService;
import com.distributed.prject.distributedweb.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.bytebuddy.asm.Advice;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(description = "coupon com.distributed.prject.distributedweb.controller for mobile app")
@RequestMapping(value = "/api/v1/coupon")
@RestController
public class CouponController {
    @Autowired
    UserService userService;
    @Autowired
    CouponRepository u;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthorizationService userAuthorizationService;
    @ApiMethod(description = "create a coupon API ")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> signUp(@RequestBody @Valid Coupon coupon   ) {


                u.save(coupon);
        return new ResponseEntity<>("Created " , HttpStatus.CREATED);
    }


    @ApiMethod(description = "add a coupon to an employee API ")
    @RequestMapping(value = "/{userId}/{couponId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> signIn(@ApiPathParam(name = "userId", description = "user ID") @PathVariable("userId") int userId,  @ApiPathParam(name = "couponId", description = "couponId ID") @PathVariable("couponId") int couponId   ) {

        return userService.addCouponToUser(couponId,userId);
    }


    @ApiMethod(description = "update coupon ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> updateCoupon(@RequestBody @Valid Coupon coupon ,@ApiPathParam(name = "id", description = "coupon ID") @PathVariable("id") int id) {

        return userService.updateCoupon(coupon,id);
    }

    @ApiMethod(description = "delete coupon")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> deleterCoupon( @ApiPathParam(name = "id", description = "coupon ID") @PathVariable("id") int id) {

        return userService.deleterCoupon(id);
    }

    @ApiMethod(description = "return all coupons for a user")
    @RequestMapping(value = "user-coupons/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Coupon> getUerCoupons(@ApiPathParam(name = "userId", description = "user ID") @PathVariable("userId") int userId) {
User user = userRepository.findById(userId);
        return user.getCoupons();
    }


}
