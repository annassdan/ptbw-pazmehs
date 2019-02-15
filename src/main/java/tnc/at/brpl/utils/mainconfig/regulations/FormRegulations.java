package tnc.at.brpl.utils.mainconfig.regulations;

import java.util.List;

/**
 * Copyright (c) 2017.
 *
 * @author annasldan ~| annasmn34333@gmail.com | TNC Indonesia |~
 */
public interface FormRegulations<M> {

    List<String> analyzingDataRegulations(M data);

    List<String> analyzingDataRegulations(M data, List<String> errors);


}
