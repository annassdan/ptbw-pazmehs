package tnc.at.brpl.utils.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


/**
 * Copyright (c) 2017.
 *
 * @author  annasldan   ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Getter
@Setter
@Builder
class ResponseGeneratedUri {

    private String first;

    private String previous;

    private String next;

    private String last;
}
