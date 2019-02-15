package tnc.at.brpl.utils.mainconfig.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Data
@AllArgsConstructor
@Builder
public class FieldChoice {

    CellModel choiceSelector;

    CellModel choiceValueAt;

}
