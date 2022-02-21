package com.letscode.moveisbattle.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@AllArgsConstructor
public class EmailValidator implements Predicate<String> {
    @Override
    public boolean test(String email) {
        // TODO: Adicionar regex para validação de email
        return true;
    }
}
