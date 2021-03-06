package com.target.casestudy.myretailrestfulservice.service;

import com.target.casestudy.myretailrestfulservice.exception.ResourceNotFoundException;
import com.target.casestudy.myretailrestfulservice.model.ProductDetails;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.URISyntaxException;


public interface ProductDetailsService {

	ProductDetails getProductDetails(int id) throws HttpClientErrorException, IOException, URISyntaxException, ResourceNotFoundException;
	ProductDetails putProductDetails(int id, ProductDetails newProduct) throws Exception;
}
