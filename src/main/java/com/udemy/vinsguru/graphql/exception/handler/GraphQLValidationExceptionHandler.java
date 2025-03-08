package com.udemy.vinsguru.graphql.exception.handler;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GraphQLValidationExceptionHandler extends DataFetcherExceptionResolverAdapter {
	@Override
	protected GraphQLError resolveToSingleError(@NotNull Throwable ex, @NotNull DataFetchingEnvironment env) {
		if (ex instanceof ConstraintViolationException violationException) {
			String message = violationException.getConstraintViolations().stream()
					.map(ConstraintViolation::getMessage)
					.collect(Collectors.joining("; "));

			return GraphqlErrorBuilder.newError(env)
					.message(message)
					.errorType(ErrorType.BAD_REQUEST)
					.build();
		}
		return null;
	}
}
