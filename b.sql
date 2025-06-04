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


