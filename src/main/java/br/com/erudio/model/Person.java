package br.com.erudio.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

}
