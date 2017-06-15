package com.ysrbdlgn.audioconverter.converter.listener;

import javafx.beans.property.SimpleDoubleProperty;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.Obuffer;

/**
 * Created by ysrbdlgn on 15-Jun-17.
 */
public class ConverterProgressListener implements Converter.ProgressListener {

    private SimpleDoubleProperty progress;

    public ConverterProgressListener() {
    }

    private void initialize() {
        if (progress == null) {
            progress = new SimpleDoubleProperty();
        }
    }

    @Override
    public void converterUpdate(int i, int i1, int i2) {}

    @Override
    public void parsedFrame(int i, Header header) {
        System.out.println("Parse Frame: " + i);
    }

    @Override
    public void readFrame(int i, Header header) {
        System.out.println("Read Frame: " + i);
    }

    @Override
    public void decodedFrame(int i, Header header, Obuffer obuffer) {

        double freqInKHz = header.frequency() / 1000.0;

        progress.set(i / (header.framesize * freqInKHz));
    }

    @Override
    public boolean converterException(Throwable throwable) {
        return false;
    }

    public SimpleDoubleProperty progressProperty() {
        return progress;
    }

}
