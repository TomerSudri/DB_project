--create User Table
CREATE TABLE Users (userId SERIAL PRIMARY KEY, username VARCHAR(50) NOT NULL, password VARCHAR(50) NOT NULL);

--create Buyer Table
CREATE TABLE Buyers (BuyerId SERIAL PRIMARY KEY REFERENCES Users(userId),
    address VARCHAR(50),
    orderHistorySize INT);

--create Seller Table
CREATE TABLE Sellers (SellerId INT PRIMARY KEY REFERENCES Users(userId),productCount INT);

--create Product Table
CREATE TABLE Products (serialNumber SERIAL PRIMARY KEY, SellerId INT REFERENCES Sellers(SellerId)
				       ,name VARCHAR(50) NOT NULL, price NUMERIC(10,2) NOT NULL,
				       category_id INT REFERENCES Categories(category_id), specialPackaging BOOLEAN DEFAULT FALSE,
				        packagingCost NUMERIC(10,2));


--create Catagory Table
CREATE TABLE Categories (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(50) UNIQUE NOT NULL);


--create Order Table
CREATE TABLE Orders (
    orderId SERIAL PRIMARY KEY,
    shippingAddress VARCHAR(50) NOT NULL,
    totalPrice NUMERIC(10,2) NOT NULL,
    orderDate DATE NOT NULL,
    BuyerId INT REFERENCES Buyers(BuyerId)
);

--create Cart Table
CREATE Table Cart (CartId SERIAL PRIMARY KEY,
BuyerId INT REFERENCES Buyers(BuyerId));

--create CartProduct Table
CREATE TABLE CartProduct (
    CartId INT REFERENCES Cart(CartId),
    ProductId INT REFERENCES Products(serialNumber),
    PRIMARY KEY (CartId, ProductId)
);

--create OrderProduct
CREATE TABLE OrderProduct (
    orderId INT REFERENCES Orders(orderId),
    ProductId INT REFERENCES Products(serialNumber),
    PRIMARY KEY (orderId, ProductId)
);

