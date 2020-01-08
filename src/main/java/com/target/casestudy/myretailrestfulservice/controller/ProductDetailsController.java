package com.target.casestudy.myretailrestfulservice.controller;

import com.target.casestudy.myretailrestfulservice.model.ProductDetails;
import com.target.casestudy.myretailrestfulservice.service.ProductDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/")
public class ProductDetailsController {

	@Autowired
	@Qualifier(value="productDetailService")
	ProductDetailsService productDetailsService;
	
	
	@RequestMapping(value="product/{id}",method= RequestMethod.GET)
	public ProductDetails getProductDetails(@PathVariable int id) throws HttpClientErrorException, IOException, URISyntaxException {
		log.info("in controller getProductDetails id :"+id);
		ProductDetails prodDetails=null;
		prodDetails=productDetailsService.getProductDetails(id);
		log.info(" return productDetails :"+prodDetails);
		return prodDetails;
	}
	
	@RequestMapping(value="product/{id}",method= RequestMethod.PUT)
	public ProductDetails putProductDetails(@PathVariable int id, @RequestBody ProductDetails prodDetails) throws Exception{
		log.info("in controller putProductDetails id :"+id);
		log.info("in controller putProductDetails requestBody :"+prodDetails);
		ProductDetails updatedProductDetails=productDetailsService.putProductDetails(id, prodDetails);
		log.info(" updated putProductDetails :"+updatedProductDetails);
		return updatedProductDetails;
	}
}
