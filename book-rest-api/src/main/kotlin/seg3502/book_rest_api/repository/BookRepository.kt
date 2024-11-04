package seg3x02.booksrestapi.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import seg3x02.booksrestapi.entities.Book

interface BookRepository : CrudRepository<Book, Long> {
    fun findByTitleContaining(title: String): List<Book>
    fun findByCategory(category: String): List<Book>

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    fun findByAuthorId(authorId: Long): List<Book>

    fun countByIdIsNotNull(): Long
}
