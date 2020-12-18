package com.example.inuapp.models;

public class Products {
    private String productId;
    public static final String PRODUCT_ID = "productId";
    private String productCategory;
    public static final String PRODUCT_CATEGORY = "productCategory";
    private String productName;
    public static final String PRODUCT_NAME = "productName";

    private String productImageUrl;
    public static final String PRODUCT_IMAGE_URL = "productImageUrl";
    private Double productMarketPricePerUnit;
    public static final String PRODUCT_MP = "productMarketPricePerUnit";
    private String productDescription;
    public static final String PRODUCT_DESCRIPTION = "productDescription";

    private String productUnit;
    public static final String PRODUCT_UNIT = "productUnit";
    private Double productSellingPricePerUnit;
    public static final String PRODUCT_SP = "productSellingPricePerUnit";
    private String postedAt;
    public static final String POSTED_AT = "postedAt";
    private String productCount;
    public static final String PRODUCT_COUNT = "productCount";

    private int productSellCount;
    public static final String PRODUCT_SELL_COUNT = "productSellCount";

    public static final String ELECTRONICS = "Electronics";
    public static final String HOME = "Home";
    public static final String GAMING = "Gaming";
    public static final String FASHION = "Fashion";
    public static final String COMPUTING = "Computing";
    public static final String SPORTING = "Sporting";

    public static final String WEIGHT = "Weight";
    public static final String LITRES = "Litres";
    public static final String COUNT = "Count";

    public static final String PRODUCT_IMAGES = "Product Images";

    public static final String PRODUCT_SUR = "PD";
    public static final String NO_IMAGE = "https://techweez.com/wp-content/uploads/2017/07/NO-IMAGE.png";
    public static final String PRODUCTS = "Products";
    public static final String SHOP = "Shop";


    public Products() {
    }

    public Products(String productId, String productName, String productImageUrl, Double productSellingPricePerUnit, String productCount) {
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.productSellingPricePerUnit = productSellingPricePerUnit;
        this.productCount = productCount;
    }

    public Products(String productName) {
        this.productName = productName;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public Double getProductMarketPricePerUnit() {
        return productMarketPricePerUnit;
    }

    public void setProductMarketPricePerUnit(Double productMarketPricePerUnit) {
        this.productMarketPricePerUnit = productMarketPricePerUnit;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public Double getProductSellingPricePerUnit() {
        return productSellingPricePerUnit;
    }

    public void setProductSellingPricePerUnit(Double productSellingPricePerUnit) {
        this.productSellingPricePerUnit = productSellingPricePerUnit;
    }

    public String getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(String postedAt) {
        this.postedAt = postedAt;
    }

    public int getProductSellCount() {
        return productSellCount;
    }

    public void setProductSellCount(int productSellCount) {
        this.productSellCount = productSellCount;
    }
}
