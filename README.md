# README #

As a <<user role>>, I want to <<specific requirement>>, so that <<benefit>>.

1. As a customer, I want to have access to point-of-sale view, so that I can purchase products.
2. As a warehouse manager, I want to have access to warehouse view, so that I would be able to add newly arrived products. 
3. As a customer, I want to have access to history view, so that I could see my past purchases.
4. As a customer, I want to manage my products in a shopping cart, so that I could adjust them prior to checkout.
5. As a warehouse worker, I want to be able to get the location of specific products, so that I can access them quickly.
6. As a sales manager, I want to be able to see all sales where a specific product was purchased, so that in case something happens regarding a product I have the tools to deal with it.
7. As a sales manager, I want to be able to see all purchases within a specified time, so that I can see how much money we made. 
8. As a warehouse manager, I want to be able to search specific products in the warehouse, so that I can see how many of those products we currently have.
9. As a warehouse manager, I want to be able to remove specific products from the warehouse, so that unsuitable products can be removed.
10. As a cashier, I want to be able to remove specific items from the shopping cart, so that in case of a mistake the item(s) can be removed.
11. As a cashier, I want to be able to add items to the shopping cart, so that all items can be purchased simultaneously.
12. As a warehouse worker, I want to be able to add items to the warehouse, so that a new shipment of items can be inserted into the system.
13. As a customer, I want to be able to see a list of discounted products, so that I could save money.
14. As a team leader, I want to be able to add or remove team members, so that I could efficiently manage my team.  
15. As a customer I want to be able to see the total sum of the shopping cart, so that I can see how much will I have to pay.
16. As a customer, I want to see a confirmation message after purchase, so that I can be sure that my order went through.
17. As a shop manager, I want the program to be usable by CLI and by GUI, so that in case that the GUI version doesn't work, there is a backup version.
18. As a cashier, I want the CLI version to be very clear and understandable, so that I can service customers quick and easy when I have to use the CLI version.
19. As a shop manager, I want the program to be user friendly, so that it would not scare off customers and employees.
20. As a cashier, I want to be able to edit and change previous orders, so that in case I make a mistake, it would be easy to fix the mistake.

Questions about the system:

1. Under the "point-of-sale" tab the only way to search products seems to be through a dropbox, wouldn't it be better to be able to search using the product name and/or with the product's barcode?
2. What is the purpose of the "Team" tab?
3. What about discounts? There doesn't seem to be any feature for them in the initial product proposal.
4. There doesn't seem to be a good way to filter things, for example under the warehouse tab if you suddenly have thousands of products then finding a specific one is gonna be difficult. Also under the "history" tab there isn't a way to filter by time or anything else. Should we look to add filters?
5. In the "point of sale" tab there doesnt seem to be a way to remove added products seperately, you can only cancel the entire cart.
6. Why is it, that when to be purchased product is added to the shopping cart, it doesn't show the corresponding ID?
7. As shown in the picture, the program does not indicate the maximum available products, when the quantity of this given item is exceeded. Should this information be displayed in the error pop-up window or should the ‘’Amount’’ section work in a way where the limits are shown?
8. In the warehouse view, it is rather vague how the ID system works. It seems, that Green tea is added with Bar Code(ID) 1, but on the other hand, Black tea ID was randomly generated. How are the IDs determined?
9. What are the restrictions for IDs? With an enormous database, this system gives the impression of being a complete disaster. Green tea has two different IDs in two separate pictures, wouldn’t it be easier if products had a fixed ID?
10. If they are randomly generated, can there appear duplicates? How are they fixed?
11. Quantity wise, are there any restrictions on the items in the warehouse?
12. If a product is to be inserted with the wrong data, can it be removed?
13. When a certain product runs out, how will the program notify warehouse manager? Via “Refresh warehouse” button or some other way?
14. Shouldn't there be a separate GUI version for warehouse and POS? For example, why does a warehouse worker have the ability to process orders?

**15. What software is currently being used? (For reference)**