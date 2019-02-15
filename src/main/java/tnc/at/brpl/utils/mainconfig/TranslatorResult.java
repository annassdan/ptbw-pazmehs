package tnc.at.brpl.utils.mainconfig;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import tnc.at.brpl.utils.api.response.ResponseResolver;

import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Data
@AllArgsConstructor
@Builder
public class TranslatorResult<T> {

    T objectResult;

    List<String> errorsFound;

    @Builder.Default
    ResponseResolver.DataErrorType errorType = ResponseResolver.DataErrorType.DEFAULT_ERROR;
}
