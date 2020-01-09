package com.target.casestudy.myretailrestfulservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.casestudy.myretailrestfulservice.exception.ResourceNotFoundException;
import com.target.casestudy.myretailrestfulservice.model.ProductDetails;
import com.target.casestudy.myretailrestfulservice.model.ProductPrice;
import com.target.casestudy.myretailrestfulservice.repository.ProductPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

@Slf4j
@Service(value="productDetailService")
@Scope(proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ProductDetailsServiceImpl implements ProductDetailsService {

	@Autowired
	private ProductPriceRepository productPriceRepository;
	@Autowired
	private ProductDetailsServiceImpl productDetailsServiceImpl;
	@Autowired
	private Environment env;
	
	
	@Override
	public ProductDetails getProductDetails(int id) throws IOException, ResourceNotFoundException {
		log.info("in  getProductDetails ");
		log.debug("id: "+id);
		String productName=getProductName(id);
		log.debug("productName: "+productName);
		Optional<ProductPrice> prodPrice= Optional.ofNullable(productDetailsServiceImpl.getProductPrice(id));
		ProductPrice productPrice = null;
		if(prodPrice.isPresent()){
			productPrice = prodPrice.get();
		}
		ProductDetails prodDetails= new ProductDetails(id,productName,productPrice);
		log.debug("prodDetails: "+prodDetails);
		return prodDetails;
	}

	@Override
	public ProductDetails putProductDetails(int id, ProductDetails newProduct) throws Exception{
		log.info("inside putProductDetails");
		log.debug(" newProduct : "+newProduct);
		if(id!=newProduct.getId()){
			throw new Exception(" Product price cannot be updated, request body json should have matching id with path variable.");
		}
		ProductPrice newProductPrice=newProduct.getProductPrice();
		if(newProduct.getProductPrice().getCurrencyCode()==null||newProduct.getProductPrice().getPrice()==null){
				throw new Exception(" Please check product price and currency code details, it should not be empty ");
		}
		newProductPrice.setId(id);
		String productName=getProductName(id);
		newProduct.setName(productName);
		newProductPrice=productDetailsServiceImpl.updateProductPrice(id,newProduct);
		
		newProduct.setProductPrice(newProductPrice);
		return newProduct;
	}
	
	@Cacheable(value = "productPriceCache", key = "#id", unless="#result == null")
	public ProductPrice getProductPrice(int id){
		log.info("inside getProductPrice");
		log.debug("id : "+id);
		ProductPrice prodPrice=productPriceRepository.findById(id);
		log.debug("prodPrice : "+prodPrice);
		return prodPrice;
	}
	
	
	@CachePut(value = "productPriceCache", key = "#id", unless="#result == null")
	public ProductPrice updateProductPrice(int id,ProductDetails newProduct){
		log.info("inside updateProductPrice");
		ProductPrice newProductPrice=newProduct.getProductPrice();
		newProductPrice.setId(id);
		if(productPriceRepository.findById(newProductPrice.getId())!=null){
			newProductPrice=productPriceRepository.save(newProduct.getProductPrice());
		}else{
			log.error("price detail null");
		}
		log.debug("newProductPrice : "+newProductPrice);
		return newProductPrice;
	}
	
	private String getProductName(int id) throws IOException, ResourceNotFoundException {
		log.info("in getProductName");
		URL url = new URL(env.getProperty("target.restUrl1")+id+env.getProperty("target.restUrl2"));
		URLConnection conn = url.openConnection();
		String redirect = conn.getHeaderField("Location");
		if (redirect != null){
			conn = new URL(redirect).openConnection();
		}
		try(BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())))) {
			String output;
			log.debug("Output from Server ....");
			StringBuffer sb = new StringBuffer();
			while ((output = br.readLine()) != null) {
				log.info(output);
				sb.append(output);
			}

			ObjectMapper mapper = new ObjectMapper();
			String productName = "";
			try {
				JsonNode root = null;
				String jsonString = sb.toString();
				if (jsonString != null || !"".equals(jsonString)) {
					root = mapper.readTree(jsonString);
					if (root.findValue("product") != null) {
						root = root.findValue("product");
						if (root.findValue("item") != null) {
							root = root.findValue("item");
							if (root.findValue("product_description") != null) {
								root = root.findValue("product_description");
								if (root.findValue("title") != null) {
									productName = root.findValue("title").asText();
								}
							}
						}
					}
				}
				log.debug("productName : " + productName);
			} catch (IOException e) {
				log.error("Parsing failed IOException " + e.getMessage());
				throw new IOException(e.getMessage());
			}
			return productName;
		}catch(Exception exception){
			log.error("Exception occured:{}",exception);
			throw new ResourceNotFoundException(exception.getMessage());
		}
	}

}
