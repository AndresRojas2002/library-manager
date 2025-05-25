package co.com.andres.models.dto;



import lombok.Data;

//peticion de libro
@Data
public class BookRequest {

    private String title;

    private String author;

    private String isbn;

    private String  yearOfPublication;

    private String gender;

}
