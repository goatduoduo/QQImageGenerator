package com.duoduo.bean;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * @Author: goatduoduo
 * @Description:
 * @Date: Created in 2022/11/20 21:23
 */
public class TransferableImage implements Transferable {
    private final Image image;
    public TransferableImage(Image image) {this.image = image;}
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        if(!DataFlavor.imageFlavor.equals(flavor)){throw new UnsupportedFlavorException(flavor);}
        return image;
    }
}
