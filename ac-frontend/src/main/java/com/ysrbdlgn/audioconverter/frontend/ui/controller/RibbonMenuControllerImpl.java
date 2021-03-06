package com.ysrbdlgn.audioconverter.frontend.ui.controller;

import com.ysrbdlgn.audioconverter.common.entity.EFileType;
import com.ysrbdlgn.audioconverter.common.entity.FileTableEntry;
import com.ysrbdlgn.audioconverter.converter.service.ConverterService;
import com.ysrbdlgn.audioconverter.frontend.AudioConverterApplication;
import com.ysrbdlgn.audioconverter.frontend.service.FileTableService;
import com.ysrbdlgn.audioconverter.settings.command.OpenSettingsDialogCommandImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javazoom.jl.decoder.JavaLayerException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysrbdlgn on 11-Jun-17.
 */
public class RibbonMenuControllerImpl implements RibbonMenuController {

    private FileTableService fileTableService;
    private ConverterService converterService;
    private OpenSettingsDialogCommandImpl openSettingsDialogCommand;

    @Override
    public void addFileButtonPressed(ActionEvent actionEvent) {

        FileChooser chooser = new FileChooser();
        /* TODO: Initial directory should be the directory where app started.*/

        chooser.setInitialDirectory(new File("E:\\test_mp3"));
        addExtensionFilters(chooser);
        File file = chooser.showOpenDialog(AudioConverterApplication.getScene().getWindow());

        if(file == null)
            return;

        fileTableService.addFile(file);

    }

    @Override
    public void removeFileButtonPressed(ActionEvent actionEvent) {

        Button sourceButton = (Button) actionEvent.getSource();

        System.out.println(sourceButton.getId());

    }

    @Override
    public void convertButtonPressed(ActionEvent actionEvent) {

        ObservableList<FileTableEntry> entryList = (ObservableList<FileTableEntry>) fileTableService.getEntries();

        String outputFolder = "E:\\converted\\";
        String fileName;

        for(FileTableEntry entry : entryList) {

            fileName = entry.getPath().substring(entry.getPath().lastIndexOf(File.separator), entry.getPath().lastIndexOf("."));
            converterService.convert(entry, outputFolder + fileName + ".wav");

        }
    }

    @Override
    public void settingsButtonPressed(ActionEvent actionEvent) {
        openSettingsDialogCommand.execute();
    }

    private void addExtensionFilters(FileChooser chooser){

        String extensionTemplate = "*.%s";

        // Add individual filters
        for(EFileType fileType : EFileType.values()) {
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    fileType.getDescription(), String.format(extensionTemplate, fileType.getText()));
            chooser.getExtensionFilters().add(extFilter);
        }

        // Add "all audio files" item

        List<String> allExtensions = new ArrayList<>();
        for(EFileType fileType : EFileType.values()) {
            allExtensions.add(String.format(extensionTemplate, fileType.getText()));
        }
        FileChooser.ExtensionFilter allExtFilter = new FileChooser.ExtensionFilter("All Audio Files", allExtensions);
        chooser.getExtensionFilters().add(0, allExtFilter);

    }

    public void setFileTableService(FileTableService fileTableService) {
        this.fileTableService = fileTableService;
    }

    public void setConverterService(ConverterService converterService) {
        this.converterService = converterService;
    }

    public void setOpenSettingsDialogCommand(OpenSettingsDialogCommandImpl openSettingsDialogCommand) {
        this.openSettingsDialogCommand = openSettingsDialogCommand;
    }

    public OpenSettingsDialogCommandImpl getOpenSettingsDialogCommand() {
        return openSettingsDialogCommand;
    }
}
