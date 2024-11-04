package seg3x02.booksrestapi.representation

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AuthorNameRepresentation(var firstName: String = "", var lastName: String = "")
