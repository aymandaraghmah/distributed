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
import org.jsondoc.core.annotation.ApiMethod;
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
    public ResponseEntity<Object> signIn(@ApiParam(name="userId") int userId,@ApiParam(name="couponId") int couponId   ) {

        return userService.addCouponToUser(couponId,userId);
    }


    @ApiMethod(description = "update coupon ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> updateCoupon(@RequestBody @Valid Coupon coupon , @ApiParam(name="id") int id) {

        return userService.updateCoupon(coupon,id);
    }

    @ApiMethod(description = "delete coupon")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> deleterCoupon( @ApiParam(name="id") int id) {

        return userService.deleterCoupon(id);
    }

    @ApiMethod(description = "return all coupons for a user")
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Coupon> getUerCoupons(@ApiParam(name="userId") int userId) {

        return u.findUserCoupons(userId);
    }


}
