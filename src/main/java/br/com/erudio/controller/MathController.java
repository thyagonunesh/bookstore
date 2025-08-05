package br.com.erudio.controller;

import br.com.erudio.exception.UnsupportedMathOperationException;
import br.com.erudio.math.SimpleMath;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.erudio.request.converters.NumberConverter.convertToDouble;
import static br.com.erudio.request.converters.NumberConverter.isNumeric;

@RestController
@RequestMapping("/math")
public class MathController {

    private final SimpleMath math = new SimpleMath();

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");
        
        return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.subtraction(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.multiplication(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/division/{numberOne}/{numberTwo}")
    public Double division(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.division(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(
            @PathVariable String numberOne,
            @PathVariable String numberTwo
    ) {
        if (!isNumeric(numberOne) || !isNumeric(numberTwo)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.mean(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/squareRoot/{numberOne}")
    public Double squareRoot(
            @PathVariable String numberOne
    ) {
        if (!isNumeric(numberOne)) throw new UnsupportedMathOperationException("Please set a numeric value!");

        return math.squareRoot(convertToDouble(numberOne));
    }

}
