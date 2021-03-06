package com.ysrbdlgn.audioconverter.converter.service;

import com.ysrbdlgn.audioconverter.common.entity.FileTableEntry;
import com.ysrbdlgn.audioconverter.common.concurrent.ACThreadFactory;
import com.ysrbdlgn.audioconverter.converter.listener.ConvertFileProgressListener;
import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ysrbdlgn on 14-Jun-17.
 */
public class ConverterServiceImpl implements ConverterService {

    private Converter converter;
    private ExecutorService executorService;
    private ConvertFileProgressListener convertFileProgressListener;

    public ConverterServiceImpl() {}

    public void initialize() {

        executorService = Executors.newFixedThreadPool(
                1,
                new ACThreadFactory()
        );
    }

    @Override
    public void convert(FileTableEntry entry, String destination) {

        ConverterExecService<Void> converterExecService = new ConverterExecService<>();
        converterExecService.setExecutor(executorService);
        converterExecService.setEntry(entry);
        converterExecService.setDestination(destination);
        converterExecService.setConverter(converter);
        converterExecService.setConvertFileProgressListener(convertFileProgressListener);
        converterExecService.start();

    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void setConvertFileProgressListener(ConvertFileProgressListener convertFileProgressListener) {
        this.convertFileProgressListener = convertFileProgressListener;
    }

}
