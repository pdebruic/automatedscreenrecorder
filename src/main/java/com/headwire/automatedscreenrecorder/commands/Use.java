package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;

public class Use extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        ctx.getDriver().use(ctx, args.getModifier());
    }
}
