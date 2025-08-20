package br.com.erudio.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
public class PersonDTO extends RepresentationModel<PersonDTO> {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

}
