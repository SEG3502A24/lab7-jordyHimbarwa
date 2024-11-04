package seg3x02.booksrestapi.repository

import org.springframework.data.repository.CrudRepository
import seg3x02.booksrestapi.entities.Order

interface OrderRepository : CrudRepository<Order, Long> {
    // You can add custom query methods here if needed

    // Example: Find orders by book ID
    fun findByBookId(bookId: Long): List<Order>

    // Example: Find all orders sorted by order date
    // fun findAllByOrderByOrderDateAsc(): List<Order>
}
