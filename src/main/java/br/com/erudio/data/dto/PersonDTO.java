package br.com.erudio.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PersonDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

}
