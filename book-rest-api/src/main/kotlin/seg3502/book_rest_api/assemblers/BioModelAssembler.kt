package seg3x02.booksrestapi.assemblers

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.stereotype.Component
import seg3x02.booksrestapi.controller.ApiController
import seg3x02.booksrestapi.entities.Bio
import seg3x02.booksrestapi.representation.BioRepresentation

@Component
class BioModelAssembler :
        RepresentationModelAssemblerSupport<Bio, BioRepresentation>(
                ApiController::class.java,
                BioRepresentation::class.java
        ) {

    override fun toModel(entity: Bio): BioRepresentation {
        // Convert Bio entity to BioRepresentation
        val bioRepresentation =
                BioRepresentation(
                        id = entity.id,
                        content = entity.content // Add more fields as necessary
                )

        // Add links to the representation (self link)
        val selfLink: Link =
                linkTo(ApiController::class.java).slash(bioRepresentation.id).withSelfRel()
        bioRepresentation.add(selfLink)

        return bioRepresentation
    }

    override fun toCollectionModel(entities: Iterable<Bio>): CollectionModel<BioRepresentation> {
        val biosRepresentation: List<BioRepresentation> = entities.map { toModel(it) }.toList()
        return CollectionModel(
                biosRepresentation,
                linkTo(ApiController::class.java).slash("bios").withSelfRel()
        )
    }
}
