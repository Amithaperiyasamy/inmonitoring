<<<<<<< HEAD
CREATE TABLE qualityproduct(
  id INT AUTO_INCREMENT PRIMARY KEY,
  qualityproduct_name VARCHAR(255) NOT NULL,
  category VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  description TEXT,
  manufacturer VARCHAR(255),
  model_number VARCHAR(255)
);
INSERT INTO qualityproduct(qualityproduct_name, category, quantity, price, description, manufacturer, model_number)
VALUES
  ('Apple iPhone 14', 'Electronics', 100, 999.99, 'A high-end smartphone from Apple.', 'Apple', 'iPhone 14'),
  ('Samsung Galaxy S22', 'Electronics', 50, 899.99, 'A high-end smartphone from Samsung.', 'Samsung', 'Galaxy S22'),
  ('Nike Air Max 270', 'Footwear', 200, 129.99, 'A popular running shoe from Nike.', 'Nike', 'Air Max 270'),
  ('Sony WH-1000XM5', 'Electronics', 150, 349.99, 'A high-end wireless headphone from Sony.', 'Sony', 'WH-1000XM5');
  SELECT * FROM qualityproduct;
  SELECT * FROM qualityproduct WHERE category = 'Electronics';
  SELECT * FROM qualityproduct WHERE quantity > 10;
  SELECT * FROM qualityproduct WHERE price < 5000;
  SELECT * FROM qualityproduct ORDER BY price DESC;
  SELECT * FROM qualityproduct ORDER BY price DESC LIMIT 3;
  SELECT SUM(quantity) AS total_quantity FROM qualityproduct;
  SELECT AVG(price) AS average_price FROM qualityproduct;
  SELECT * FROM qualityproduct ORDER BY price DESC LIMIT 1;





=======
CREATE TABLE qualityproduct(
  id INT AUTO_INCREMENT PRIMARY KEY,
  qualityproduct_name VARCHAR(255) NOT NULL,
  category VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  description TEXT,
  manufacturer VARCHAR(255),
  model_number VARCHAR(255)
);
INSERT INTO qualityproduct(qualityproduct_name, category, quantity, price, description, manufacturer, model_number)
VALUES
  ('Apple iPhone 14', 'Electronics', 100, 999.99, 'A high-end smartphone from Apple.', 'Apple', 'iPhone 14'),
  ('Samsung Galaxy S22', 'Electronics', 50, 899.99, 'A high-end smartphone from Samsung.', 'Samsung', 'Galaxy S22'),
  ('Nike Air Max 270', 'Footwear', 200, 129.99, 'A popular running shoe from Nike.', 'Nike', 'Air Max 270'),
  ('Sony WH-1000XM5', 'Electronics', 150, 349.99, 'A high-end wireless headphone from Sony.', 'Sony', 'WH-1000XM5');
  SELECT * FROM qualityproduct;
  SELECT * FROM qualityproduct WHERE category = 'Electronics';
  SELECT * FROM qualityproduct WHERE quantity > 10;
  SELECT * FROM qualityproduct WHERE price < 5000;
  SELECT * FROM qualityproduct ORDER BY price DESC;
  SELECT * FROM qualityproduct ORDER BY price DESC LIMIT 3;
  SELECT SUM(quantity) AS total_quantity FROM qualityproduct;
  SELECT AVG(price) AS average_price FROM qualityproduct;
  SELECT * FROM qualityproduct ORDER BY price DESC LIMIT 1;





>>>>>>> cbfc11c (WIP: my current changes)
