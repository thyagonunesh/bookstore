package br.com.erudio.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class BookDTO extends RepresentationModel<BookDTO> {

    private Long id;
    private String author;
    private Date launchDate;
    private BigDecimal price;
    private String title;

}
