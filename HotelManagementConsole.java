import java.util.*;

class Customer {
    int id;
    String name;
    String address;
    String inDate;
    String outDate;
    String roomType;  // new column

    Customer(int id, String name, String address, String inDate, String outDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.inDate = inDate;
        this.outDate = outDate;
        this.roomType = "Not Assigned"; // default
    }

    public String toString() {
        return String.format("%-5d %-15s %-20s %-12s %-12s %-20s",
                id, name, address, inDate, outDate, roomType);
    }
}

public class HotelManagementConsole {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Customer> customers = new ArrayList<>();
    static int nextId = 1; // customer ID counter

    // Register customer
    
    public static void registerCustomer() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter Location: ");
        String addr = sc.nextLine();
        System.out.print("Enter check-in date (YYYY/MM/DD): ");
        String indate = sc.nextLine();
        System.out.print("Enter check-out date (YYYY/MM/DD): ");
        String outdate = sc.nextLine();

        customers.add(new Customer(nextId++, name, addr, indate, outdate));
        System.out.println("Customer registered successfully!\n");
    }

    // Room rent
    public static void roomRent() {
        if (customers.isEmpty()) {
            System.out.println("No customer found! Please register first.\n");
            return;
        }

        System.out.println("We have the following rooms for you:");
        System.out.println("1. Classic Room         ---> Rs 1500 P/N (Single Bed, Non A/C, Basic Room Service)");
        System.out.println("2. Deluxe Room          ---> Rs 2800 P/N (Single Bed, A/C, Basic Room Service + Free Wi-Fi)");
        System.out.println("3. Executive Room       ---> Rs 3500 P/N (Double Bed, Non A/C, Basic Room Service + TV, Fan, Free Wi-Fi)");
        System.out.println("4. Luxury Room          ---> Rs 5000 P/N (Double Bed, A/C, Free Wi-Fi, Room Service 24/7, Mini Fridge)");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        System.out.print("For how many nights: ");
        int nights = sc.nextInt();
        sc.nextLine(); // clear buffer

        int rate = 0;
        String roomType = "";
        switch (choice) {
            case 1: rate = 1500; roomType = "Classic Room"; break;
            case 2: rate = 2800; roomType = "Deluxe Room"; break;
            case 3: rate = 3500; roomType = "Executive Room"; break;
            case 4: rate = 5000; roomType = "Luxury Room"; break;
            default: System.out.println("Invalid choice!");
        }

        int total = rate * nights;
        System.out.println("Your room rent is = Rs " + total + "\n");

        // Assign room type to the latest registered customer
        if (!roomType.isEmpty()) {
            customers.get(customers.size() - 1).roomType = roomType;
        }
    }

    // Restaurant menu
    public static void restaurantMenu() {
        System.out.println("Available items:");
        System.out.println("1. Tea (Rs 15)");
        System.out.println("2. Coffee (Rs 20)");
        System.out.println("3. Milk (Rs 13)");
        System.out.println("4. Soft drinks (Rs 60)");
        System.out.println("5. Waffle (Rs 100)");
        System.out.println("6. Sandwich (Rs 70)");
        System.out.println("7. Ice Cream (Rs 75)");
        System.out.println("8. Biryani (Rs 250)");
        System.out.println("9. Noodles (Rs 50)");
        System.out.println("10. Cheese Macaroni Pasta (Rs 80)");
    }

    // Order multiple foods and calculate bill (merge repeated items)
    public static void orderFood() {
        ArrayList<String> foodNames = new ArrayList<>(Arrays.asList(
            "Tea","Coffee","Milk","Soft drinks","Waffle","Sandwich","Ice Cream","Biryani","Noodles","Cheese Macaroni Pasta"));
        ArrayList<Integer> foodPrices = new ArrayList<>(Arrays.asList(
            15,20,13,60,100,70,75,250,50,80));

        Map<String, Integer> orderMap = new LinkedHashMap<>();
        int grandTotal = 0;

        restaurantMenu();
        System.out.println("Enter food number (0 to finish ordering):");

        while (true) {
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            if (choice == 0) break;
            if (choice < 1 || choice > 10) {
                System.out.println("Invalid choice! Try again.");
                continue;
            }
            System.out.print("Enter Quantity: ");
            int qty = sc.nextInt();
            sc.nextLine(); // clear buffer

            String food = foodNames.get(choice - 1);
            orderMap.put(food, orderMap.getOrDefault(food, 0) + qty);
            System.out.println(food + " added to Order.\n");
        }

        if (!orderMap.isEmpty()) {
            System.out.println("\n--- Food Bill ---");
            System.out.printf("%-25s %-10s %-10s %-10s\n", "Food Item", "Price", "Quantity", "Total");

            for (String food : orderMap.keySet()) {
                int price = foodPrices.get(foodNames.indexOf(food));
                int qty = orderMap.get(food);
                int total = price * qty;
                grandTotal += total;

                System.out.printf("%-25s %-10d %-10d %-10d\n", food, price, qty, total);
            }
            System.out.println("-------------------------------------------");
            System.out.println("Grand Total: Rs " + grandTotal + "\n");
        } else {
            System.out.println("No food ordered.\n");
        }
    }

    // Laundry bill
    public static void laundryBill() {
        System.out.print("Enter number of clothes: ");
        int clothes = sc.nextInt();
        sc.nextLine(); // clear buffer
        int bill = clothes * 15;
        System.out.println("Your Laundry Bill = Rs " + bill + "\n");
    }

    // View customer details in table format
    public static void viewCustomerDetails() {
        if(customers.isEmpty()) {
            System.out.println("No customer records found.\n");
            return;
        }

        System.out.printf("%-5s %-15s %-20s %-12s %-12s %-20s\n",
                "ID", "Name", "Location", "Check-in", "Check-out", "Room Type");
        System.out.println("-------------------------------------------------------------------------------");

        for(Customer c : customers) {
            System.out.println(c);
        }
        System.out.println();
    }

    // Menu system
    public static void menuSet() {
        int choice;
        do {
            System.out.println("\n--- Hotel Management System ---");
            System.out.println("1. Enter Customer Data");
            System.out.println("2. Calculate Room Bill");
            System.out.println("3. View Restaurant Menu");
            System.out.println("4. Order Food & Calculate Bill");
            System.out.println("5. Laundry Bill");
            System.out.println("6. View Customer Details");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1: registerCustomer(); break;
                case 2: roomRent(); break;
                case 3: restaurantMenu(); break;
                case 4: orderFood(); break;
                case 5: laundryBill(); break;
                case 6: viewCustomerDetails(); break;
                case 7: System.out.println("Thanks for visiting!"); break;
                default: System.out.println("Invalid choice!"); 
            }
        } while (choice != 7);
    }

    public static void main(String[] args) {
        menuSet();
    }
}
