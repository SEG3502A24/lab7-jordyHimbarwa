package seg3x02.booksrestapi.assemblers

import java.util.*
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component
import seg3x02.booksrestapi.controller.ApiController
import seg3x02.booksrestapi.entities.Author
import seg3x02.booksrestapi.entities.Book
import seg3x02.booksrestapi.representation.AuthorNameRepresentation
import seg3x02.booksrestapi.representation.BookRepresentation

@Component
class BookModelAssembler :
        RepresentationModelAssemblerSupport<Book, BookRepresentation>(
                ApiController::class.java,
                BookRepresentation::class.java
        ) {
    override fun toModel(entity: Book): BookRepresentation {
        val bookRepresentation = instantiateModel(entity)

        // Adding self link
        bookRepresentation.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ApiController::class.java)
                                        .getBookById(entity.id)
                        )
                        .withSelfRel()
        )

        // Adding authors representation
        bookRepresentation.authors = toAuthorsRepresentation(entity.authors)

        // Adding link for book orders
        bookRepresentation.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ApiController::class.java)
                                        .getBookById(entity.id)
                        )
                        .withRel("orders")
        )

        // Mapping fields from entity to representation
        bookRepresentation.id = entity.id
        bookRepresentation.isbn = entity.isbn
        bookRepresentation.category = entity.category
        bookRepresentation.title = entity.title
        bookRepresentation.cost = entity.cost
        bookRepresentation.year = entity.year
        bookRepresentation.description = entity.description

        return bookRepresentation
    }

    override fun toCollectionModel(entities: Iterable<Book>): CollectionModel<BookRepresentation> {
        val bookRepresentations = super.toCollectionModel(entities)

        // Adding self link to the collection
        bookRepresentations.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ApiController::class.java).allBooks()
                        )
                        .withSelfRel()
        )

        return bookRepresentations
    }

    // Converts a list of authors to their representations
    fun toAuthorsRepresentation(authors: List<Author>): List<AuthorNameRepresentation> {
        return if (authors.isEmpty()) Collections.emptyList()
        else authors.map { authorRepresentation(it) }
    }

    // Converts a single author to its representation
    private fun authorRepresentation(author: Author): AuthorNameRepresentation {
        val representation =
                AuthorNameRepresentation().apply {
                    firstName = author.firstName
                    lastName = author.lastName
                }

        representation.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ApiController::class.java)
                                        .getAuthorById(author.id)
                        )
                        .withSelfRel()
        )

        return representation
    }
}
