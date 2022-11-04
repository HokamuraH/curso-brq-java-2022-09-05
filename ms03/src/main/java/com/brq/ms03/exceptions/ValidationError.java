package com.brq.ms03.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
//@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError extends StandardError {
    private List<FieldMessage> errors = new ArrayList<>();
}
