package seg3x02.booksrestapi.entities

import jakarta.persistence.*

@Entity
@Table(name = "BookOrder")
class Order {
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0

    var quantity: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    lateinit var book: Book

    var orderDate: String = "" // Or use LocalDate for better date handling

    // Optional: You might want to include a reference to a User entity
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id", nullable = false)
    // lateinit var user: User
}
