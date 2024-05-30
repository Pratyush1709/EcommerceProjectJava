import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // @Override
    public String toString() {
        return name + " ($" + price + ")";
    }
}

class Cart {
    private List<Product> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        items.add(product);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total;
    }

    // @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Cart Items:\n");
        for (Product item : items) {
            sb.append(item).append("\n");
        }
        sb.append("Total Price: $").append(getTotalPrice());
        return sb.toString();
    }
}

class Order {
    private String orderId;
    private Cart cart;
    private Date orderDate;

    public Order(String orderId, Cart cart) {
        this.orderId = orderId;
        this.cart = cart;
        this.orderDate = new Date();
    }

    public String getOrderId() {
        return orderId;
    }

    public Cart getCart() {
        return cart;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    // @Override
    public String toString() {
        return "Order ID: " + orderId + "\n" + cart + "\nOrder Date: " + orderDate;
    }
}

public class ECommerceApplication {
    private JFrame frame;
    private JComboBox<Product> productComboBox;
    private JTextArea cartTextArea;
    private Cart cart;

    public ECommerceApplication() {
        cart = new Cart();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("E-Commerce Application");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        // Sample products
        Product product1 = new Product("P001", "Laptop", 1200.00);
        Product product2 = new Product("P002", "Smartphone", 800.00);
        Product product3 = new Product("P003", "Headphones", 150.00);
        Product[] products = {product1, product2, product3};

        // Product selection panel
        JPanel productPanel = new JPanel();
        frame.getContentPane().add(productPanel, BorderLayout.NORTH);

        JLabel lblSelectProduct = new JLabel("Select Product:");
        productPanel.add(lblSelectProduct);

        productComboBox = new JComboBox<>(products);
        productPanel.add(productComboBox);

        JButton btnAddToCart = new JButton("Add to Cart");
        productPanel.add(btnAddToCart);

        // Cart display panel
        cartTextArea = new JTextArea();
        cartTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cartTextArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Order panel
        JPanel orderPanel = new JPanel();
        frame.getContentPane().add(orderPanel, BorderLayout.SOUTH);

        JButton btnRemoveFromCart = new JButton("Remove from Cart");
        orderPanel.add(btnRemoveFromCart);

        JButton btnPlaceOrder = new JButton("Place Order");
        orderPanel.add(btnPlaceOrder);

        // Add to Cart button action
        btnAddToCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = (Product) productComboBox.getSelectedItem();
                cart.addProduct(selectedProduct);
                updateCartDisplay();
            }
        });

        // Remove from Cart button action
        btnRemoveFromCart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Product selectedProduct = (Product) productComboBox.getSelectedItem();
                cart.removeProduct(selectedProduct);
                updateCartDisplay();
            }
        });

        // Place Order button action
        btnPlaceOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Order order = new Order("O001", cart);
                JOptionPane.showMessageDialog(frame, order.toString(), "Order Placed", JOptionPane.INFORMATION_MESSAGE);
                cart = new Cart();  // Reset cart after order
                updateCartDisplay();
            }
        });
    }

    private void updateCartDisplay() {
        cartTextArea.setText(cart.toString());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ECommerceApplication window = new ECommerceApplication();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
