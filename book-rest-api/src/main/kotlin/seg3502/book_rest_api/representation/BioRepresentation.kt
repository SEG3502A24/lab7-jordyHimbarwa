package seg3x02.booksrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation

@Relation(collectionRelation = "bios")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class BioRepresentation(var id: Long = 0, var biodata: String = "") :
        RepresentationModel<BioRepresentation>()
