package com.distributed.prject.distributedweb.controller;

import com.distributed.prject.distributedweb.model.Coupon;
import com.distributed.prject.distributedweb.model.FoodCategory;
import com.distributed.prject.distributedweb.model.User;
import com.distributed.prject.distributedweb.repository.CategoryRepository;
import com.distributed.prject.distributedweb.repository.CouponRepository;
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
@Api(description = "user com.distributed.prject.distributedweb.controller for mobile app")
@RequestMapping(value = "/api/v1/category")
@RestController

public class CategoryController {

    @Autowired
    UserService userService;
    @Autowired
    CategoryRepository u;

    @Autowired
    UserAuthorizationService userAuthorizationService;
    @ApiMethod(description = "create a food Category API ")
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> createCategory(@RequestBody @Valid FoodCategory category   ) {


        u.save(category);
        return new ResponseEntity<>("Created " , HttpStatus.CREATED);
    }


    @ApiMethod(description = "add a product to a category API ")
    @RequestMapping(value = "/{productId}/{categoryId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> addProductToCategory(@ApiParam(name="productId") int productId, @ApiParam(name="categoryId") int categoryId   ) {

        return userService.addProductToCategory(productId,categoryId);
    }


    @ApiMethod(description = "update category ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> updateFoodCategory(@RequestBody @Valid FoodCategory foodCategory , @ApiParam(name="id") int id) {

        return userService.updateFoodCategory(foodCategory,id);
    }

    @ApiMethod(description = "delete food category")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> deleteCategory( @ApiParam(name="id") int id) {

        return userService.deleteCategory(id);
    }

    @ApiMethod(description = "return all Categories")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FoodCategory> getFoodCategories(@ApiParam(name="userId") int userId) {

        return u.findAll();
    }
}
