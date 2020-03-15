package tnc.at.brpl.utils.mainconfig.models;

import lombok.*;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FieldChoice {

    CellModel choiceSelector;

    CellModel choiceValueAt;

}
