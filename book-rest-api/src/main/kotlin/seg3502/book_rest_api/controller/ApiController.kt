package seg3x02.booksrestapi.controller

import io.swagger.v3.oas.annotations.Operation
import java.net.URI
import org.springframework.hateoas.CollectionModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import seg3502.booksrestapi.repository.AuthorRepository
import seg3x02.booksrestapi.assemblers.AuthorModelAssembler
import seg3x02.booksrestapi.assemblers.BioModelAssembler
import seg3x02.booksrestapi.assemblers.BookModelAssembler
import seg3x02.booksrestapi.assemblers.OrderModelAssembler
import seg3x02.booksrestapi.entities.Author
import seg3x02.booksrestapi.entities.Bio
import seg3x02.booksrestapi.entities.Book
import seg3x02.booksrestapi.entities.Order
import seg3x02.booksrestapi.repository.BioRepository
import seg3x02.booksrestapi.repository.BookRepository
import seg3x02.booksrestapi.repository.OrderRepository
import seg3x02.booksrestapi.representation.AuthorRepresentation
import seg3x02.booksrestapi.representation.BioRepresentation
import seg3x02.booksrestapi.representation.BookRepresentation
import seg3x02.booksrestapi.representation.OrderRepresentation

@RestController
@CrossOrigin(origins = ["http://localhost:4200"])
@RequestMapping("books-api", produces = ["application/hal+json"])
class ApiController(
        val authorRepository: AuthorRepository,
        val bookRepository: BookRepository,
        val bioRepository: BioRepository,
        val orderRepository: OrderRepository,
        val authorAssembler: AuthorModelAssembler,
        val bookAssembler: BookModelAssembler,
        val orderAssembler: OrderModelAssembler,
        val bioAssembler: BioModelAssembler
) {
    // --------------------- Book Operations --------------------- //

    @Operation(summary = "Get all books")
    @GetMapping("/books")
    fun allBooks(): ResponseEntity<CollectionModel<BookRepresentation>> {
        val books = bookRepository.findAll()
        return ResponseEntity(bookAssembler.toCollectionModel(books), HttpStatus.OK)
    }

    @Operation(summary = "Get a book by id")
    @GetMapping("/books/{id}")
    fun getBookById(@PathVariable("id") id: Long): ResponseEntity<BookRepresentation> {
        return bookRepository
                .findById(id)
                .map { entity: Book -> bookAssembler.toModel(entity) }
                .map { body: BookRepresentation -> ResponseEntity.ok(body) }
                .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Add a new book")
    @PostMapping("/books")
    fun addBook(@RequestBody book: Book): ResponseEntity<Any> {
        return try {
            val newBook = this.bookRepository.save(book)
            val location: URI =
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(newBook.id)
                            .toUri()
            ResponseEntity.created(location).body(bookAssembler.toModel(newBook))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update the information of a book")
    @PutMapping("/books/{id}")
    fun updateBook(@PathVariable("id") id: Long, @RequestBody book: Book): ResponseEntity<Any> {
        return try {
            val currBook = bookRepository.findById(id).get()
            currBook.title = book.title
            currBook.isbn = book.isbn
            currBook.cost = book.cost
            currBook.category = book.category
            currBook.description = book.description
            currBook.year = book.year
            bookRepository.save(currBook)
            ResponseEntity.noContent().build<Any>()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Remove a book")
    @DeleteMapping("/books/{id}")
    fun deleteBook(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            this.bookRepository.deleteById(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    // --------------------- Author Operations --------------------- //

    @Operation(summary = "Get all authors")
    @GetMapping("/authors")
    fun allAuthors(): ResponseEntity<CollectionModel<AuthorRepresentation>> {
        val authors = authorRepository.findAll()
        return ResponseEntity(authorAssembler.toCollectionModel(authors), HttpStatus.OK)
    }

    @Operation(summary = "Get an author by id")
    @GetMapping("/authors/{id}")
    fun getAuthorById(@PathVariable("id") id: Long): ResponseEntity<AuthorRepresentation> {
        return authorRepository
                .findById(id)
                .map { entity: Author -> authorAssembler.toModel(entity) }
                .map { body: AuthorRepresentation -> ResponseEntity.ok(body) }
                .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Add a new author")
    @PostMapping("/authors")
    fun addAuthor(@RequestBody author: Author): ResponseEntity<Any> {
        return try {
            val newAuthor = this.authorRepository.save(author)
            val location: URI =
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(newAuthor.id)
                            .toUri()
            ResponseEntity.created(location).body(authorAssembler.toModel(newAuthor))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update the information of an author")
    @PutMapping("/authors/{id}")
    fun updateAuthor(
            @PathVariable("id") id: Long,
            @RequestBody author: Author
    ): ResponseEntity<Any> {
        return try {
            val currAuthor = authorRepository.findById(id).get()
            currAuthor.firstName = author.firstName
            currAuthor.lastName = author.lastName
            authorRepository.save(currAuthor)
            ResponseEntity.noContent().build<Any>()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Remove an author")
    @DeleteMapping("/authors/{id}")
    fun deleteAuthor(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            this.authorRepository.deleteById(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    // --------------------- Bio Operations --------------------- //

    @Operation(summary = "Get all bios")
    @GetMapping("/bios")
    fun allBios(): ResponseEntity<CollectionModel<BioRepresentation>> {
        val bios = bioRepository.findAll()
        return ResponseEntity(bioAssembler.toCollectionModel(bios), HttpStatus.OK)
    }

    @Operation(summary = "Get a bio by id")
    @GetMapping("/bios/{id}")
    fun getBioById(@PathVariable("id") id: Long): ResponseEntity<BioRepresentation> {
        return bioRepository
                .findById(id)
                .map { entity: Bio -> bioAssembler.toModel(entity) }
                .map { body: BioRepresentation -> ResponseEntity.ok(body) }
                .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Add a new bio")
    @PostMapping("/bios")
    fun addBio(@RequestBody bio: Bio): ResponseEntity<Any> {
        return try {
            val newBio = this.bioRepository.save(bio)
            val location: URI =
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(newBio.id)
                            .toUri()
            ResponseEntity.created(location).body(bioAssembler.toModel(newBio))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Update the information of a bio")
    @PutMapping("/bios/{id}")
    fun updateBio(@PathVariable("id") id: Long, @RequestBody bio: Bio): ResponseEntity<Any> {
        return try {
            val currBio = bioRepository.findById(id).get()
            currBio.content = bio.content // Update fields as necessary
            bioRepository.save(currBio)
            ResponseEntity.noContent().build<Any>()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Remove a bio")
    @DeleteMapping("/bios/{id}")
    fun deleteBio(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            this.bioRepository.deleteById(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    // --------------------- Order Operations --------------------- //

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    fun allOrders(): ResponseEntity<CollectionModel<OrderRepresentation>> {
        val orders = orderRepository.findAll()
        return ResponseEntity(orderAssembler.toCollectionModel(orders), HttpStatus.OK)
    }

    @Operation(summary = "Get an order by id")
    @GetMapping("/orders/{id}")
    fun getOrderById(@PathVariable("id") id: Long): ResponseEntity<OrderRepresentation> {
        return orderRepository
                .findById(id)
                .map { entity: Order -> orderAssembler.toModel(entity) }
                .map { body: OrderRepresentation -> ResponseEntity.ok(body) }
                .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Add a new order")
    @PostMapping("/orders")
    fun addOrder(@RequestBody order: Order): ResponseEntity<Any> {
        return try {
            val newOrder = this.orderRepository.save(order)
            val location: URI =
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(newOrder.id)
                            .toUri()
            ResponseEntity.created(location).body(orderAssembler.toModel(newOrder))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
    @Operation(summary = "Get all orders for a specific book")
    @GetMapping("/books/{id}/orders")
    fun getBookOrdersById(
            @PathVariable("id") id: Long
    ): ResponseEntity<CollectionModel<OrderRepresentation>> {
        return bookRepository
                .findById(id)
                .map { book ->
                    val orders = orderRepository.findByBookId(book.id)
                    ResponseEntity.ok(orderAssembler.toCollectionModel(orders))
                }
                .orElse(ResponseEntity.notFound().build())
    }

    @Operation(summary = "Update the information of an order")
    @PutMapping("/orders/{id}")
    fun updateOrder(@PathVariable("id") id: Long, @RequestBody order: Order): ResponseEntity<Any> {
        return try {
            val currOrder = orderRepository.findById(id).get()
            // Update order fields as necessary
            currOrder.status = order.status
            orderRepository.save(currOrder)
            ResponseEntity.noContent().build<Any>()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(summary = "Remove an order")
    @DeleteMapping("/orders/{id}")
    fun deleteOrder(@PathVariable("id") id: Long): ResponseEntity<Any> {
        return try {
            this.orderRepository.deleteById(id)
            ResponseEntity.noContent().build<Any>()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().build()
        }
    }
}
