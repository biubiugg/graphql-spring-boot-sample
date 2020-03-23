package com.youway.mybiz.graphql.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class BookNotFoundGraphqlException  extends RuntimeException implements GraphQLError {
	
	private static final long serialVersionUID = -5798629279894414342L;
	
	private Map<String, Object> extensions = new HashMap<>();

    public BookNotFoundGraphqlException(String message, Long invalidBookId) {
        super(message);
        extensions.put("invalidBookId", invalidBookId);
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}