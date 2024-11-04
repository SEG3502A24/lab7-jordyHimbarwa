package seg3x02.booksrestapi.assemblers

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Component
import seg3x02.booksrestapi.controller.ApiController
import seg3x02.booksrestapi.entities.Order
import seg3x02.booksrestapi.representation.OrderRepresentation

@Component
class OrderModelAssembler :
        RepresentationModelAssemblerSupport<Order, OrderRepresentation>(
                ApiController::class.java,
                OrderRepresentation::class.java
        ) {

    override fun toModel(entity: Order): OrderRepresentation {
        // Convert Order entity to OrderRepresentation
        val orderRepresentation = OrderRepresentation(id = entity.id, quantity = entity.quantity)

        // Add links to the representation (self link)
        val selfLink: Link =
                linkTo(ApiController::class.java).slash(orderRepresentation.id).withSelfRel()
        orderRepresentation.add(selfLink)

        return orderRepresentation
    }

    override fun toCollectionModel(
            entities: Iterable<Order>
    ): CollectionModel<OrderRepresentation> {
        val ordersRepresentation: List<OrderRepresentation> = entities.map { toModel(it) }.toList()
        return CollectionModel(
                ordersRepresentation,
                linkTo(ApiController::class.java).slash("orders").withSelfRel()
        )
    }
}
