package com.vst.myapplication.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;

import java.io.FileOutputStream;
import java.io.IOException;

public class ImagePrintDocumentAdapter extends PrintDocumentAdapter {

    private final Bitmap bitmap;
    private final Context context;

    public ImagePrintDocumentAdapter(Context context, Bitmap bitmap) {
        this.context = context;
        this.bitmap = bitmap;
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes,
                         CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle bundle) {
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        // Create a print document info with the content type set to PHOTO
        PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("image_document.pdf")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_PHOTO)
                .build();
        callback.onLayoutFinished(pdi, true);
    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination,
                        CancellationSignal cancellationSignal, WriteResultCallback callback) {
        try (FileOutputStream out = new FileOutputStream(destination.getFileDescriptor())) {
            // Compress the bitmap to PNG and write it to the output stream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
        }
    }
}
