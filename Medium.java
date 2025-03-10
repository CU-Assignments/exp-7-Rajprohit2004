import sqlite3

# Connect to SQLite database (or create it)
def connect_to_db():
    conn = sqlite3.connect('product_db.db')
    return conn

# Create Product table if it doesn't exist
def create_table():
    conn = connect_to_db()
    cursor = conn.cursor()
    cursor.execute('''CREATE TABLE IF NOT EXISTS Product (
                        ProductID INTEGER PRIMARY KEY,
                        ProductName TEXT NOT NULL,
                        Price REAL NOT NULL,
                        Quantity INTEGER NOT NULL
                    )''')
    conn.commit()
    conn.close()

# Create a new product in the database
def create_product(product_name, price, quantity):
    conn = connect_to_db()
    cursor = conn.cursor()
    cursor.execute("INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)",
                   (product_name, price, quantity))
    conn.commit()
    conn.close()
    print("Product created successfully!")

# Read and display all products
def read_all_products():
    conn = connect_to_db()
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM Product")
    products = cursor.fetchall()
    
    if not products:
        print("No products found.")
    else:
        for product in products:
            print(f"ID: {product[0]}, Name: {product[1]}, Price: {product[2]}, Quantity: {product[3]}")
    
    conn.close()

# Update a product by ProductID
def update_product(product_id, product_name, price, quantity):
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        cursor.execute("BEGIN TRANSACTION;")
        cursor.execute("UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?",
                       (product_name, price, quantity, product_id))
        conn.commit()
        print("Product updated successfully!")
    except Exception as e:
        conn.rollback()
        print(f"An error occurred: {e}")
    finally:
        conn.close()

# Delete a product by ProductID
def delete_product(product_id):
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        cursor.execute("BEGIN TRANSACTION;")
        cursor.execute("DELETE FROM Product WHERE ProductID = ?", (product_id,))
        conn.commit()
        print("Product deleted successfully!")
    except Exception as e:
        conn.rollback()
        print(f"An error occurred: {e}")
    finally:
        conn.close()

# Menu-driven interface
def menu():
    create_table()  # Ensure table is created on startup
    
    while True:
        print("\n--- Product Management Menu ---")
        print("1. Create Product")
        print("2. Read All Products")
        print("3. Update Product")
        print("4. Delete Product")
        print("5. Exit")
        
        choice = input("Enter your choice: ")

        if choice == '1':
            product_name = input("Enter product name: ")
            price = float(input("Enter product price: "))
            quantity = int(input("Enter product quantity: "))
            create_product(product_name, price, quantity)
        
        elif choice == '2':
            read_all_products()
        
        elif choice == '3':
            product_id = int(input("Enter product ID to update: "))
            product_name = input("Enter new product name: ")
            price = float(input("Enter new product price: "))
            quantity = int(input("Enter new product quantity: "))
            update_product(product_id, product_name, price, quantity)
        
        elif choice == '4':
            product_id = int(input("Enter product ID to delete: "))
            delete_product(product_id)
        
        elif choice == '5':
            print("Exiting...")
            break
        
        else:
            print("Invalid choice! Please try again.")

if __name__ == "__main__":
    menu()
