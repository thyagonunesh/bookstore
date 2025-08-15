package br.com.erudio.data.dto;

import br.com.erudio.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
//@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
@JsonFilter("PersonFilter")
public class PersonDTO {

    private Long id;

//    @JsonProperty(value = "first_name")
    private String firstName;

    //    @JsonProperty(value = "last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    private String address;

//    @JsonIgnore
    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    private String sensitiveData;

}
