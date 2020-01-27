package iot.empiaurhouse.ambrosia.objectconverters;

import iot.empiaurhouse.ambrosia.commandobjects.SynopsisCommand;
import iot.empiaurhouse.ambrosia.model.Synopsis;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class SynopsisCommandToSynopsis implements Converter<SynopsisCommand, Synopsis> {

    @Synchronized
    @Nullable
    @Override
    public Synopsis convert(SynopsisCommand synopsisCommand) {
        if(synopsisCommand == null){
            return null;
        }

        final Synopsis synopsis = new Synopsis();
        synopsis.setId(synopsisCommand.getId());
        synopsis.setRecipeSynopsis(synopsisCommand.getRecipeSynopsis());
        return synopsis;
    }
}
