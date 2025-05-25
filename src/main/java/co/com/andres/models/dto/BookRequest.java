package co.com.andres.models.dto;



import lombok.Data;

@Data
public class BookRequest {

    private String title;

    private String author;

    private String isbn;

    private String  yearOfPublication;

    private String gender;

}
