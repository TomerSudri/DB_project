# E-Commerce System – Java + PostgreSQL

## Overview

This is a console-based e-commerce system built in Java, connected to a PostgreSQL database using JDBC.  
The system supports three user roles:

- **Admin** (code-only): manages users, products, and reports
- **Seller**: registers, logs in, adds and manages products
- **Buyer**: registers, logs in, browses products, adds to cart, and places orders

All actions are saved and retrieved from the database in real time.

## System Design

### Core Classes

- `User` – abstract superclass for `Buyer` and `Seller`  
- `Buyer` / `Seller` – extend `User` with specific features  
- `Product`, `Cart`, `Order` – represent core entities  
- `ECommerceSystem` – main controller that manages user actions, login/signup, product management, orders, etc.  
- `DBUtil` – provides a single method `connect()` to establish JDBC connection  

### Architecture Notes

- Uses **object-oriented design** with inheritance (`User` → `Buyer` / `Seller`)
- Central logic is separated from input/output
- Uses `Map<String, Buyer>` and `Map<String, Seller>` for efficient user access
- Clear separation of responsibilities: Buyers, Sellers, and Admins each have access only to their permitted actions
- Logical access control is enforced through menu structure and method-level restrictions

## Database Integration

### Technologies

- PostgreSQL relational database
- JDBC used to connect from Java

### Key Features

- `ON DELETE CASCADE` on key foreign keys for clean deletions
- Trigger to automatically delete `Buyer` or `Seller` when a `User` is deleted
- Data validation trigger to prevent negative prices or packaging cost
- Index on product category for faster queries

### Main Tables and Relations

- `Users` (userId, username, password, userType)  
- `Buyers`, `Sellers` extend `Users` (1:1)  
- `Products` belong to `Sellers` and `Categories`  
- `Carts` and `Orders` link buyers and products via M:N relations (`CartProduct`, `OrderProduct`)  

## How to Run

1. Compile and run `Main.java` in your IDE (e.g., IntelliJ)
2. Ensure PostgreSQL is running and the correct schema is set up
3. Edit database credentials inside `DBUtil.java`
4. The console interface will guide user interactions

## Notes

- Admin (via code) can delete any buyer or seller.  
  When deleted, all associated rows in other tables (e.g., orders, cart entries, products) are also deleted automatically thanks to `ON DELETE CASCADE`.
- The system can be extended to support GUI, additional features, and advanced analytics.
