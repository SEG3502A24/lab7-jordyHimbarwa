package seg3x02.booksrestapi.assemblers

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component
import seg3x02.booksrestapi.controller.ApiController
import seg3x02.booksrestapi.entities.Author
import seg3x02.booksrestapi.representation.AuthorRepresentation

@Component
class AuthorModelAssembler :
        RepresentationModelAssemblerSupport<Author, AuthorRepresentation>(
                ApiController::class.java,
                AuthorRepresentation::class.java
        ) {
    override fun toModel(entity: Author): AuthorRepresentation {
        val authorRepresentation = instantiateModel(entity)

        // Adding self link to the author representation
        authorRepresentation.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ApiController::class.java)
                                        .getAuthorById(entity.id)
                        )
                        .withSelfRel()
        )

        // Mapping fields from entity to representation
        authorRepresentation.id = entity.id
        authorRepresentation.firstName = entity.firstName
        authorRepresentation.lastName = entity.lastName
        authorRepresentation.books = toBooksRepresentation(entity.books)

        return authorRepresentation
    }

    override fun toCollectionModel(
            entities: Iterable<Author>
    ): CollectionModel<AuthorRepresentation> {
        val authorRepresentations = super.toCollectionModel(entities)

        // Adding self link to the collection of authors
        authorRepresentations.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(ApiController::class.java).allAuthors()
                        )
                        .withSelfRel()
        )

        return authorRepresentations
    }

    // Converts a list of books to their representations
    private fun toBooksRepresentation(books: List<Book>): List<BookTitleRepresentation> {
        return books.map { book ->
            BookTitleRepresentation().apply {
                id = book.id
                title = book.title
            }
        }
    }
}
