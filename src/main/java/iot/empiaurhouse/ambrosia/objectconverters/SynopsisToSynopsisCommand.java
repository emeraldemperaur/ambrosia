package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.SynopsisCommand;
import iot.empiaurhouse.ambrosia.model.Synopsis;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SynopsisToSynopsisCommand implements Converter<Synopsis, SynopsisCommand> {

    @Synchronized
    @Nullable
    @Override
    public SynopsisCommand convert(Synopsis synopsis) {
        if (synopsis == null){
            return null;
        }

        final SynopsisCommand synopsisCommand = new SynopsisCommand();
        synopsisCommand.setId(synopsis.getId());
        synopsisCommand.setRecipeSynopsis(synopsis.getRecipeSynopsis());
        return synopsisCommand;
    }



}
