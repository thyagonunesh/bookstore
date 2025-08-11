package br.com.erudio.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ObjectMapper {

    private final ModelMapper mapper;

    public ObjectMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <O, D> D parseObject(O origem, Class<D> destino) {
        return mapper.map(origem, destino);
    }

    public <O, D> List<D> parseListObjects(List<O> origem, Class<D> destino) {
        return origem.stream()
                .map(element -> mapper.map(element, destino))
                .collect(Collectors.toList());
    }

    public <O, D> void mapTo(O origem, D destino) {
        mapper.map(origem, destino);
    }

}

