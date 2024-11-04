package seg3x02.booksrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrderRepresentation(
        var id: Long = 0,
        var quantity: Int = 0,
        var bookId: Long = 0, // The ID of the book related to this order
        var orderDate: String = "", // Format: YYYY-MM-DD or LocalDate
        var totalCost: Double = 0.0
) : RepresentationModel<OrderRepresentation>()
