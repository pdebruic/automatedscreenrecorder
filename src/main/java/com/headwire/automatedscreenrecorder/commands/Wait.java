package com.headwire.automatedscreenrecorder.commands;

import com.headwire.automatedscreenrecorder.helpers.Arguments;
import com.headwire.automatedscreenrecorder.helpers.AudioMark;
import com.headwire.automatedscreenrecorder.helpers.Command;
import com.headwire.automatedscreenrecorder.helpers.Context;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.Map;

public class Wait extends Command {
    @Override
    public void execute(Context ctx, Arguments args) {
        String modifier = args.getModifier();
        try {
            long time = Integer.parseInt(modifier);
            long maxWait = ctx.getMaxWait();
            time = Math.min(time, maxWait);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException("wait interrupted, exiting",e);
            }
        } catch(NumberFormatException nfe) {
            try {
                if(!ctx.produceAudio()) {
                    execute(ctx, new Arguments("wait 100"));
                    return;
                }

                long add = 0;
                if(args.getData() != null) {
                    add = Integer.parseInt(args.getData());
                }

                AudioMark mark = ctx.getLastAudioMark();
                File file = new File(mark.getFile());
                AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(file);
                Map properties = baseFileFormat.properties();
                Long duration = (Long) properties.get("duration") - 500;
                System.out.println(modifier + " duration: "+((duration+add)/1000));
                Thread.sleep((duration+add)/1000);
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
