INSERT INTO categories (name) VALUES ('Eletronics');
INSERT INTO categories (name) VALUES ('Books');
INSERT INTO categories (name) VALUES ('Jewelry');

INSERT INTO products (name,price,description,img_url) VALUES ('Mistborn', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Macbook', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Playstation', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Bike a', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Bike b', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Bike c', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Nocturne star ring silver', 380.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Nocturne star ring gold', 1470.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Aurora Skies ring', 4560.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Dreamer charm silver', 600.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Sweet nothing gold', 1000.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Sweet nothing gold choker', 870.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Dreamer charm gold', 2000.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Spear', 460.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow1', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow2', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow3', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow4', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow5', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow6', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow7', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow8', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow9', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow10', 100.0, 'Product description', 'http://Pinterest.com');
INSERT INTO products (name,price,description,img_url) VALUES ('Lovesickbow11', 100.0, 'Product description', 'http://Pinterest.com');


INSERT INTO product_category (product_id, category_id) VALUES (1, 2);
INSERT INTO product_category (product_id, category_id) VALUES (2, 1);
INSERT INTO product_category (product_id, category_id) VALUES (3, 1);
INSERT INTO product_category (product_id, category_id) VALUES (4, 3);
INSERT INTO product_category (product_id, category_id) VALUES (4, 2);
INSERT INTO product_category (product_id, category_id) VALUES (5, 2);
INSERT INTO product_category (product_id, category_id) VALUES (6, 3);
INSERT INTO product_category (product_id, category_id) VALUES (7, 3);
INSERT INTO product_category (product_id, category_id) VALUES (8, 3);
INSERT INTO product_category (product_id, category_id) VALUES (9, 3);
INSERT INTO product_category (product_id, category_id) VALUES (10, 3);
INSERT INTO product_category (product_id, category_id) VALUES (11, 3);
INSERT INTO product_category (product_id, category_id) VALUES (12, 3);
INSERT INTO product_category (product_id, category_id) VALUES (13, 3);
INSERT INTO product_category (product_id, category_id) VALUES (14, 3);
INSERT INTO product_category (product_id, category_id) VALUES (15, 3);
INSERT INTO product_category (product_id, category_id) VALUES (16, 3);
INSERT INTO product_category (product_id, category_id) VALUES (17, 3);
INSERT INTO product_category (product_id, category_id) VALUES (18, 3);
INSERT INTO product_category (product_id, category_id) VALUES (19, 3);
INSERT INTO product_category (product_id, category_id) VALUES (20, 3);
INSERT INTO product_category (product_id, category_id) VALUES (21, 3);
INSERT INTO product_category (product_id, category_id) VALUES (22, 3);
INSERT INTO product_category (product_id, category_id) VALUES (23, 3);
INSERT INTO product_category (product_id, category_id) VALUES (24, 3);
INSERT INTO product_category (product_id, category_id) VALUES (25, 3);

INSERT INTO users (name,email,phone,birth_date) VALUES ('Margit', 'Margit@gmail.com', '91234-5678','1958-01-31');
INSERT INTO users (name,email,phone,birth_date) VALUES ('Radahn', 'Radahn@gmail.com', '91234-5678','1966-07-09');
INSERT INTO users (name,email,phone,birth_date) VALUES ('Malenia', 'bladeOfMiquella@gmail.com', '91234-5678','1966-07-09');
INSERT INTO users (name,email,phone,birth_date) VALUES ('Marika', 'Marika@gmail.com', '91234-5678','1910-10-23');
INSERT INTO users (name,email,phone,birth_date) VALUES ('Messmer', 'TheImpaler@gmail.com', '91234-5678','1966-07-09');

INSERT INTO orders (moment,status,client_id) VALUES ('2025-02-1T13:00:00Z', 2, 5);
INSERT INTO orders (moment,status,client_id) VALUES ('2025-02-1T13:00:00Z', 1, 3);
INSERT INTO orders (moment,status,client_id) VALUES ('2025-02-1T13:00:00Z', 1, 5);
INSERT INTO orders (moment,status,client_id) VALUES ('2025-02-1T13:00:00Z', 2, 2);

INSERT INTO order_items (price,quantity,order_id,product_id) VALUES (9120.0,2,2,9);
INSERT INTO order_items (price,quantity,order_id,product_id) VALUES (4000.0,1,2,13);
INSERT INTO order_items (price,quantity,order_id,product_id) VALUES (100.0,1,4,4);
INSERT INTO order_items (price,quantity,order_id,product_id) VALUES (920.0,2,3,14);

INSERT INTO payments (moment,payment_method,order_id) VALUES (TIMESTAMP WITH TIME ZONE '2025-02-06T15:00:00Z',2,3);
INSERT INTO payments (moment,payment_method,order_id) VALUES (TIMESTAMP WITH TIME ZONE '2025-02-06T15:00:00Z',3,2);
INSERT INTO payments (moment,payment_method,order_id) VALUES (TIMESTAMP WITH TIME ZONE '2025-02-06T15:00:00Z',1,4);
