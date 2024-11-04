package seg3x02.booksrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BookTitleRepresentation(var id: Long = 0, var title: String = "")
