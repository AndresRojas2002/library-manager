package co.com.andres.models.dto;



import lombok.Data;

@Data
public class BookResponse {

    private Long bookId;

    private String title;

    private String author;

    private String isbn;

    private String yearOfPublication;

    private String gender;

    private String State;
}
