# DDClothsStore
This is a sample online shopping app with certain basic features like browsing the catalog of products and adding them to wishlist or cart. Products name,category, discounted and actual price and stock information are displayed to the user. Products and cart list are fetched from API(Retrofit and GSON libraries are used) 

Cart:
Products in cart will be pulled from API
  1. Products that are in stock can be added to the cart.
  2. Products in cart can be removed from the cart.
  3. Total cart amount will be calculated and displayed.

Wishlist:
Wishlist is local to the device and stored in database for easy retrieval.
  1. Products can be added to wishlist even if they are out of stock.
  2. Products from wishlist can be moved to cart if there are in stock.
