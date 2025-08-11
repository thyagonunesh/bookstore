package br.com.erudio.data.dto.v2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PersonDTOV2 {

    private Long id;
    private String firstName;
    private String lastName;
    private Date birthDay;
    private String address;
    private String gender;

}
