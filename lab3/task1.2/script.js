// --- Product data ---
const products = [
  { id: 1, name: "Laptop", price: 999.99, image: "💻" },
  { id: 2, name: "Smartphone", price: 699.99, image: "📱" },
  { id: 3, name: "Headphones", price: 199.99, image: "🎧" },
  { id: 4, name: "Smartwatch", price: 299.99, image: "⌚" },
];

// --- Shopping cart array ---
let cart = [];

// ✅ Add product to cart or increase quantity
function addToCart(productId) {
  const product = products.find((p) => p.id === productId);
  const existingItem = cart.find((item) => item.id === productId);

  if (existingItem) {
    existingItem.quantity++;
  } else {
    cart.push({ ...product, quantity: 1 });
  }

  renderCart();
}

// ✅ Remove item completely from cart
function removeFromCart(itemId) {
  cart = cart.filter((item) => item.id !== itemId);
  renderCart();
}

// ✅ Update quantity (+1 or -1)
function updateQuantity(itemId, change) {
  const item = cart.find((i) => i.id === itemId);
  if (!item) return;

  item.quantity += change;

  if (item.quantity <= 0) {
    removeFromCart(itemId);
  } else {
    renderCart();
  }
}

// ✅ Calculate total price
function calculateTotal() {
  return cart
    .reduce((total, item) => total + item.price * item.quantity, 0)
    .toFixed(2);
}

// ✅ Render products
function renderProducts() {
  const grid = document.getElementById("productsGrid");
  grid.innerHTML = "";

  products.forEach((p) => {
    const div = document.createElement("div");
    div.className = "product-card";
    div.innerHTML = `
      <div class="product-image">${p.image}</div>
      <div class="product-name">${p.name}</div>
      <div class="product-price">$${p.price.toFixed(2)}</div>
      <button class="add-to-cart-btn" onclick="addToCart(${
        p.id
      })">Add to Cart</button>
    `;
    grid.appendChild(div);
  });
}

// ✅ Render cart display
function renderCart() {
  const cartItems = document.getElementById("cartItems");
  const cartTotal = document.getElementById("cartTotal");
  const cartCount = document.getElementById("cartCount");
  cartItems.innerHTML = "";

  if (cart.length === 0) {
    cartItems.innerHTML = "<p>Your cart is empty 🛒</p>";
  } else {
    cart.forEach((item) => {
      const div = document.createElement("div");
      div.className = "cart-item";
      div.innerHTML = `
        <div>
          ${item.image} <strong>${item.name}</strong> - $${item.price.toFixed(
        2
      )}
        </div>
        <div class="quantity-controls">
          <button onclick="updateQuantity(${item.id}, -1)">-</button>
          <span>${item.quantity}</span>
          <button onclick="updateQuantity(${item.id}, 1)">+</button>
          <button onclick="removeFromCart(${
            item.id
          })" style="color:red;border:none;background:none;cursor:pointer;">Remove</button>
        </div>
      `;
      cartItems.appendChild(div);
    });
  }

  // Update total and cart icon count
  cartTotal.textContent = calculateTotal();
  cartCount.textContent = cart.reduce((sum, i) => sum + i.quantity, 0);
}

// ✅ Toggle cart visibility
function toggleCart() {
  const section = document.getElementById("cartSection");
  section.style.display =
    section.style.display === "none" || section.style.display === ""
      ? "block"
      : "none";
}

// ✅ Initialize
renderProducts();
renderCart();
